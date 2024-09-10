package kr.co.iei.notice.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.notice.model.dto.Notice;
import kr.co.iei.notice.model.dto.NoticeComment;
import kr.co.iei.notice.model.dto.NoticeCommentRowMapper;
import kr.co.iei.notice.model.dto.NoticeFile;
import kr.co.iei.notice.model.dto.NoticeFileRowMapper;
import kr.co.iei.notice.model.dto.NoticeRowMapper;

@Repository
public class NoticeDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private NoticeRowMapper noticeRowMapper;
	@Autowired
	private NoticeFileRowMapper noticeFileRowMapper;
	@Autowired
	private NoticeCommentRowMapper noticeCommentRowMapper;
	
	public List selectNoticeList(int start, int end) {
	String query =
	"select * from(select rownum as rnum, n.* from (select * from notice order by 1 desc)n)where rnum between ? and ?";
	
	Object[] params = {start,end};
	List list = jdbc.query(query, noticeRowMapper, params);
		return list;
	}
	public int selectNoticeToTalCount() {
		String query= "select count(*) from notice";
		//조회결과가 단일값(1행 1열)인경우에만 사용할 수 있는 메소드
		int totalCount = jdbc.queryForObject(query,Integer.class);
		
		return totalCount;
	}
	public int insertNotice(Notice n) {
		String query = "insert into notice values(notice_seq.nextval,?,?,?,0,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {n.getNoticeTitle(),n.getNoticeWriter(),n.getNoticeContent()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int insertNoticeFile(NoticeFile noticeFile) {
		String query = "insert into notice_file values(notice_file_seq.nextval,?,?,?)";
		Object[] params = {noticeFile.getNoticeNo(),noticeFile.getFilename(),noticeFile.getFilepath()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectNoticeNo() {
		String query = "select max(notice_no) from notice";
		int noticeNo = jdbc.queryForObject(query, Integer.class);
		
		return noticeNo;
	}
	public Notice selectOneNotice(int noticeNo) {
		String query = "select * from notice where notice_no=? ";
		Object[] params = {noticeNo};
		List list = jdbc.query(query,noticeRowMapper,params);
		if(list.isEmpty()) {
			return null;			
		}else {
			return(Notice)list.get(0);
		}
	}
	public int updateReadCount(int noticeNo) {
		String query = "update notice set read_count = read_count+1 where notice_no=?";
			Object[] params = {noticeNo};
			int result = jdbc.update(query,params);
		return result;
	}
	public List selectNoticeFile(int noticeNo) {
		String query ="select * from notice_file where notice_no=?";
		Object[] params = {noticeNo};
		List list = jdbc.query(query,noticeFileRowMapper,params);
		return list;
	}
	public int deleteNotice(int noticeNo) {
		String query="delete from notice where notice_no=?";
		Object[] params = {noticeNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int updateNotice(Notice n) {
		String query = "update notice set notice_title=?, notice_content=?, read_count=read_count-1 where notice_no=?";
		Object[] params = {n.getNoticeTitle(),n.getNoticeContent(),n.getNoticeNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public NoticeFile selectOneNoticeFile(int fileNo) {
		String query = "select * from notice_file where file_no=?";
		Object[] params = {fileNo};		
		List list = jdbc.query(query, noticeFileRowMapper, params);
		return (NoticeFile)list.get(0);	
	}
	public int deleteNoticeFile(int fileNo) {
		String query = "delete from notice_file where file_no=?";
		Object[] params = {fileNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int insertComment(NoticeComment nc) {
		System.out.println(nc);
		String query = "insert into notice_comment values(notice_comment_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),?,?)";
		String noticeCommentRef = nc.getNoticeCommentRef() ==0 ? null : String.valueOf(nc.getNoticeCommentRef());
		Object[] params = {nc.getNoticeCommentWriter(),nc.getNoticeCommentContent(),nc.getNoticeRef(), noticeCommentRef};
		int result = jdbc.update(query,params);
			
		return result;
	}
	public List selectCommentList(int noticeNo, int memberNo) {
		String query = "select \r\n" + 
				"    nc.*,\r\n" + 
				"    (select count(*) from notice_comment_like where notice_comment_no=nc.notice_comment_no) as like_count,\r\n" + 
				"    (select count(*) from notice_comment_like where notice_comment_no=nc.notice_comment_no and member_no=?)as is_like\r\n" + 
				"from notice_comment nc\r\n" + 
				"where notice_ref=? and notice_comment_ref is null order by 1";
		Object[] params = {memberNo, noticeNo};
		List list = jdbc.query(query,noticeCommentRowMapper,params);
		return list;
	}
	public List selectReCommentList(int noticeNo, int memberNo) {
		String query = "select \r\n" + 
				"    nc.*,\r\n" + 
				"    (select count(*) from notice_comment_like where notice_comment_no=nc.notice_comment_no) as like_count,\r\n" + 
				"    (select count(*) from notice_comment_like where notice_comment_no=nc.notice_comment_no and member_no=?)as is_like\r\n" + 
				"from notice_comment nc\r\n" + 
				"where notice_ref=? and notice_comment_ref is not null order by 1";
		Object[] params = {memberNo,noticeNo};
		List list = jdbc.query(query,noticeCommentRowMapper,params);
		return list;
	}
	public int updateComment(NoticeComment nc) {
		String query = "update notice_comment set notice_comment_content=? where notice_comment_no = ?"; 
		Object[] params = {nc.getNoticeCommentContent(),nc.getNoticeCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteComment(NoticeComment nc) {
		String query = "delete from notice_comment where notice_comment_no =?";
		Object[] params = {nc.getNoticeCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int insertNoticeCommentLike(int noticeCommentNo, int memberNo) {
	String query ="insert into notice_comment_like values(?,?)";
	Object[] params = {noticeCommentNo,memberNo};
	int result = jdbc.update(query,params);
		return result;
	}
	public int deleteNoticeCommentLike(int noticeCommentNo, int memberNo) {
		String query = "delete from notice_comment_like where notice_comment_no=? and member_no=?";
		Object[] params = {noticeCommentNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectNoticeCommentLikeCount(int noticeCommentNo) {
		String query = "select count(*) from notice_comment_like where notice_comment_no=?";
		Object[] params = {noticeCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		
		return likeCount;
	}
	public int selectNoticeCommentIsLike(int noticeCommentNo, int memberNo) {
		String query = "select count(*) from notice_comment_like where notice_comment_no=? and member_no=? ";
		Object[] params = {noticeCommentNo,memberNo};
		int isLike =jdbc.queryForObject(query, Integer.class,params);
		return isLike;
	}
}
