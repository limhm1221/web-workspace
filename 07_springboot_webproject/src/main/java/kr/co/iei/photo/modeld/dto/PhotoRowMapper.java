package kr.co.iei.photo.modeld.dto;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoRowMapper implements RowMapper<Photo>{

	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo p = new Photo();
		p.setPhotoContent(rs.getString("photo_content"));
		p.setPhotoImg(rs.getString("photo_img"));
		p.setPhotoNo(rs.getInt("photo_no"));
		p.setPhotoTitle(rs.getString("photo_title"));
		p.setPhotoWriter(rs.getString("photo_writer"));
		return p;
	}

}
