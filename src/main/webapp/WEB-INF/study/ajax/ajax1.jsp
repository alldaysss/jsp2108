<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>
    <%@include file="/include/bs4.jsp" %>
    <script>
    	function idCheck() {
    		var mid = searchForm.mid.value;
    		if(mid == "") {
    			alert("아이디를 입력하세요.");
    			searchForm.mid.focus();
    			return false;
    		}
    		
    		var query = {
    				mid : mid
    		}
    		
    		// alert("입력된 아이디 : " + mid);
    		//$.ajax(변수1:값1,변수2:값2);
    		$.ajax({ // 전송방식과 URL 써줌
    			type : "get",
    			url : "${ctp}/ajax1Ok",
    			//data : {mid:'hkd1234',age:22,......}
    			data : query, // data : {mid:mid},
    			contentType : "application/json",
    			charset : "utf-8",
    			success : function(data) {
    				//성공적으로 ajax(비동기식)처리 끝내고 돌아왔을경우 수행하는곳
    				if(data =="") {
    					alert("검색된 성명이 없습니다.");
    				}
    				else {
    					alert("검색된 성명은? " + data);    					
    				}
    			},
    			error : function(data) {
    				//성공적으로 ajax(비동기식)처리 실패 후 돌아왔을경우 수행하는곳
    				alert("검색식 오류발생!")
    			}
    		});
    	}
    	
    	// aJax로 User등록 시키기
    	function inputCheck() {
			var name = $("#name").val()
			var age = $("#age").val()
			if(name == "") {
				alert("성명을 입력하세요!");
				$("#name").focus();
				return false;
			}
			
			var query = {
				name : name,
				age : age
			}
			
			$.ajax({
				type : "post",
				url : "${ctp}/userInput",
				data : query,
				success : function(data) {
					if(data == "1") {
						alert("등록 성공!");
						location.reload();
					}
				}
			});
		}
    	// 유저 전체 조회 
    	function listCheck() {   		
			$.ajax({
				type : "post",
				url : "${ctp}/userList",
				success : function(data) {
					if(data == "1") {
					alert("전체자료를 출력합니다.");						
					location.reload();
					}
				}
			});
		}
    	
    	// 자료삭제
    	function delCheck(idx) {
			var ans = confirm("자료를 삭제하시겠습니까?");
			// if(ans) location.href = "${ctp}/userDelete?idx="+idx;
			var query = {
					idx : idx
			}
			
			$.ajax({
				type : "get",
				url : "${ctp}/userDelete",
				data : query,
				success: function(res) {
					if(result == 1) {
						alert("자료가 삭제되었습니다.");
						location.reload();
					}
				},
				error: function() {
					alert("삭제 오류 !!!")
				}
			});
		}

    </script>
    <style>
    	th, td {
    		text-align: center;
    		border: 1px solid #ccc;
    	}
    	th {
    		background-color: #eee;
    	}
    </style>
</head>
<body>
<%@ include file="/include/header_home.jsp" %> 
<jsp:include page="/include/nav.jsp"/>
<p><br/></p>
	<div class="container">
		<h2>Ajax연습</h2>
		<form name="searchform">
			아이디 : <input type="text" name="mid"/>
			<input type="button" value="아이디검색" onclick="idCheck()"/>
		</form>
	</div>
	<br/>
	<form name="inputForm">
		<table class="table table-bordered">
			<tr>
				<td colspan="2"><h3>User 가입</h3></td>
			</tr>
			<tr>
				<th>성명</th>
				<th><input type="text" name="name" id="name" class="form-control"></th>
			</tr>
			<tr>
				<th>나이</th>
				<th><input type="number" name="age" id="age" value="20" class="form-control"></th>
			</tr>
			<tr>
				<th colspan="2" style="text-align: center;">
				<input type="button" value="등록" onclick="inputCheck()" class="btn btn-secondary"/>
				<input type="reset" value="다시입력" class="btn btn-secondary"/>
				<input type="button" value="User조회" onclick="listCheck()" class="btn btn-secondary"/>
				</th>
			</tr>
		</table>
	</form>
	<hr/>
	<!-- 출력 -->
	<h3>User 전체 리스트</h3>
	<table class="table">
		<tr>
			<th>번호</th>
			<th>성명</th>
			<th>나이</th>
			<th>처리</th>
		</tr>
		<c:forEach var="vo" items="${vos}">
		<tr>
			<th>${vo.idx}</th>
			<th>${vo.name}</th>
			<th>${vo.age}</th>
			<th>
				<a href="${ctp}/userUpdate?idx=${vo.idx}" class="btn btn-secondary btn-sm">수정</a> &nbsp;
				<a href="javascript:delCheck(${vo.idx})" class="btn btn-secondary btn-sm" >삭제</a>
			</th>	
		</tr>
		</c:forEach>
	</table>
<%@ include file="/include/footer.jsp" %> 
</body>
</html>