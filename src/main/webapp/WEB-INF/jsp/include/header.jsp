<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<header class="bg-secondary pl-3 pt-2 pb-2">
	<div class="h-100 header d-flex justify-content-between">
		<%-- logo --%>
		<div class="logo d-flex align-items-center">
			<h1 class="text-white ml-3">Marondalgram</h1>
		</div>
		
		<%-- 로그인 정보 --%>
		<div>
			<%-- 로그인이 된 경우에만 로그인 정보와 로그아웃 노출 --%>
			<c:if test="${not empty userName}">
			<span>${userName}님 안녕하세요</span>
			<a href="/user/sign_out">로그아웃</a>
			</c:if>
		</div>
	</div>
</header>