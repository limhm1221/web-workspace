package kr.co.iei.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.board.model.dto.BoardDTO;
import kr.co.iei.board.model.dto.BoardFileDTO;

@Mapper
public interface BoardDao {

	List selectBoardList(Map<String, Object> map);

	int totalCount();

	int insertBoard(BoardDTO board);

	int insertBoardFile(BoardFileDTO boardFile);

	BoardDTO selectOneBoard(int boardNo);

	List selectBoardFile(int boardNo);

	int updateBoard(BoardDTO board);

	List<BoardFileDTO> selectDeleteFileList(int[] delFileNo);

	int deleteBoardFile(int[] delFileNo);



}
