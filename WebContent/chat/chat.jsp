<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>聊天界面</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	var uid='${sessionScope.user.id}';
	var account='${sessionScope.user.account}';
//	var groupId=window.location.href.split("?")[1].split("=")[1].split("&")[0];		//页面跳转带参接收
//	var adminId=window.location.href.split("?")[1].split("=")[2];
	var groupId='${groupId }';
//	alert(groupId);
//	alert(adminId);
	 var currentPage='${sessionScope.page.currentPage}';
	 var username='${sessionScope.user.name}';
	 var count=0;
	//加双引号（单引号）是因为${sessionScope.username}是字符串，不加会当成是变量
	var ws;		//一个ws对象就是一个通信管道,在外面是全局变量
	var target="ws://localhost:8888/1webchat/gchat?account="+account+"&groupId="+groupId;		//url
	window.onload=function(){
		//进入聊天页面，就打开socket通道
				
            if ('WebSocket' in window) {		//判断浏览器是否支持WebSocket
                ws = new WebSocket(target);		
            } else if ('MozWebSocket' in window) {
                ws = new MozWebSocket(target);
            } else {
                alert('WebSocket is not supported by this browser.');
                return;
            }
        
          ws.onmessage=function(event){			//信息管道
        	  eval("var msg="+event.data+";");	//eval可以将一个字符串转成本地代码来执行
        	  	     	  
        	  if(undefined!=msg.welcome){
            	$("#content").append(msg.welcome);	//将msg.welcome打印到content中
            	}
			  if(undefined!=msg.usernames){		//未定义的值和定义未赋值的为undefined，null是一种特殊的object,NaN是一种特殊的number
				  $("#userList").html("");		//将内容清空,如不执行一个用户退出再进来会显示两次名字
 					$(msg.ids).each(function(i,v){		//遍历到第i个，v为第i个的值			  
					  $("#userList").append("<img src='../"+msg.picPaths[i]+"' width='"+50+"px"+"' height='"+50+"px'>"+
							  "<input type=checkbox  value='"+msg.ids[i]+"'/>"+msg.usernames[i]+" "+
					 "<input type=button  onclick='Add("+v+")' value='添加好友'>"+
					 "<input type=button  onclick='See("+v+")' value='查看信息'>"	+	 
							  "<br/>")		//this，遍历到的当前对象
				  });

			  }
			  if(undefined!=msg.notice){
				  $("#userList").html("");
				  $("#announcement").append(msg.notice);
			  }
			  if(undefined!=content){
				  $("#content").append(msg.content);			//将发送的消息显示在下面的id为content的框中
			  }
            }
         //发生错误时
          ws.onerror = function(msg) {
              writeToScreen('<span style="color:red;">系统出错啦</span>' + msg.data);
              ws.close();
          }; 
	}

	 function subSend(){			//发送信息
		 var val=$("#msg").val();//得到用户聊天的信息--发出
		 if(val==""){
			 return;
		 }
	/* 	  console.info(u.length);  */
		  var obj="";
			obj={					//json对象
   				msg: val
		   		} 		 
		 var str=JSON.stringify(obj);	//将对象转换成json字符串
   		ws.send(str);		//传给后台OnMessage
   		$("#msg").val("");  
   	}	
	 
	 
	 
	function Add(u){			//添加好友
		var flag=window.confirm("是否添加对方为好友");
		if(flag){
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"fid":u,"uid":uid,"ch":"1","group":"我的好友"},
				
			success:function(result){
				if(result=="true"){
					alert("添加成功");
					var val=username+"("+account+")"+"已添加你为好友";
					var obj={					//json对象
							to:u,
			   				msg: val
					   		} 
					 var str=JSON.stringify(obj);
			   		ws.send(str);		
				}else if(result=="self"){
					alert("不能添加自己为好友");
				}
				else if(result=="false")
					alert("添加失败");
				else
					alert("你们已经是好友");
			}			
		});
		}
	}
		
	function See(u){			//查看好友信息
		window.location.href="${pageContext.request.contextPath}/FriendServlet?fid="+u+"&uid="+uid+"&ch="+3;
	}
	
	
 	  (function Refresh(){		//自动执行将聊天记录显示
		setTimeout("SeeData()",0);
	}()
	)   

	
	
	 function SeeData(data){		//查看聊天记录,自动执行
		 var totalPage='${sessionScope.page.totalPage}';
		 if(count==0){				//每次进页面都重置当前页数
			 currentPage=1;
			 count++;
		 }
		 if('start'==data)
			 currentPage=1;
		 if('-1'==data&&currentPage>1)
			 currentPage--;
		 if('+1'==data&&currentPage<totalPage)
			 currentPage++;
		 if('finally'==data)
			 currentPage=totalPage;
		 
		 $.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/PageServlet',
				async:true,		//异步
				data:{
					"xid":groupId,
					"uid":uid,
					"currentPage":currentPage,
					"oper":2	
				},
				success:function(data){
					var majorList=eval("("+data+")");
					 if(undefined!=majorList)
					 {
						 $("#recordArea").html("");
						 $.each(majorList, function (i, v){
							 $("#recordArea").append(v+"<br/>");
						 });
					 }
				}
			});
		 
	 }
	 
 	 
 	 function Banned(){				//禁言
 		 var u=$("#userList :checked").val();	//进行禁言的人
 		 if(u==uid)
 			 return;
	//	alert(u);
 		 var obj="";
 		 obj={
 		 banned:u			
 		 }
 		 var str=JSON.stringify(obj);
	   		ws.send(str);	
 		 
 	 }
 	function Lift(){				//解禁
		 var u=$("#userList :checked").val();	//进行禁言的人
		 if(u==uid)
 			 return;
		 var obj="";
		 obj={
		 lift:u				
		 }
		 var str=JSON.stringify(obj);
	   		ws.send(str);	
		 
	 }
 	 

</script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">
	
	<h1>Web聊天室</h1>
	<hr>
	
	<div id="announcement" style="border:1px solid black; width:1500px;height:200px;overflow: scroll;">
	<div><h2  style="text-align:center">群公告:</h2></div>
	
	</div>
	<div style="border:1px solid black; width:600px;height:580px;
	float:left;">
	<div
        style="width: 600px; height: 500px; overflow: scroll; border: 1px solid;"
        id="content"></div>

	<div  style="border-top:1px solid black; width:400px;height:50px;">
	<input id="msg"/><button onclick="subSend()">send</button>
	</div>
	</div>
	
	
	<div id="userList" style="border:1px solid black; width:220px;height:580px; overflow: scroll;
	float:left">
	</div>
	
	
	
	<div>	
	<h2>聊天记录：</h2>
	<div id="recordArea" style="border:1px solid black; width:450px;height:510px; overflow: scroll;
	float:left">
	</div>
	</div>
	<c:if test="${ sessionScope.user.id eq adminId }">	
	<button onclick="Banned()">禁言</button><br/>
	<button onclick="Lift()">解禁</button><br/><br/>
	</c:if>
	<span><button onclick="SeeData('start')">首页</button></span>
	<span><button onclick="SeeData('-1')">上一页</button></span><br/>
	<span><button onclick="SeeData('+1')">下一页</button></span>
	<span><button onclick="SeeData('finally')">尾页</button></span>   
</body>
</html>









