<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户界面</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

		var uid='${sessionScope.user.id}'; 
		function add(){
			var gname=$("#group").val();
			$.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/FriendServlet',
				async:true,		//异步
				data:{"gname":gname},
				success:function(data){
					alert(data);
					var majorList=eval("("+data+")");//处理，将json字符串转换为对象    
					$.each(majorList, function (i, v) { 
						$("#friend").append(v.name+" "+"<input type=button onclick='Chat("+v.id+")'  value='私聊'>"+" "+
								 "<input type=button  onclick='Del("+v.id+")' value='删除好友'>"+
								"<input type=button onclick='See("+v.id+")'  value='查看信息'>"+
								"<br/>"							
						);
					});	
				}
			});
		}		
		function Chat(v){		
				window.location.href="${pageContext.request.contextPath}/chat/chat.jsp?uid="+v; 												
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
					else{
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
					if(result=="true")
					window.location.href="${pageContext.request.contextPath}/features/information.jsp"
					else
						alert("用户不存在");
				}			
			});
			
		}
		
</script>
</head>
<body>

	<h1>用户界面</h1>
	<hr/>
	<a href="${pageContext.request.contextPath}/features/updateInfo.jsp">修改个人信息</a><br/><br/><br/> 
	好友列表：
	<c:forEach items="${user.groups }" var="group">
	
		<br/><input type="button" onclick="add()" id="group" value="${group.name }">
		<div  style="width: 400px; height: 150px; overflow: scroll; border: 1px solid;" 
        id="friend"></div>
	</c:forEach>
      
</body>
</html>