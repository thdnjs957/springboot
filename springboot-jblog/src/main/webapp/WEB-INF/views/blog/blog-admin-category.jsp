<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src = "${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>

	/* 
		일단 redirect로 하고 ajax로 수정
	$(function(){ 

		$('#add_category').click(function(){
			
			var name = $('#name').val();
			var desc = $('#desc').val();
			
			if(name == '' || desc == ''){
				return;
			}
			
			$.ajax({
				url:"${pageContext.request.contextPath}/${authUser.id}/admin/category",
				type:"post",
				dataType:"json",
				data:"
				
						",
				success:function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
				},
				error:function(xhr,error){
					
				},
			});
			console.log(name);
		});
		
	}); */
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/blog/includes/admin-menu.jsp"/>
		      	<table class="admin-cat">
		      		
		      		<c:set var ='count' value ='${fn:length(categoryList) }' />
		      		
			      	<c:forEach items = '${categoryList }' var = 'vo' varStatus = 'status'>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
						<tr>
							<td>${status.index+1 }</td>
							<td>${vo.name }</td>
							<td>${vo.count }</td>
							<td>${vo.description }</td>
							<td><a href="${pageContext.request.contextPath}/${blogVo.id}/admin/category/delete/${vo.no}"><img src="${pageContext.request.contextPath}/assets/images/delete.jpg" ></a></td>
						</tr>  
					</c:forEach>			  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form name="addCategory" id="addCategory" action="${pageContext.request.contextPath}/${blogVo.id}/admin/category" method="post" >
      				<input type="hidden" name ="blogId" value="${blogVo.id }"/>
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" id = "name" name="name"/></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" id = "desc" name="description"/></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" id ="add_category" value="카테고리 추가"/></td>
			      		</tr>   
		      		</table>
		      	</form> 
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2019
			</p>
		</div>
	</div>
</body>
</html>