package kr.co.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardFile;
import kr.co.iei.board.model.dto.BoardFileRowMapper;
import kr.co.iei.board.model.dto.BoardRowMapper;


@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	
	public  List selectBoardList(int start, int end) {
		String query = "select * from board";
		List list = jdbc.query(query,boardRowMapper);
		System.out.println(list);
		return list;
	}
		public int selectBoardTotalCount() {
			String query="select count(*) from board";
			
			int totalCount =jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
		public int insertBoard(Board b) {
			String query= "insert into board values(board_seq.nextval,?,?,?,0,to_char(sysdate,'yyyy-mm-dd'))";
			Object[] params= {b.getBoardTitle(),b.getBoardWriter(),b.getBoardContent()};
			int result = jdbc.update(query,params);
			return result;
		}
		public int selectBoardNo() {
			String query = "select max(board_no) from board";
			int boardNo = jdbc.queryForObject(query, Integer.class);
			return boardNo;
		}
		
		public int insertBoardFile(BoardFile boardFile) {
			String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
			Object[] params = {boardFile.getBoardNo(),boardFile.getFilename(),boardFile.getFilepath()};
			int result = jdbc.update(query,params);
			return result;
		
		}
}
