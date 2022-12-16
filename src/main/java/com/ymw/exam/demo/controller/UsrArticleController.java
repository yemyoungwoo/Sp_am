package com.ymw.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.exam.demo.service.ArticleService;
import com.ymw.exam.demo.util.Utility;
import com.ymw.exam.demo.vo.Article;
import com.ymw.exam.demo.vo.ResultData;
import com.ymw.exam.demo.vo.Rq;


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

		Rq rq = (Rq) req.getAttribute("rq");

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

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);

		if (actorCanMDRd.isFail()) {
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}

		articleService.deleteArticle(id);

		return Utility.jsReplace(Utility.f("%d번 게시물을 삭제했습니다", id), "list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);

		if (actorCanMDRd.isFail()) {
			return rq.jsReturnOnView(actorCanMDRd.getMsg(), true);
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getArticle(id);

		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);

		if (actorCanMDRd.isFail()) {
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Utility.jsReplace(Utility.f("%d번 게시물을 수정했습니다", id), Utility.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/detail")
	public String ShowDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

}