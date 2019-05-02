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
	/*  var uid='${sessionScope.user.id}';  */
	 var uid='${requestScope.id}'; 
	//加双引号（单引号）是因为${sessionScope.username}是字符串，不加会当成是变量
	var ws;		//一个ws对象就是一个通信管道,在外面是全局变量
	var target="ws://localhost:8888/1webchat/chatSocket?uid="+uid+"&username="+username;		//url
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
        
          ws.onmessage=function(event){
        	  eval("var msg="+event.data+";");	//eval可以将一个字符串转成本地代码来执行
        	  	     	  
        	  if(undefined!=msg.welcome){
            	$("#content").append(msg.welcome);	//将msg.welcome打印到content中
            	}
			  if(undefined!=msg.usernames){		//未定义的值和定义未赋值的为undefined，null是一种特殊的object,NaN是一种特殊的number
				  $("#userList").html("");		//将内容清空,如不执行一个用户退出再进来会显示两次名字
 					$(msg.ids).each(function(i,v){		//遍历到第i个，v为第i个的值			  
					  $("#userList").append("<input type=checkbox   value='"+msg.usernames[i]+"'/>"+msg.usernames[i]+" "+
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
	
	 
	function Add(u){
		var flag=window.confirm("是否添加对方为好友");
		if(flag){
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"fid":u,"uid":uid,"ch":"1","group":"def"},
				
			success:function(result){
				if(result=="true"){
					alert("添加成功");
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
	function Del(u){
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
					alert("删除成功！！！");
				}
			}	
		});
		
		}
	}
	function See(u){
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"fid":u,"uid":uid,"ch":"3"},
				
			success:function(result){
				if(result=="true"){
					alert("添加成功");
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
	
	 function subSend(){
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
			 	to:to,
   				msg: val,
   				type:2  //1 广播  2 私聊
		   		} 
		 }
		 var str=JSON.stringify(obj);	//将对象转换成json字符串
   		ws.send(str);		//传给后台OnMessage
   		$("#msg").val("");  
   	}	

</script>
</head>
<body>
	
	<h1>Web聊天室</h1>
	<hr>
	<div id="container" style="border:1px solid black; width:400px;height:400px;
	float:left;">
	<div
        style="width: 400px; height: 350px; overflow: scroll; border: 1px solid;"
        id="content"></div>

	<div  style="border-top:1px solid black; width:400px;height:50px;">
	<input id="msg"/><button onclick="subSend()">send</button>
	</div>
	</div>
	<div id="userList" style="border:1px solid black; width:150px;height:400px;
	float:left"></div>
	<a href="${pageContext.request.contextPath}/features/updateInfo.jsp">修改个人信息</a>
</body>
</html>









