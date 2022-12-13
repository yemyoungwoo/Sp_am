package com.cjh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.exam.demo.service.ArticleService;
import com.cjh.exam.demo.util.Utility;
import com.cjh.exam.demo.vo.Article;
import com.cjh.exam.demo.vo.ResultData;
import com.cjh.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	private ArticleService articleService;

	@Autowired
	public UsrArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {

		Rq rq = new Rq(req);

		if (rq.getLoginedMemberId() == 0) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}

		if (Utility.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Utility.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

		Article article = articleService.getArticle((int) writeArticleRd.getData1());

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "article", article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {

		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = new Rq(req);

		if (rq.getLoginedMemberId() == 0) {
			return Utility.jsHistoryBack("로그인 후 이용해주세요");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Utility.jsHistoryBack(Utility.f("%d번 게시물은 존재하지 않습니다", id));
		}

		if (rq.getLoginedMemberId() != article.getMemberId()) {
			return Utility.jsHistoryBack("해당 게시물에 대한 권한이 없습니다");
		}

		articleService.deleteArticle(id);

		return Utility.jsReplace(Utility.f("%d번 게시물을 삭제했습니다", id), "list");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = new Rq(req);
		
		if (rq.getLoginedMemberId() == 0) {
			return ResultData.from("F-A", "로그인 후 이용해주세요");
		}

		Article article = articleService.getArticle(id);

//		if (article == null) {
//			return ResultData.from("F-1", Utility.f("%d번 게시물은 존재하지 않습니다", id));
//		}

		ResultData actorCanModifyRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
	}

	@RequestMapping("/usr/article/detail")
	public String ShowDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = new Rq(req);
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

}