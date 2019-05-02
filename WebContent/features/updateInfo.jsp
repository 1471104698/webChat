<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="cn.oy.pojo.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人信息修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">
	<script type="text/javascript">
	function check(){	
		var name=$("#name").val();
		var age=$("#age").val();
		var tel=$("#tel").val();
		var sex= $('input[name="sex"]:checked').val();
		var signature=$("#signature").val();
		$.ajax({			
			url:'${pageContext.request.contextPath }/UpdateInfoServlet',
			method:'Post',			
			async:true,		//异步
			data:{
				"age":age,
				"name":name,
				"tel":tel,
				"sex":sex,
				"signature":signature
				},
			success:function(result){
				if(result=="true"){
					alert("信息修改成功！！！");	
					window.location.href="${pageContext.request.contextPath}/chat/chat.jsp"; 
				}else{
					alert("信息修改失败！！！");
				}							
			}		
		});

		return false;
	}
	</script>
	
   <h1 >更新信息界面</h1>	
   			<hr/>
   			<input type="button" value="back" onclick="back()">
	<input type="button" value="next" onclick="next()">
	<script type="text/javascript">
		function back() {
			history.back();
		}
		function next() {
			history.forward();
		}	
	</script>

		
		<c:if test="${not empty user}">
		&emsp;&emsp;<a href="${pageContext.request.contextPath }/chat/chat.jsp">返回首页</a><br/><br/>
		</c:if>
		<form action=""  onsubmit="return check()">
			<ul>
				<li>姓名：&emsp;&emsp;<input type="text" name="name" id="name" placeholder="姓名" value="${sessionScope.user.name }" maxlength="10" required onkeyup="this.value=this.value.replace(/(^\s+)|(\s+$)/g,'');"></li>
				<li>年龄：&emsp;&emsp;<input type="text" name="age" id="age"  placeholder="年龄"  value="${sessionScope.user.age }" maxlength="3" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');"></li>
				<li>手机号码：<input type="text" placeholder="手机号码" id="tel" name="tel"  value="${sessionScope.user.tel }" maxlength="11" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');"></li>
				<li>性别：&emsp;&emsp;<input type="radio" name="sex"  value="1" checked="checked">男
					  				  <input type="radio" name="sex"  value="2" >女</li>
				<li>个性签名：<br/><textarea cols="50" rows="10" id="signature" name="signature" >${sessionScope.user.signature }</textarea></li>
				<li>&emsp;&emsp;&emsp;<input type="submit" value="确认修改"></li>
			</ul>	
			
		</form>
</body>
</html>