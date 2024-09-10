package kr.co.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.board.model.dao.BoardDao;
import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardFile;
import kr.co.iei.notice.model.dto.Notice;
import kr.co.iei.notice.model.dto.NoticeFile;
import kr.co.iei.notice.model.dto.NoticeListData;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public List selectBoardeList(int reqPage) {
		int numPerPage = 5;
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage +1;
		
		List list = boardDao.selectBoardList(start, end);
		
		int totalCount = boardDao.selectBoardTotalCount();
		
		int totalPage = 0;
		if(totalCount%numPerPage==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage= totalCount/numPerPage +1;
		}
		int pageNaviSize = 5;
int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		//html코드작성(페이지네비게이션 작성)
		String pageNavi = "<ul class='pagination circle-style'>";
		
		//이전버튼(1페이지로 시작하지 않으면)
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi +="<a class ='page-item' href='/board/list?reqPage="+(pageNo-1)+"'>";	
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		
		for(int i=0 ; i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi +="<a class ='page-item active-page' href='/board/list?reqPage="+pageNo+"'>";				
			}else {
				pageNavi +="<a class ='page-item' href='/board/list?reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			//페이지를 만들다가 최종페이지를 출력했으면 더이상 반복하지 않고 종료
			if(pageNo > totalPage) {
				break;
			}		
		}
		//다음 버튼 생성(최종페이지를 출력하지 않았으면)
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi +="<a class ='page-item' href='/board/list?reqPage="+pageNo+"'>";	
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
			
			
		pageNavi += "</ul>";
		
		//컴트롤러로 되돌려줄 데이터가 공지사항 목록, 만든 페이지 네비게이션
		// - > java의 메소드는 1개의 자료형만 리턴 가능 -> 2개를 되돌려줘야함 list, String
		// - > 되돌려주고싶은 데이터 2개를 저장할 수 있는 객체를 생성해서 객체로 묶어서 하나로 리턴
		NoticeListData nld = new NoticeListData(list,pageNavi);
		
		return list;
	}
	
	@Transactional
	public int insertboard(Board b, List<BoardFile> fileList) {
		int result = boardDao.insertBoard(b);
		if(result > 0) {
			int boardNo = boardDao.selectBoardNo();
			
			for(BoardFile boardFile : fileList) {
				boardFile.setBoardNo(boardNo);
				result +=boardDao.insertBoardFile(boardFile);
			}
		}
		return result;
	}

}
