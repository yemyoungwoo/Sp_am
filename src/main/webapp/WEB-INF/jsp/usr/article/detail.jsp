<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jsp"%>

<script>
	const params = {};
	params.id = parseInt('${param.id}')
	
	function ArticleDetail__increaseHitCount() {
		
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		
		if(localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
		
		$.get('doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json')
	}
	
	
	$(function(){
		//실전코드
	// 	ArticleDetail__increaseHitCount()
	
	//연습코드
		setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>번호</th>
						<td><div class="badge">${article.id}</div></td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${article.regDate}</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>${article.sumReactionPoint}</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${article.updateDate}</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>
							<c:if test="${rq.getLoginedMemberId() == 0 }">
								<span class="badge">${article.sumReactionPoint}</span>
							</c:if>
							<c:if test="${rq.getLoginedMemberId() != 0 }">
								<button class="btn btn-xs btn-outline">좋아요 👍</button>
								<span class="badge">좋아요 : ${article.goodReactionPoint}개</span>
								<br />
								<button class="btn btn-xs btn-outline">싫어요 👎</button>
								<span class="badge">싫어요 : ${article.badReactionPoint}개</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.writerName}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title}<tr>
						<th>내용</th>
						<td>${article.body}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="btns">
			<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.actorCanChangeData }">
				<a class="btn-text-link" href="modify?id=${article.id }">수정</a>
				<a class="btn-text-link"
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>