<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src = "${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	
	$(function(){ 
		
		$("#blog-id").change(function(){ //포커스를 다른곳으로 갈때 다시 검사
			$("#btn-checkemail").show();
			$("#img-checkemail").hide();
		});
		
		$('#btn-checkemail').click(function(){
			var id = $('#blog-id').val();
			if(id == ''){
				return;
			}
			
			//ajax 통신
			$.ajax({
				url:"/jblog2/user/api/checkid?id="+id,
				type:"get",
				dataType:"json",
				data:"",
				success:function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					if(response.data == true){
						alert("이미 존재하는 아이디입니다.\n다른 아이디를 사용해 주세요.");
						$("#blog-id").focus();
						$("#blog-id").val("");
						return;
					}
					
					$("#btn-checkemail").hide();
					$("#img-checkemail").show();
					
				},
				error:function(xhr,error){//내부에서 통신하고 있는 객체(에러내용 더 자세히 보라)
					console.error("error:"+error);					
				},
			
			});
			console.log(id);
		});
		
	});
</script>

</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<ul class="menu">
			<c:choose>
				<c:when test='${empty authUser }'>
					<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
					<li><a href="${pageContext.request.contextPath}/user/join">회원가입</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
					<li><a href="${pageContext.request.contextPath}">내블로그</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		
		<form:form modelAttribute="userVo" id="join-form" name="joinForm" class="join-form" method="post" action="${pageContext.servletContext.contextPath }/user/join">
			<label class="block-label" for="name">이름</label>
			<input id="name" name="name" type="text" value="">
			
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"> 
			
			<spring:hasBindErrors name="userVo">
				<c:if test="${errors.hasFieldErrors('id') }">
					<p style="font-weight:bold; color:red; text-align:left; padding:0">
						 <spring:message 
				     		code="${errors.getFieldError( 'id' ).codes[0] }" 				     
				     		text="${errors.getFieldError( 'id' ).defaultMessage }" />
					</p>
				</c:if>
			</spring:hasBindErrors>
			
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />
			<spring:hasBindErrors name="userVo">
				<c:if test="${errors.hasFieldErrors('password') }">
					<p style="font-weight:bold; color:red; text-align:left; padding:0">
						 <spring:message 
				     		code="${errors.getFieldError( 'password' ).codes[0] }" 				     
				     		text="${errors.getFieldError( 'password' ).defaultMessage }" />
					</p>
				</c:if>
			</spring:hasBindErrors>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
