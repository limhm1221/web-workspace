package kr.co.iei.notice.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NoticeCommentRowMapper implements RowMapper<NoticeComment>{

	@Override
	public NoticeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		NoticeComment comment = new NoticeComment();
		comment.setNoticeCommentContent(rs.getString("notice_comment_content"));
		comment.setNoticeCommentDate(rs.getString("notice_comment_date"));
		comment.setNoticeCommentNo(rs.getInt("notice_comment_no"));
		comment.setNoticeCommentRef(rs.getInt("notice_comment_ref"));
		comment.setNoticeCommentWriter(rs.getString("notice_comment_writer"));
		comment.setNoticeRef(rs.getInt("notice_ref"));
		comment.setIsLike(rs.getInt("is_like"));
		comment.setLikeCount(rs.getInt("like_count"));
		return comment;
	}
	
	

}
