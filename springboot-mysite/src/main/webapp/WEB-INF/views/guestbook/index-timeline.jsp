<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook2.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<script>
		
	$(document).ready(function(){
		
		/*
		 * 초기 페이지 로딩시 댓글 불러오기
		 */
		getGuestbookList();
		
	    $("#submit_btn").click(function(){
	        //JSON 객체에 값을 담아줌
	        var json = {
	            name : $("#input-name").val(),
	            password : $("#input-password").val(),
	            contents: $("#tx-content").val()
	        };
	        
	 		//변수명이 JSON에 담아둔 값만큼 포이치문을 돌림
	        for(var str in json){
	            //JSON에 STR의 길이가 0일 경우
	            if(json[str].length == 0){
	                //해당하는 ID에 placeholder를 찾아 경고창을 띄움
	                alert($("#" + str).attr("placeholder") + "를 입력해주세요.");
	                //해당하는 ID에 포커스를 올림
	                $("#" + str).focus();
	                //리턴
	                return;
	            }
	        } 
	        
	        //비동기 요청
	         $.ajax({
	            type : "post", //POST로
	            url : "${pageContext.request.contextPath}/guestbook/list", //URL 지정
	            data : json, //전달값은 JSON
	            success : function(response) { //성공시
	            	if(response.result != "success"){
	            	
						console.error(response.message);
						return;
					}
	            	if(response.data){
	            		getGuestbookList();
						alert("성공했습니다.");
						return;
					}
	            },
	            error:function(xhr,error){
					console.error("error:"+error);	
				}
	        });
	    });
	});
	
	
	function getGuestbookList(){
		
		$.ajax({
	        type:'GET',
	        url : "<c:url value='/board/commentList.do'/>",
	        dataType : "json",
	        data:$("#commentForm").serialize(),
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	        success : function(data){
	            
	            var html = "";
	            var cCnt = data.length;
	            
	            if(data.length > 0){
	                
	                for(i=0; i<data.length; i++){
	                    html += "<div>";
	                    html += "<div><table class='table'><h6><strong>"+data[i].writer+"</strong></h6>";
	                    html += data[i].comment + "<tr><td></td></tr>";
	                    html += "</table></div>";
	                    html += "</div>";
	                }
	                
	            } else {
	                
	                html += "<div>";
	                html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
	                html += "</table></div>";
	                html += "</div>";
	                
	            }
	            
	            $("#cCnt").html(cCnt);
	            $("#commentList").html(html);
	            
	        },
	        error:function(request,status,error){
	            
	       }
	        
	    });
		
	}
	
	

</script>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<img id="guestbookimg" src="${pageContext.request.contextPath }/assets/images/notebook.png">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" id ="submit_btn"/>
				</form>
				<ul id="list-guestbook">
					<li data-no=''>
						<strong>지나가다가</strong>
						<p>
							별루입니다.<br>
							비번:1234 -,.-
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a>
					</li>

					<li data-no=''>
						<strong>둘리</strong>
						<p>
							안녕하세요<br>
							홈페이지가 개 굿 입니다.
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a>
					</li>

					<li data-no=''>
						<strong>주인</strong>
						<p>
							아작스 방명록 입니다.<br>
							테스트~
						</p>
						<strong></strong>
						<a href='' data-no=''>삭제</a>
					</li>


				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="timeline"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
