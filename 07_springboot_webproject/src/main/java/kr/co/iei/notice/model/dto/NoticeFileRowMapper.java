package kr.co.iei.notice.model.dto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class NoticeFileRowMapper implements RowMapper<NoticeFile> {
	@Override
	public NoticeFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		NoticeFile noticeFile = new NoticeFile();
		noticeFile.setFilename(rs.getString("filename"));
		noticeFile.setFileNo(rs.getInt("file_no"));
		noticeFile.setFilepath(rs.getString("filepath"));
		noticeFile.setNoticeNo(rs.getInt("notice_no"));
		return noticeFile;
	}
}