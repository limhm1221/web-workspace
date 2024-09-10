package kr.co.iei.notice.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.notice.model.dao.NoticeDao;
import kr.co.iei.notice.model.dto.Notice;
import kr.co.iei.notice.model.dto.NoticeComment;
import kr.co.iei.notice.model.dto.NoticeFile;
import kr.co.iei.notice.model.dto.NoticeListData;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;

	public NoticeListData selectNoticeList(int reqPage) {
		//reqPage :  사용자가 요청한 페이지 번호 
		//한 페이지당 보여줄 게시물의 숫자 (지정) -> 10개 
		int numPerPage = 10;
		
		//사용자가 요청한 페이지에 따른 게시물 시작번호/ 끝번호 계산
		//reqPage == 1 -> start = 1  / end=10
		//reqPage == 1 -> start = 11 / end=20
		//reqPage == 1 -> start = 21 / end=30
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		//요청페이지에 필요한 공지사항 목록을 조회
		List list = noticeDao.selectNoticeList(start, end);
		
		//페이지네비게이션(사용자가 클릭해서 다른 페이지를 요청할 수 있도록 하는 요소) 제작
		//페이지네비게이션을 서비스에서 만드는 이유 -> 총 게시물수를 조회해와야 가능하기 때문에 
		//select count(*) from notice
		int totalCount = noticeDao.selectNoticeToTalCount();
		//전체 페이지 수 계산 
		int totalPage = 0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage + 1;
		}
		
		//페이지네비 사이즈 지정
		int pageNaviSize = 5;
		
		//페이지 네비 시작번호를 지정 
		//reqPage 1 ~ 5 : 1 2 3 4 5
		//reqPage 6 ~ 10 : 6 7 8 9 10
		//reqPage 11 ~ 15 : 11 12 13 14 15
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		//html코드작성(페이지네비게이션 작성)
		String pageNavi = "<ul class='pagination circle-style'>";
		
		//이전버튼(1페이지로 시작하지 않으면)
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi +="<a class ='page-item' href='/notice/list?reqPage="+(pageNo-1)+"'>";	
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		
		for(int i=0 ; i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi +="<a class ='page-item active-page' href='/notice/list?reqPage="+pageNo+"'>";				
			}else {
				pageNavi +="<a class ='page-item' href='/notice/list?reqPage="+pageNo+"'>";
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
			pageNavi +="<a class ='page-item' href='/notice/list?reqPage="+pageNo+"'>";	
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
			
			
		pageNavi += "</ul>";
		
		//컴트롤러로 되돌려줄 데이터가 공지사항 목록, 만든 페이지 네비게이션
		// - > java의 메소드는 1개의 자료형만 리턴 가능 -> 2개를 되돌려줘야함 list, String
		// - > 되돌려주고싶은 데이터 2개를 저장할 수 있는 객체를 생성해서 객체로 묶어서 하나로 리턴
		NoticeListData nld = new NoticeListData(list,pageNavi);
		
		return nld;
	}

	@Transactional
	public int insertNotice(Notice n, List<NoticeFile> fileList) {
		//1. notice 테이블 insert
		int result = noticeDao.insertNotice(n);
		if(result > 0) {
			//2. 1번작업으로 insert할때 생성된 번호를 조회
			int noticeNo = noticeDao.selectNoticeNo();
			//3. 반복문으로 Notice_FIle 테이블 Insert
			for(NoticeFile noticeFile : fileList) {
				// 1번작업으로 insert될때 생성된 공지사항번호를 저장 후 notice_file insert요청
				noticeFile.setNoticeNo(noticeNo);
				result +=noticeDao.insertNoticeFile(noticeFile);
			}
		}
		return result;
	}

	@Transactional
	public Notice selectOneNotice(int noticeNo,String check, int memberNo) {
		//게시글 조회
		Notice n = noticeDao.selectOneNotice(noticeNo);
		if(n != null) {
			//조회수 증가
			if(check == null) {
				int result = noticeDao.updateReadCount(noticeNo);
			}
			//해당게시글의 첨부파일을 조회
			List fileList = noticeDao.selectNoticeFile(noticeNo);
			n.setFileList(fileList);
			//댓글 조회(공지사항 상세보기 할때 해당 공지사항의 댓글을 같이 조회) - 기본 댓글만
			List<NoticeComment> commentList = noticeDao.selectCommentList(noticeNo,memberNo);
			
			/*
			for(NoticeComment nc : commentList) {
				int noticeCommentNo = nc.getNoticeCommentNo();
				int likeCount = noticeDao.selectNoticeCommentLikeCount(noticeCommentNo);
				nc.setLikeCount(likeCount);
				int isLike = noticeDao.selectNoticeCommentIsLike(noticeCommentNo, memberNo);
				nc.setIsLike(isLike);
			}
			*/
			
			n.setCommentList(commentList);
			//댓글 조회 - 대댓글만 조회
			List reCommentList = noticeDao.selectReCommentList(noticeNo,memberNo);
			n.setReCommentList(reCommentList);
		}
		return n;
	}

	public List<NoticeFile> deleteNotice(int noticeNo) {
		//Notice테이블에서 공지사항 삭제/NoticeFile에서 해당 공지사항의 첨부파일 삭제 / NoticeFile에서 해당공시자사항의 첨푸파일 조회
		//1. NoticeFile 테이블에서 지우려는 공지사항의 첨부파일을 조회
		List list = noticeDao.selectNoticeFile(noticeNo);
		//2. Notice테이블에서 해당 공지사항 삭제(외래키 옵션으로 Notice에서 삭제되면 Notice_file은 자동삭제)
		int result = noticeDao.deleteNotice(noticeNo);
		if(result>0) {
			return list;
		}
		return null;
	}

	public Notice getOneNotice(int noticeNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		List list = noticeDao.selectNoticeFile(noticeNo);
		n.setFileList(list);
		return n;
	}

	@Transactional
	public List<NoticeFile> updateNotice(Notice n, List<NoticeFile> fileList, int[] delFileNo) {
		List<NoticeFile> delFileList = new ArrayList<NoticeFile>();
		//notice업데이트 , notice_file insert(추가한파일이 있을때만) , notice_file delete(삭제한파일이 있을때만)
		int result = noticeDao.updateNotice(n);
		if(result > 0) {
			//추가한 파일이 있는 경우 추가파일 insert
			for(NoticeFile noticeFile : fileList) {
				result += noticeDao.insertNoticeFile(noticeFile);
			}
			//삭제한 파일이 있는경우 (파일을 DB에서 조회한 후삭제(실제 폴더에서도 지우기위함) -> 삭제 )
			if(delFileNo != null) {

				for(int fileNo : delFileNo) {
					NoticeFile noticeFile = noticeDao.selectOneNoticeFile(fileNo);
					delFileList.add(noticeFile);
					result += noticeDao.deleteNoticeFile(fileNo);
				}
			}
		}
		int updateTotal = delFileNo == null?fileList.size()+1:fileList.size()+1+delFileNo.length;
		if(updateTotal == result) {
			return delFileList;
		}else {
		return null;
	}
}
	@Transactional
	public int insertComment(NoticeComment nc) {
		int result = noticeDao.insertComment(nc);
		return result;
	}
	
	@Transactional
	public int updateComment(NoticeComment nc) {
		int result = noticeDao.updateComment(nc);
		return result;
	}
	
	@Transactional
	public int deleteComment(NoticeComment nc) {
		int result=noticeDao.deleteComment(nc);
		return result;
	}
	
	@Transactional
	public int likePush(int noticeCommentNo, int isLike, int memberNo) {
		int result= 0;
		
		
		if(isLike ==0) {
			//현재 좋아요를 누르지않은 상태에서 클릭 ->좋아요 ->insert
			result = noticeDao.insertNoticeCommentLike(noticeCommentNo,memberNo);
		}else if(isLike == 1){
			//현재 좋아요를 누른 상태에서 클랙 -> 좋아요 취소 -> delete
			result = noticeDao.deleteNoticeCommentLike(noticeCommentNo,memberNo);
		}
		if(result > 0) {
			//좋아요, 좋아요취소 로직을 수행하고 나면 현재 좋아요 갯수를 조회해서 리턴
			int likeCount = noticeDao.selectNoticeCommentLikeCount(noticeCommentNo);
			return likeCount;
		}else {
			return -1;			
		}
	}
}

