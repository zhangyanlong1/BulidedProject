<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
     <%@  taglib  prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
     <%@  taglib  prefix="f"   uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link  href="/resource/bootstrap-4.3.1/css/bootstrap.css"  rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<body>
<a  href="login">返回登录界面</a>
<nav class="nav fixed-top justify-content-center" style="height:50px;margin-top: 100px " > 注册界面 </nav>
<div class="container-fulid text-center" style="margin-top:200px;height:200px">
	<div  class="container">
		<div  class="row">
			<div class="col-md-6 offset-3">
				<f:form  action="register"  method="post"  modelAttribute="user"  min="2"  max="8"  id="form">
					${err }
					<div>
					<f:input path="username"   class="form-control"    aria-describedby="emailHelp" remote="/user/checkname" />
					<f:errors path="username"></f:errors>
					</div>
					<div>
					<f:input path="password"  class="form-control"   aria-describedby="emailHelp"/>
					<f:errors path="password"></f:errors>
					</div>
							<input type="submit"  value="注册"  class="btn">
							<input type="button" class="btn" onclick="add()" value="提交"/>
				</f:form>	
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">
		$("#form").validate();
		function add(){
			alert('校验开始')
			$("#form").valid();
			alert('校验结束')
		}
	</script>
</body>
</html>