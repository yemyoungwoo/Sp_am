<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jsp"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="mb-2 flex justify-between items-center">
			<div>
				<span>${articlesCount } 개</span>
			</div>
			<c:if test="${rq.getLoginedMemberId() != 0 }">
				<a class="btn-text-link btn btn-active btn-ghost" href="/usr/article/write">WRITE</a>
			</c:if>
		</div>
		<div class="table-box-type-1">
			<table class="table w-full">
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="article" items="${articles}">
						<tr class="hover">
							<td>${article.id}</td>
							<td>${article.regDate.substring(2,16)}</td>
							<td><a class="hover:underline" href="detail?id=${article.id}">${article.title}</a></td>
							<td>${article.writerName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
				<div class="page-menu mt-2 flex justify-center">
			<div class="btn-group">
				<c:forEach begin="1" end="${pagesCount }" var="i">
					<a class="btn btn-sm ${page == i ? 'btn-active' : ''}" href="?boardId=${boardId }&page=${i }">${i }</a>
				</c:forEach>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>