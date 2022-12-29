package com.ymw.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.exam.demo.service.ReplyService;
import com.ymw.exam.demo.util.Utility;
import com.ymw.exam.demo.vo.Article;
import com.ymw.exam.demo.vo.Reply;
import com.ymw.exam.demo.vo.ResultData;
import com.ymw.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {

	private ReplyService replyService;
	private Rq rq;

	@Autowired
	public UsrReplyController(ReplyService replyService, Rq rq) {
		this.replyService = replyService;
		this.rq = rq;
	}

	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body) {

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = writeReplyRd.getData1();

		return Utility.jsReplace(Utility.f("%d번 댓글이 생성되었습니다", id), Utility.f("../article/detail?id=%d", relId));
	}
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Reply reply = replyService.getReply(id);

		ResultData actorCanMDRd = replyService.actorCanMD(rq.getLoginedMemberId(), reply);

		if (actorCanMDRd.isFail()) {
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}

		replyService.deleteReply(id);

		return Utility.jsReplace(Utility.f("%d번 댓글을 삭제했습니다", id), Utility.f("../article/detail?id=%d", reply.getRelId()));
	}
	
}