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
	var totalPage='${sessionScope.page.totalPage}';
	var groupId=window.location.href.split("?")[1].split("=")[1];		//页面跳转带参接收
	//alert(groupId);
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
					  $("#userList").append("<input type=checkbox   value='"+msg.ids[i]+"'/>"+msg.usernames[i]+" "+
					 "<input type=button  onclick='Add("+v+")' value='添加好友'>"+
					 "<input type=button  onclick='Del("+v+")' value='删除好友'>"+
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
	
	 function subSend(){			//发送信息
		 var val=$("#msg").val();//得到用户聊天的信息--发出
		 var u=$("#userList :checked");			//ps:要加个空格，不然取不到值。。。
	/* 	  console.info(u.length);  */
		  var obj="";
		 if(u.length==0){		//如果选中的人为0，则进行广播
			obj={					//json对象
   				msg: val,
   				type:1  //1 广播  2 私聊
		   		} 
		 }else{		
			 //否则进行私聊，下面得到私聊的用户，通过json进行传值
			 var to=$("#userList :checked").val();   //发送给选中的人，进行私聊，可以选中多个
			obj={			
			 	to:who,
   				msg: val,
   				type:2  //1 广播  2 私聊
		   		} 
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
			   				msg: val,
			   				type:2  //1 广播  2 私聊
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
	
	function Del(u){			//删除好友
		var flag=window.confirm("是否删除好友？？？");
		if(flag){
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"fid":u,"uid":uid,"ch":"2"},
				
			success:function(result){
				if(result=="false")
					alert("删除失败！！！");
				else if(result=="self")
					alert("不能删除自己！！！");
				else if(result=="no"){
					alert("对方还不是你的好友！！！");
				}else{
					var flag=window.confirm("删除成功，是否告知对方？？？");
					if(flag){
					var val=username+"("+account+")"+"已在好友列表中将你删除";
					var obj={					//json对象
							to:u,
			   				msg: val,
			   				type:2  //1 广播  2 私聊
					   		} 
					 var str=JSON.stringify(obj);
			   		ws.send(str);	
			   		}	
				}
			}	
		});
		
		}
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
	
	function SeeData(data){		//查看聊天记录
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
				data:{"fid":who,"uid":uid,"currentPage":currentPage},
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
	
	 function SeeData(data){		//查看聊天记录
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
					"way":2	
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
<body>
	
	<h1>Web聊天室</h1>
	<hr>
	
	<div id="announcement" style="border:1px solid black; width:1500px;height:80px;"></div>
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
	float:left"></div>
	<div id="recordArea" style="border:1px solid black; width:450px;height:580px; overflow: scroll;
	float:left">
	</div>
	
	<button onclick="SeeData()">点击查看消息记录</button><br/>
	<span><button onclick="SeeData('start')">首页</button></span>
	<span><button onclick="SeeData('-1')">上一页</button></span>
	<span><button onclick="SeeData('+1')">下一页</button></span>
	<span><button onclick="SeeData('finally')">尾页</button></span>   
</body>
</html>









