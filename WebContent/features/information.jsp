<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改昵称</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function MoName(){
	var name=$("#mo").val();
	var fid=${friend.id};
	$.ajax({
		method:'post',
		url:'${pageContext.request.contextPath}/MoNameServlet',
		async:true,		//异步
		data:{"fid":fid,"nickName":name,"ch":4},
			
		success:function(result){
			if(result=="false")
				alert("修改失败！！！");
			else{
				alert("修改成功！！！");
			}
		}	
	});
	
	
}

</script>
</head>
<body>
<h1>信息界面</h1>
	<hr/>
	<div>
	<table border="1">
		<thead>
		<tr>		<!-- tr是行 td是列 -->

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
		<input  id="mo" /><button onclick="MoName()">修改昵称</button>
	</div>	
	
</body>
</html>