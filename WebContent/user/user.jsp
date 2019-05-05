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
		var name='${sessionScope.user.name}';
		function Get(gname){						//显示该列表下的好友
			$.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/FriendServlet',
				async:true,		//异步
				data:{"gname":gname,"gh":"0"},
				success:function(data){
					//alert(data);
					var majorList=eval("("+data+")");//处理，将json字符串转换为对象    
					 $("#"+"friend"+gname).html("");			//清空
					$.each(majorList, function (i, v) {
						if(v.nickName!=""&&v.nickName!=null){
							$("#"+"friend"+gname).append(v.nickName+"");	
						}else{
							$("#"+"friend"+gname).append(v.name+"");	
						}

						$("#"+"friend"+gname).append("<input type=button onclick='Chat("+v.id+")'  value='私聊'>"+" "+
								 "<input type=button  onclick='Del("+v.id+")' value='删除好友'>"+
								"<input type=button onclick='See("+v.id+")'  value='查看信息'>"+
								"<br/>"							
						);
					});	
				}
			});
		}		
		function Chat(fid){		
				window.location.href="${pageContext.request.contextPath}/ChatServlet?fid="+fid; 												
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
						window.location.href="${pageContext.request.contextPath}/user/user.jsp"; 
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
	
	function MoGroup(oldGroupName){	
		var flag=window.confirm("是否修改分组？？？");
		if(flag){
		var newGroupName=$("#"+oldGroupName).val();
		if(newGroupName==""||newGroupName==undefined)
		{
		alert("分组名不能为空");
		return;
		}
		if(oldGroupName=="我的好友"){
			alert("默认分组名不能进行修改");
			return;
		}
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"gname":newGroupName,"oldgname":oldGroupName,"uid":uid,"gh":"1"},
				
			success:function(result){
				if(result=="true"){
					alert("修改成功");
				window.location.href="${pageContext.request.contextPath}/user/user.jsp"
				}
				else
					alert("修改失败");
			}			
		});
		}
	}
	
	function Create(){	

		var groupName=$("#create").val();
		if(groupName==""||groupName==undefined)
		{
		alert("分组名不能为空");
		return;
		}
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/FriendServlet',
			async:true,		//异步
			data:{"gname":groupName,"uid":uid,"gh":"2"},
				
			success:function(result){
				if(result=="true"){
					alert("创建分组成功");
				window.location.href="${pageContext.request.contextPath}/user/user.jsp"
				}
				else
					alert("创建分组失败");
			}			
		});
					
	}
		
	function Out(){
		var flag=window.confirm("你真的要退出么？？？");
		if(flag){
			window.location.href="${pageContext.request.contextPath }/UserOutServlet"; 
		}
	}
		
</script>
</head>
<body>

	<h1>用户界面</h1>
	<hr/>
	<h3>用户名：${sessionScope.user.name}</h3>
	<a href="${pageContext.request.contextPath}/features/updateInfo.jsp">修改个人信息</a><br/>
	<a href="${pageContext.request.contextPath}/features/updatePwd.jsp">修改密码</a><br/><br/><br/>
	   <div style="float:right;"> <span onclick="Out()">退出账号</span></div>
	<input id="create"><button onclick="Create()">创建分组</button>
	<h2>好友列表：</h2>
	<c:forEach items="${user.groups }" var="group">
		
		<br/><input type="button" onclick="Get('${group.name }')" id="" value="${group.name }"><font color=red>(点击查看该分组下的好友)</font>
		<input id="${group.name }"><button onclick="MoGroup('${group.name }')">修改分组名</button>
		<div  style="width: 400px; height: 150px; overflow: scroll; border: 1px solid;" 
        id='friend${group.name }'></div>
	</c:forEach>
  
</body>
</html>