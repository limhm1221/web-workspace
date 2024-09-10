package kr.co.iei.board.model.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.board.model.dao.BoardDao;
import kr.co.iei.board.model.dto.BoardDTO;
import kr.co.iei.board.model.dto.BoardFileDTO;
import kr.co.iei.board.model.dto.BoardPageData;
@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public BoardPageData selectBoardList(int reqPage) {
		int numPerPage = 10; //한페이지당 보여줄 게시물 수
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		//마이바티스에서 데이터를 전달할 때 여러개여도 하나로 묶어서 전달
		//start, end  정수 2개를 전달하고 싶은 상황
		//하나로 묶는 방법 -> 객체를 만들어서 사용 or HashMap
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		List list = boardDao.selectBoardList(map);
		
		//pageNavi 생성
		//-> 총 페이지수 계산을 위해서 총 게시물 수를 조회
		int totalCount = boardDao.totalCount();
		
		//총 페이지 수 계산
		int totalPage = totalCount % numPerPage == 0 ? (totalCount/numPerPage) : (totalCount/numPerPage) + 1;
		
		//페이지 네비 길이
		int pageNaviSize = 5;
		
		//페이징 시작번호
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "";
		//이전 버튼
		if(pageNo != 1) {
			pageNavi += "<a href='/board/list?reqPage="+(pageNo-1)+"'>[이전]</a>";
		}
		//숫자 페이징
		for(int i=0; i<pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span>"+pageNo+"</span>";
			} else {
				pageNavi += "<a href='/board/list?reqPage="+pageNo+"'>"+pageNo+"</a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		//다음 버튼
		if(pageNo<=totalPage) {
			pageNavi += "<a href='/board/list?reqPage="+pageNo+"'>[다음]</a>";
		}
		
		BoardPageData bpd = new BoardPageData(list, pageNavi);
		return bpd;
	}
	
	@Transactional
	public int insertBoard(BoardDTO board, ArrayList<BoardFileDTO> fileList) {
			//board테이블 insert
		int result = boardDao.insertBoard(board);
		if(result>0) {
			//방금 board테이블에 insert된 게시글의 글번호를 조회 
			//int boardNo = boardDao.selectBoardNo();
			for(BoardFileDTO boardFile : fileList) {
				boardFile.setBoardNo(board.getBoardNo());
				result += boardDao.insertBoardFile(boardFile);
			}
		}
		return result;
	}

	public BoardDTO selectOneBoard(int boardNo) {
		BoardDTO board = boardDao.selectOneBoard(boardNo);

		return board;
	}
	
	@Transactional
	public List<BoardFileDTO> updateBoard(BoardDTO board, List<BoardFileDTO> fileList, int[] delFileNo) {
		//Board테이블 수정
		int result = boardDao.updateBoard(board);
		if(result > 0) {
			List<BoardFileDTO> delFileList = new ArrayList<BoardFileDTO>();
			//삭제한 파일이 있는경우
			if(delFileNo != null) {
				//board_file테이블에서 삭제할 파일을 조회
				 delFileList = boardDao.selectDeleteFileList(delFileNo); 
				//board_file테이블에서 삭제할 파일을 삭제
				result += boardDao.deleteBoardFile(delFileNo);
			}
			//추가 첨부파일 insert
			for(BoardFileDTO boardFile : fileList) {
				result += boardDao.insertBoardFile(boardFile);
			}
			//최종 성공/실패 판단
			//삭제 파일이 있는지 없는지에 따라서 다르게 처리
			int updateTotal =(delFileNo == null)? 
												1 + fileList.size() //board테이블 수정 + 추가한 파일
												:
												1 + fileList.size()+delFileNo.length // board테이블 수정 + 추가한 파일 + 지운파일수 
												;
			if(result == updateTotal) {
				return delFileList;
			}else {
				return null;
			}
		}else {
			return null;			
		}
	}


}