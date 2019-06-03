<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${postVo eq null }">
								<h4>작성된 포스트가 없습니다</h4>
								<p></p>
							</c:when>
							<c:otherwise>
								<h4>${postVo.title }</h4>
								<p>
									${postVo.content }
								<p>
							</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:choose>
						<c:when test="${postList eq null }">
							<li>작성된 포스트가 없습니다.</li>
						</c:when>
						<c:otherwise>
							<c:forEach items = '${postList }' var = 'vo'>
								<li><a href="${pageContext.request.contextPath}/${blogVo.id}/${vo.categoryNo}/${vo.no}">${vo.title }</a> <span>${vo.regDate }</span>	</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/assets-upload${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items = '${categoryList }' var = 'vo'>
					<li><a href="${pageContext.request.contextPath}/${blogVo.id}/${vo.no}">${vo.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>