<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='${pageContext.request.contextPath}/js/prefixfree.min.js'></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/normalize.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-3.3.1.min.js"></script>
</head>
<body style="background: url(${pageContext.request.contextPath }/images/bg.png) ;background-size:100%">


	<script type="text/javascript">
	
	function check(){
		var reg=/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9]|16[0-9])[0-9]{8}$/;		
		if( !reg.test( $( "#tel" ).val() ) ){
			alert("电话格式错误");
		}else{ 
		var account=$("#account").val();
		var pwd=$("#pwd").val();
		var name=$("#name").val();
		var tel=$("#tel").val();
		var age=$("#age").val();
		var sex= $('input[name="sex"]:checked').val();
		var signature=$("#signature").val();
		$.ajax({		
			url:'${pageContext.request.contextPath}/RegServlet',
			type:'POST',			
			async:true,		
			dataType:'text',
			data:{
				"pwd":$("#pwd").val(),
				"account":$("#account").val(),
				"name":$("#name").val(),
				"tel":$("#tel").val(),
				"age":$("#age").val(),
				"sex":$("#sex").val(),
				"signature":$("#signature").val()
				},
			success:function(result){  
					if(result=="false"){
						alert("用户已存在！！！");
					}
					else{		
						alert("注册成功！！！");
						window.location.href="${pageContext.request.contextPath }/login.jsp"; 
					}						
			}		
		});
		}
		return false;
	}
	
/* change 事件被<input>, <select>, 和<textarea> 元素触发。
触发情况
<input type="radio"> 和 <input type="checkbox"> 的默认选项被修改时（通过点击或者键盘事件）。
 */
/*  
	$("#uploadFile").change(function(){
		var formData = new FormData($('#uploadFile')[0]);		//得到表单属性
		$.ajax({                        //发请求给接口
            cache:false,
            contentType: false,	 //很重要，指定为false才能形成正确的Content-Type
            processData: false,	//很重要，告诉jquery不要对form进行处理
            url:'/Pic',   
            type:'POST',
            enctype:'multipart/form-data',
            data:formData,
            dataType:'JSON',
            success:function (data) {
                if (data.data.imgs.length > 0) {
                    //上存成功
                    var url = data.data.imgs[0]["thumbnail_url"];
                    $("#photo").attr("src", url);		//attr()可以用来修改或者添加属性或者属性值,这里用来修改
                    updateHeadImg(url);
                }
            }
        });
	}); */
	
	</script>
	
   <h1>注册界面</h1>		<hr/>
		
		<div style="text-align:center;">
		<span style="font-size:20px;color:black;font-weight:bold;">${str }<br/>
		</span>
		
		</div>
		 &emsp;&emsp;<a href="${pageContext.request.contextPath }/login.jsp">返回登录界面</a><br/><br/>
		 
		<%--  ${pageContext.request.contextPath }/RegServlet --%>
		<form action=""  onsubmit="return check()">
			<input type="hidden" name="iden" value="0">
			<ul>
				<li>账号：&emsp;&emsp;<input type="text" placeholder="学号" name="account" id="account" maxlength="20"  required onkeyup="this.value=this.value.replace(/[^\w]/g,'');"></li>
				<li>密码：&emsp;&emsp;<input type="password" placeholder="密码" name="pwd"  id="pwd"  maxlength="40" required onkeyup="this.value=this.value.replace(/[^\w]/g,'');"></li>
				<li>姓名：&emsp;&emsp;<input type="text"placeholder="姓名" name="name"   id="name"  required onkeyup="this.value=this.value.replace(/(^\s+)|(\s+$)/g,'');"></li>
				<li>手机号码：<input type="text" placeholder="手机号码" name="tel" id="tel" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');"></li>
				<li>年龄：&emsp;&emsp;<input type="text" placeholder="年龄" name="age"  id="age" required onkeyup="this.value=this.value.replace(/[^\d]/g,'');"></li>
				<li>性别：<br/><input type="radio"  placeholder="性别" name="sex" id="sex"  value="1" checked="checked">男
									  <input type="radio" name="sex"  id="sex" value="2" >女</li>
				
				<li>个性签名：&emsp;&emsp;<br/><textarea cols="50" rows="5"  placeholder="个性签名" name="signature" id="signature"  required></textarea><br/></li>
      
				<li>&emsp;&emsp;&emsp;<input type=submit value="注册">
			<input type="reset"><br></li>
			</ul>	
		</form>
		<form id="uploadFile">
		 <ul><li>点击上传头像：<input type="file" name="file" id="uploadFileInput" style="display:none;"/></li></ul>
		</form>
		<%-- <ul>
		<!-- 当这个人没有头像的时候，放一张提示用户上传的图片 -->
		<li>点击上传头像：<br/><img id="photo" name="photo" src="${pageContext.request.contextPath }/images/default.png" width="130px" height="140px" onclick="$('#uploadFileInput').click();"/>
		</li>
		</ul> --%>
		
</body>
</html>