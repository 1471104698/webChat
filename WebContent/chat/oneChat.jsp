<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	var username='${sessionScope.user.name}';
	var uid='${sessionScope.user.id}';
	var account='${sessionScope.user.account}';
	var currentPage='${sessionScope.page.currentPage}';
	var fid=window.location.href.split("?")[1].split("=")[1];
	  var who='${sessionScope.fid}'; 
	  //alert(who); 
	//加双引号（单引号）是因为${sessionScope.username}是字符串，不加会当成是变量
	var ws;		//一个ws对象就是一个通信管道
	var target="ws://localhost:8888/1webchat/ochat?account="+account+"&fid="+fid;	//url
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
       //		alert(event.data);
        	  eval("var msg="+event.data+";");	//eval可以将一个字符串转成本地代码来执行
        	  	     	  
        	  if(undefined!=msg.welcome){
            	$("#content").append(msg.welcome);	//将msg.welcome打印到content中
            	}
			  if(undefined!=msg.ids&&undefined!=msg.usernames){		//未定义的值和定义未赋值的为undefined，null是一种特殊的object,NaN是一种特殊的number
				  $("#userList").html("");		//将内容清空,如不执行一个用户退出再进来会显示两次名字
 					$(msg.ids).each(function(i,v){		//遍历到第i个，v为第i个的值
 					"<img src='../"+msg.picPaths[i]+"' width='"+50+"px"+"' height='"+50+"px'>"+
 					 $("#userList").append("<input type=hidden   value='"+msg.ids[i]+"'/>"+msg.usernames[i]+"("+msg.accounts[i]+")"+		
					 "<input type=button  onclick='See("+v+")' value='查看信息'>"	+	 
							  "<br/>")		//this，遍历到的当前对象
				  });

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
	
	
	function See(u){			//查看好友信息
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"fid":u,"uid":uid,"ch":"3"},
				
			success:function(result){
				if(result=="true")
				window.location.href="${pageContext.request.contextPath}/features/information.jsp"
				else
					alert("用户不存在");
			}			
		});
	}
	
	 function subSend(){			//发送信息
		 var val=$("#msg").val();//得到用户聊天的信息--发出d
		 if(val==""||val==null)
			 return;
		  var obj="";
			
			 //进行私聊，下面得到私聊的用户，通过json进行传值
			obj={			
			 	to:fid,
   				msg: val
		   		} 
		 
		 var str=JSON.stringify(obj);	//将对象转换成json字符串
   		ws.send(str);		//传给后台OnMessage
   		$("#msg").val("");  
   	}	

	 function SeeData(data){		//查看聊天记录
			var totalPage='${sessionScope.page.totalPage}';
		 //alert(data);
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
					"xid":fid,
					"uid":uid,
					"currentPage":currentPage,
					"oper":1
				},
				success:function(data){
					//alert(data);
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
</script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">
	
	<h1>Web聊天室</h1>
	<hr>
	<div style="border:1px solid black; width:600px;height:600px;
	float:left;">
	<div
        style="width: 600px; height: 550px; overflow: scroll; border: 1px solid;"
        id="content"></div>

	<div  style="border-top:1px solid black; width:400px;height:50px;">
	<input id="msg"/><button onclick="subSend()">send</button>
	</div>
	</div>
	<div id="userList" style="border:1px solid black; width:220px;height:600px; overflow: scroll;
	float:left"></div>
	<div id="recordArea" style="border:1px solid black; width:450px;height:600px; overflow: scroll;
	float:left">
	</div>
	
	<button onclick="SeeData('start')">点击查看消息记录</button><br/>
	<span><button onclick="SeeData('start')">首页</button></span>
	<span><button onclick="SeeData('-1')">上一页</button></span>
	<span><button onclick="SeeData('+1')">下一页</button></span>
	<span><button onclick="SeeData('finally')">尾页</button></span>   
	
</body>
</html>









