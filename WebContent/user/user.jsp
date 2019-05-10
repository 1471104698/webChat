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
		var iden='${sessionScope.user.iden}';
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
							$("#"+"friend"+gname).append("<img src='../"+v.pic+"' width='"+30+"px"+"' height='"+30+"px'>"+""+v.nickName+"");	
						}else{
							$("#"+"friend"+gname).append("<img src='../"+v.pic+"' width='"+30+"px"+"' height='"+30+"px'>"+""+v.name+"");	
						}

						$("#"+"friend"+gname).append(
								"<input type=button onclick='Chat("+v.id+")'  value='私聊'>"+" "+
								 "<input type=button  onclick='Del("+v.id+")' value='删除好友'>"+
								"<input type=button onclick='See("+v.id+")'  value='查看信息'>"+
								"<br/>"							
						);
					});	
				}
			});
		}			
		function Chat(fid){					//与好友私聊
				window.location.href="${pageContext.request.contextPath}/chat/oneChat.jsp?fid="+fid; 
			}
		function Del(u){				//删除好友
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
		function See(u){				//查看好友信息
			window.location.href="${pageContext.request.contextPath}/FriendServlet?fid="+u+"&uid="+uid+"&ch="+3
		}		
	
	function MoGroup(oldGroupName){					//修改分组名
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
				else if(result=="false")
					alert("修改失败");
				else
					alert("分组名已存在");
			}			
		});
		}
	}
	
	function Create(){					//创建分组

		var groupName=$("#create").val();
		if(groupName==""||groupName==undefined)
		{
		alert("分组名不能为空");
		return;
		}
		if(groupName=='我的好友'){
			alert("请勿修改为默认分组名");
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
				else if(result=="false")
					alert("创建分组失败");
				else
					alert("分组名已存在");
			}			
		});
					
	}
	
	function DeleteGroup(groupName){			//删除分组
		var flag=window.confirm("选定的分组将被删除，组内若有好友则将被移至默认分组'我的好友'   ,确定删除该分组么");
		if(flag){
			if(groupName=='我的好友'){
				alert("默认分组名不能进行修改");
				return;
			}
			$.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/FriendServlet',
				async:true,		//异步
				data:{"gname":groupName,"uid":uid,"gh":"3"},
					
				success:function(result){
					if(result=="true"){
						alert("分组删除成功");
					window.location.href="${pageContext.request.contextPath}/user/user.jsp"
					}
					else
						alert("分组删除失败");
				}			
			});
		}
		
	}
		
	function Out(){				//退出账号
		var flag=window.confirm("你真的要退出么？？？");
		if(flag){
			window.location.href="${pageContext.request.contextPath }/UserOutServlet"; 
		}
	}
	
	function Join(data){			//进入群聊
		//alert(data);
		if(null!=data)
		window.location.href="${pageContext.request.contextPath }/chat/chat.jsp?groupId="+data; 
	}
	
	
	function Search(){				//找群

		var value=$("#find").val(); 
		if(""==value){
			alert("查询条件不能为空");
			return;
		}
		//alert(value);
		 $("#groupChat").html("");			//清空
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/GroupChatServlet',
			async:true,		//异步
			data:{"value":value,"way":"1"},
				
			success:function(data){
				var groups=eval("("+data+")");//处理，将json字符串转换为对象  
				//alert(data);
				 $("#groupChat").html("");			//清空
				$.each(groups, function (i, v) {
					$("#groupChat").append(v.name+"("+v.id+")"+"<input type=button onclick='Attend("+v.id+")'  value='加群'>"+
							"<input type=button onclick='SeeGroupChat("+v.id+")' value='查看群信息'>"+
							"<br/>"
					);			
				});	
			}
		});	
	
	}
	function Attend(data){		//加群,此处值为群id
		var flag=window.confirm("是否加入该群？？？");
		if(flag){
			$.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/GroupChatServlet',
				async:true,		//异步
				data:{"value":data,"uid":uid,"way":"2"},
				success:function(result){
					if(result=="true"){
						alert("加群成功");
						window.location.href="${pageContext.request.contextPath }/user/user.jsp"; 
					}else if(result=="false")
						alert("加群失败");
					else
						alert("你已是该群群员，无需重复加入");
				}			
			}); 
		}
	}
	
	function OutGroupChat(data){		//退群,此处值为群id
		var flag=window.confirm("确定退群？？？");
		if(flag){
			$.ajax({
				method:'post',
				url:'${pageContext.request.contextPath}/GroupChatServlet',
				async:true,		//异步
				data:{"value":data,"uid":uid,"way":"3"},
				success:function(result){
					if(result=="true"){
						alert("成功退出该群");
						window.location.href="${pageContext.request.contextPath }/user/user.jsp"; 
					}else
						alert("退群失败");
				}			
			}); 
		}
	}
	
	function CreateGroup(){
		var value=$("#createGroup").val();
		if(""==value){
			alert("群名不能为空");
			return;
		}
		var flag=window.confirm("是否创建");
		if(flag){
		$.ajax({
			method:'post',
			url:'${pageContext.request.contextPath}/GroupChatServlet',
			async:true,		//异步
			data:{"groupName":value,"uid":uid,"way":"4"},
			success:function(result){
				if(result=="true"){
					alert("成功创建");
					window.location.href="${pageContext.request.contextPath }/user/user.jsp"; 
				}else
					alert("创建失败");
			}			
		}); 
		}
	}
	
	function upload(){
		var fileName=$("#file").val();
		if(null==fileName)
		return false;
		else
		return true;
	}
		
