package com.ymw.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyRepository {

	@Insert("""
			INSERT INTO reply
				SET regDate = NOW(),
					updateDate = NOW(),
					memberId = #{loginedMemberId},
					relTypeCode = #{relTypeCode},
					relId = #{relId},
					`body` = #{body}
			""")
	void writeReply(int loginedMemberId, String relTypeCode, int relId, String body);

	@Select("""
			SELECT LAST_INSERT_ID();
			""")
	int getLastInsertId();

}