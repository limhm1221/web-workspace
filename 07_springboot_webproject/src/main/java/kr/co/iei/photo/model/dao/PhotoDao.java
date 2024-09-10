package kr.co.iei.photo.model.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.photo.modeld.dto.Photo;
import kr.co.iei.photo.modeld.dto.PhotoRowMapper;

@Repository
public class PhotoDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private PhotoRowMapper photoRowMapper;
    public int selectTotalCount() {
        String query ="select count(*) from photo";
        int totalCount = jdbc.queryForObject(query,Integer.class);
        return totalCount;
    }
	public int insertPhoto(Photo p) {
		String query = "insert into photo values(photo_seq.nextval,?,?,?,?)";
		Object[] params= {p.getPhotoWriter(),p.getPhotoTitle(),p.getPhotoContent(),p.getPhotoImg()};
		int result = jdbc.update(query,params);
		return result;
	}
	public List selectPhotoList(int start, int end) {
		String query = "select * from (select rownum as rnum, p.* from(select * from photo order by photo_no desc)p) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, photoRowMapper , params);
		return list;
	}
}