</script>
</head>
<body>

	<c:if test="${sessionScope.user.iden eq 0 }">
	<h1>用户界面</h1>
	<hr/>
	<h3>用户名：${sessionScope.user.name}</h3>		
	
	<img src="${pageContext.request.contextPath}/${user.pic }" width="100px" height="100px">
	<form action="${pageContext.request.contextPath}/PicServlet" enctype="multipart/form-data" onsubmit="return upload()">
	<input type="file" id="file" value="选择头像"  value="upload/male.png">
	<input type="submit" value="点击上传">
	</form>
	<br/>
	
	<a href="${pageContext.request.contextPath}/features/updateInfo.jsp">修改个人信息</a><br/>
	<a href="${pageContext.request.contextPath}/features/updatePwd.jsp">修改密码</a><br/><br/><br/>
	   <div style="float:left;"> <span onclick="Out()">退出账号</span></div><br/><br/>
	   <br/>	
	<h2>我加入的群聊：</h2>
	<c:forEach items="${user.groupChats }" var="groupChat">
	${groupChat.name }(${groupChat.id })
	<button onclick="Join('${groupChat.id }')">进入群聊</button>
	<button onclick="OutGroupChat('${groupChat.id }')">退群</button><br/>
	</c:forEach>
	<br/>

	<h3>创建群聊：</h3>
	输入群名称：
	<input id="createGroup"><button onclick="CreateGroup()">点击创建</button><br/>
	
	
	<h3>找群：</h3>
	<input id="find">
	<button onclick="Search()">点击查找</button>
	<div  style="width: 400px; height: 100px; overflow: scroll; border: 1px solid;" 
        id="groupChat"></div><br/><br/>
	<div>   
	
	
	
	<input id="create"><button onclick="Create()">创建分组</button>
	<h2>好友列表：</h2>
	<c:forEach items="${user.groups }" var="group">
		<br/><input type="button" onclick="Get('${group.name }')" id="" value="${group.name }"><font color=red>(点击查看该分组下的好友)</font>
		<input id="${group.name }"><button onclick="MoGroup('${group.name }')">修改分组名</button>
		<button onclick="DeleteGroup('${group.name }')">删除分组</button>
		<div  style="width: 400px; height: 100px; overflow: scroll; border: 1px solid;" 
        id='friend${group.name }'></div>   
	</c:forEach>
  </div>
  </c:if>
  
  <c:if test="${sessionScope.user.iden eq 1 }">
	<h1>管理员界面</h1>
	<hr/>
	<img src="${pageContext.request.contextPath}/${user.pic }" width="100px" height="100px"><br/>
	<a href="${pageContext.request.contextPath}/PageServlet?way=3">查看所有群</a><br/>
	<a href="${pageContext.request.contextPath}/PageServlet?way=4">查看所有用户</a><br/>
	<div style="float:left;"> <span onclick="Out()">退出账号</span></div><br/><br/>
  </c:if>
</body>
</html>