<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="CHAT" />
<%@ include file="../common/head.jsp"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
					<input class="ml-2 w-84 input input-bordered" type="text" name="searchKeyword" placeholder="채팅방을 검색해보세요" maxlength="20"  />
					<button class="ml-2 btn btn-active btn-ghost">검색</button>
						<td>실시간으로 소통해보세요</td>
					</tr>
				</tbody>
			</table>
		</div>
			<div>
				<td colspan="2" ><input class="btn"  type="submit" value="방 만들기"/></td>
				<td colspan="2"><input class="btn btn-active btn-ghost" type="submit" value="회원가입"/></td>
			</div>
		<div class="btns mt-2 flex justify-between">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	</div>
</section>	
<%@ include file="../common/foot.jsp"%>