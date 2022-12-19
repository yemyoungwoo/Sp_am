package com.ymw.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.exam.demo.service.ArticleService;
import com.ymw.exam.demo.service.BoardService;
import com.ymw.exam.demo.util.Utility;
import com.ymw.exam.demo.vo.Article;
import com.ymw.exam.demo.vo.Board;
import com.ymw.exam.demo.vo.ResultData;
import com.ymw.exam.demo.vo.Rq;


@Controller
public class UsrArticleController {
	private ArticleService articleService;
	private BoardService boardService;
	
	private Rq rq;

	@Autowired
	public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.rq = rq;
	}

	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body) {

		if (Utility.empty(title)) {
			return Utility.jsHistoryBack("제목을 입력해주세요");
		}
		if (Utility.empty(body)) {
			return Utility.jsHistoryBack("내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		int id = (int) writeArticleRd.getData1();
		
		return Utility.jsReplace(Utility.f("%d번 글이 생성되었습니다", id), Utility.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		Board board = boardService.getBoardById(boardId);		
		if (board == null) {
			return rq.jsReturnOnView("존재하지 않는 게시판입니다", true);
		}

		int articlesCount = articleService.getArticlesCount(boardId);
		
		List<Article> articles = articleService.getArticles(boardId);
	
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("articlesCount", articlesCount);
		
		return "usr/article/list";
	}
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);
		if (actorCanMDRd.isFail()) {
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		articleService.deleteArticle(id);
		return Utility.jsReplace(Utility.f("%d번 게시물을 삭제했습니다", id), "list?boardId=1");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		
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
	public String doModify(int id, String title, String body) {
		
		Article article = articleService.getArticle(id);
		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);
		if (actorCanMDRd.isFail()) {
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Utility.jsReplace(Utility.f("%d번 게시물을 수정했습니다", id), Utility.f("detail?id=%d", id));
	}
	@RequestMapping("/usr/article/detail")
	public String ShowDetail(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		model.addAttribute("article", article);
		return "usr/article/detail";
	}
}