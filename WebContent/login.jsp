<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.oy.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/prefixfree.min.js'></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<title>欢迎NovelIdea来到登录平台</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my.css">
</head>

<body>
		
<script type="text/javascript">
	
	

	$(document).ready(function(){
		$("#form").submit(function(){
			console.log("${sessionScope.ocode }");
			var ocode="${sessionScope.ocode }";
			var vcode=$("#vcode").val().toLowerCase()+"";
/* 			console.log(ocode); */
			 /* alert(ocode);
			alert(vcode); */	 
			  if(vcode==ocode){
				alert("验证码输入错误");
				return false;
			}else{
				return true;
			} 
		});
	});
	</script>
   <h1 >Web聊天系统登录界面</h1>
		<hr />
		<div>${str }</div>
		<br/>
		<div>
		<section class="stark-login">			
		<form action ="${pageContext.request.contextPath}/LoginServlet"  method ="post" id="form"> 		
		<ul>
		<!-- hidden标签的表示此元素在页面中不显示，在提交表单时发送 value 属性的值。 -->
<!-- 		<li>身份：<input type="radio"  name="identity" value="0" checked="checked">学生	
				<input type="radio"  name="identity" value="1"  >管理员	
				<input type="radio"  name="identity" value="2" >群主	</li>	 -->		
		<li>账号：&emsp;<input type="text" placeholder="账号"  name="account" maxlength="20" required onkeyup="this.value=this.value.replace(/[^\w]/g,'');" ><br/></li>
		<li>密码：&emsp;<input type="password" placeholder="密码"  maxlength="40" name="pwd" required onkeyup="this.value=this.value.replace(/[^\w]/g,'');" ><br/></li>
		
		<li>验证码：<input type="text" name="vcode" id="vcode" required placeholder="验证码"  maxlength="4" onkeyup="this.value=this.value.replace(/[^\w]/g,'');">
      <br>
			  <img id="img1" src="${pageContext.request.contextPath}/GetImageServlet" style="cursor:pointer" width="120" height="30" onclick="refreshImage();"/></li>
		<li>看不清？点击图片更新验证码</li>
		<li><input type="submit" value="登录">&emsp;&emsp;	
		<input type="reset"><br>&emsp;&emsp;&emsp;</li>	
		<li><h1 class="login">其他功能：</h1></li>
		<li><a href="user/reg.jsp" >注册</a>&emsp;&emsp;
		<a href="user/find.jsp" >找回密码</a></li>	
		</ul>		
		</form>	

	 </section>   
     <script type="text/javascript">
		function refreshImage(){
			var d = new Date();
			var t = d.getTime();
			var img = document.getElementById("img1");
			img.src = "${pageContext.request.contextPath}/GetImageServlet?timestamp="+t;
		}
	</script>  
		</div>
		
</body>
</html>