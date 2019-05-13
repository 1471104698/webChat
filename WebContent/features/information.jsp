<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改昵称</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function MoName(){
	var name=$("#moName").val();
	var fid=${friend.id};
	var uid=${sessionScope.user.id};
	if(name=="")
		return;
	if(fid==uid){
		alert("不能修改自己！！！");
		return;
	}
	$.ajax({
		method:'post',
		url:'${pageContext.request.contextPath}/FriendServlet',
		async:true,		//异步
		data:{"fid":fid,"uid":uid,"nickName":name,"ch":4},
			
		success:function(result){
			if(result=="false")
				alert("修改失败！！！");
			else{
				alert("修改成功！！！");
				window.location.href="${pageContext.request.contextPath}/features/information.jsp"
			}
		}	
	});
}

function MoGroup(){
	var group=$("#move").val();
	if(group==0){
		return;
	}
	var fid=${friend.id};
	var uid=${sessionScope.user.id};
	if(fid==uid){
		alert("不能移动自己！！！");
		return;
	}
	$.ajax({
		method:'post',
		url:'${pageContext.request.contextPath}/FriendServlet',
		async:true,		//异步
		data:{"fid":fid,"uid":uid,"group":group,"ch":5},
			
		success:function(result){
			if(result=="false")
				alert("对方不是你的好友！！！");
			else{
				alert("移动成功！！！");
				window.location.href="${pageContext.request.contextPath}/user/user.jsp"
			}
		}	
	});
}

</script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">

<h1>信息界面</h1>
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
	<div>
	<table border="1">
		<thead>
		<tr>		<!-- tr是行 td是列 -->
		<th>    </th>
		<th>账号</th>
		<th>姓名</th>	
		<th>昵称</th>
		<th>性别</th>
		<th>年龄</th>
		<th>手机号码</th>		
		<th>个性签名</th>
		</tr>
		</thead>
		<tbody>
		<tr>
		<td><img src="${pageContext.request.contextPath}/${friend.pic }" width="60px" height="60px"></td>
		<td>${friend.account}</td>
		<td>${friend.name}</td>
		<td>${friend.nickName}</td>
		<td>${friend.sex}</td>
		<td>${friend.age}</td>
		<td>${friend.tel}</td>			
		<td>${friend.signature}</td>
		</tr>	
		</tbody>
		</table>
		<input  id="moName" /><input type="button" onclick="MoName()" value="修改昵称">
		<br/>
		==修改分组：==
		<select name="schoolId" id="move" style="width: 95%">
             <option value="0" selected='selected'>==选择要移动到的好友分组==</option>

             <c:forEach items="${sessionScope.user.groups }" var="group">
                 <option value="${group.name }"> ${group.name }</option>
             </c:forEach>
         </select>
         
		<input type="button" onclick="MoGroup()" value="点击修改">
	</div>	
</body>
</html>