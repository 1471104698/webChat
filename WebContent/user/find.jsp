<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>找回密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='js/prefixfree.min.js'></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">
   <h1>找回密码界面</h1>
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
   &emsp;&emsp;<a href="${pageContext.request.contextPath }/login.jsp">返回登录界面</a><br/><br/>
  		 
  		<div style="text-align:center;">
		<span style="font-size:20px;color:black;font-weight:bold;">${str }<br/>
		</span>
		</div>
		
   	<div style="text-align:center;">
		<form action="${pageContext.request.contextPath }/FindPwdServlet " method="post">
		<ul>
		<li>账号：&emsp;<input type="text" placeholder="账号"  name="account" maxlength="20" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');" ><br/></li>
		<li>姓名：&emsp;<input type="text" placeholder="姓名" name="name" maxlength="10"  required onkeyup="this.value=this.value.replace(/(^\s+)|(\s+$)/g,'');"><br/></li>
		<li>手机号：<input type="text" placeholder="注册时填写的手机号码" maxlength="11" name="tel" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');" ><br/><br/></li>
		
		<li>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="submit" value="点击验证"></li>
		</ul>
		</form>
	</div>
</body>
</html>