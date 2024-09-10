package kr.co.iei.notice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.notice.model.dto.Notice;
import kr.co.iei.notice.model.dto.NoticeComment;
import kr.co.iei.notice.model.dto.NoticeFile;
import kr.co.iei.notice.model.dto.NoticeListData;
import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.util.FileUtils;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService; 
	
	@Value("${file.root}")
	private String root;//application.properties에 설정되어있는 file.root값을 가지고와서 문자열로 저장
	
	
	@Autowired
	private FileUtils fileUtils;//파일업로드를 처리해줄 객체
	
	
	@GetMapping(value="/list")
	public String list(Model model, int reqPage) {
		NoticeListData nld  = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list",nld.getList());
		model.addAttribute("pageNavi", nld.getPageNavi());
		return "notice/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		
		return "notice/writeFrm";
	}
	@GetMapping(value="/editorFrm")
	public String editorFrm(@SessionAttribute(required = false) Member member){		
		
		return "notice/editorFrm";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces ="plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/notice/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/notice/editor/"+filepath;
	}
	
	@PostMapping(value="/write")
	public String write(Notice n, MultipartFile[] upfile,Model model) {
		//첫번째 매개변수 Notice에는 제목,작성자,내용이 저장
		//첨부파일은 multipartFile ,타입으로 컨트롤러로 요청
		//화면에서 multiple옵션으로 첨푸파일 여러개를 첨푸하게 했으므로 배열로 처리
		//input type=text에 아무것도 입력하지 않고 submit을 하면 null이 아니라 빈문자열(")
		//input type=file에 아무것도 첨부하지 않고 submit을 하면 배열의 길이가 0이아니라, 1이고, 첫번째 파일이 비어있음
		System.out.println(n);
		System.out.println("업로드 된 파일의 수 : "+upfile.length);
		
		//첨부파일은 서버에 업로드를 수행하고, 업로드된 결과를 (업로드한 파일이름)를 db에 저장
		//notice_file에 insert하기위한 데이터를 생성 
		List<NoticeFile> fileList= new ArrayList<NoticeFile>();
		//첨부파일이 없으면 upfile의 첫번째 객체가 비어있음 -> 첫번째 파일이 비어있는지로 첨부파일 여부 확인
		if(!upfile[0].isEmpty()) {
			//C:/Temp/upload/notice/
			String savepath = root+"/notice/";
			for(MultipartFile file : upfile) {
				//사용자가 업로드한 파일 이름 출력 
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				NoticeFile noticeFile = new NoticeFile();
				noticeFile.setFilename(filename);
				noticeFile.setFilepath(filepath);
				fileList.add(noticeFile);
			}
		}
		// n : noticeTitle, noticeWriter, noticeContent
		//fileList : (NoticeFile) x 첨푸파일갯수
		
		int result = noticeService.insertNotice(n,fileList);
		if(result > 0) {
			model.addAttribute("title", "작성성공!");
			model.addAttribute("msg", "공지사항 작성에 성공했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/notice/list?reqPage=1");
			return "common/msg";
			
		}
		return "redirect:/notice/writeFrm";
	}
	@GetMapping(value="/view")
		public String view(int noticeNo, String check, Model model, @SessionAttribute(required = false) Member member) {
			int memberNo = 0;
		if(member != null) {
				memberNo = member.getMemberNo();
			}
		//로그인이 되어있지않으면 memberNo= 0 / 로그인이 되어있으면 memberNO = 로그인한 회원번호
		
		System.out.println("check : " +check);
		Notice  n = noticeService.selectOneNotice(noticeNo,check,memberNo);
		if(n == null) {
			model.addAttribute("title","조회실패");
			model.addAttribute("msg","해당 게시글이 존재하지 않습니다.");
			model.addAttribute("icon","info");
			model.addAttribute("loc","/notice/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("n",n);			
			return "notice/view";
		}
	}
	@GetMapping(value="/filedown")
	public void filedown(NoticeFile noticeFile, HttpServletResponse response) {
		String savepath = root +"/notice/";
		fileUtils.downloadFile(savepath, noticeFile.getFilename(), noticeFile.getFilepath(),response);
	}
	
	@GetMapping(value="/delete")
	public String delete(int noticeNo, Model model) {
		//삭제를 하게되면 해다 게시글의 첨부파일도 삭제 - > 삭제결과로 파일 목록을 가져옴
		List<NoticeFile> list = noticeService.deleteNotice(noticeNo);
		if(list==null) {
			model.addAttribute("title","삭제실패 ");
			model.addAttribute("msg","존재하지 않는 게시물입니다");
			model.addAttribute("icon", "error");
			model.addAttribute("loc","/notice/list?reqPage=1");
		}else {
			String savepath = root+"/notice/";
			for(NoticeFile file : list) {
				File delFile = new File(savepath+file.getFilepath());
				delFile.delete();
			}
			
			model.addAttribute("title","삭제성공 ");
			model.addAttribute("msg","게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc","/notice/list?reqPage=1");
		}
		return "common/msg";
	}
	@GetMapping(value="/updateFrm")
	public String updateFrm(int noticeNo, Model model) {
		Notice n = noticeService.getOneNotice(noticeNo);
		model.addAttribute("n",n);
		
		return "notice/updateFrm";
	}
	
	@PostMapping(value="/update")
	public String update(Notice n, MultipartFile[] upfile, int[] delFileNo, Model model) {
		//새로 추가된 파일을 업로드 작업
		List<NoticeFile> fileList= new ArrayList<NoticeFile>();
		String savepath = root+"/notice/";
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				NoticeFile noticeFile =new NoticeFile();
				noticeFile.setFilename(filename);
				noticeFile.setFilepath(filepath);
				noticeFile.setNoticeNo(n.getNoticeNo());
				fileList.add(noticeFile);
			}
		}
		//수정요청하면서 데이터 3개 전달 (n: notice테이블 수정, fileList : notice_file 추가, delFileNo : notice_file 삭제용)
		List<NoticeFile> delFileList = noticeService.updateNotice(n,fileList,delFileNo);
		if(delFileList == null) {
			model.addAttribute("title","수정실패 ");
			model.addAttribute("msg","처리 중 문제가 발생했습니다 .잠시 후 다시해라");
			model.addAttribute("icon", "error");
			model.addAttribute("loc","/notice/list?reqPage=1");
			return "/common/msg";
		}else {
			for(NoticeFile noticeFile : delFileList) {
				File delFile = new File(savepath+noticeFile.getFilepath());
				delFile.delete();
			}
			return "redirect:/notice/view?noticeNo="+n.getNoticeNo();
		}
	}
	@PostMapping(value="/insertComment")
	public String insertComment(NoticeComment nc, Model model) {
		int result = noticeService.insertComment(nc);
		
		if(result > 0) {
			model.addAttribute("title", "댓글 작성 성공");
			model.addAttribute("msg", "댓글이 작성이 되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "댓글 작성 실패");
			model.addAttribute("msg", "댓글이 작성 중 문제가 발생했습니다.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc","/notice/view?check=1&noticeNo=" +nc.getNoticeRef());
		return "common/msg";
	}

	@PostMapping(value="/updateComment")
	public String updateComment(NoticeComment nc, Model model) {
		int result = noticeService.updateComment(nc);
		if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "댓글이 수정되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", " 실패");
			model.addAttribute("msg", "잠시 후 다시 시도해주세요");
			model.addAttribute("icon", "warning");
		}
		 model.addAttribute("loc","/notice/view?check=1&noticeNo="+nc.getNoticeRef());
		return "common/msg";
	}
	@GetMapping(value="/deleteComment")
	public String deleteComment(NoticeComment nc, Model model) {
		int result = noticeService.deleteComment(nc);
		
		if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "댓글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "삭제 안해줄껀데 ㅋ");
			model.addAttribute("icon", "warning");
		}
		 model.addAttribute("loc","/notice/view?check=1&noticeNo="+nc.getNoticeRef());
			return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="/likePush")
	public int likePush(int noticeCommentNo, int isLike, @SessionAttribute(required = false) Member member) {
		//@SessionAttribute에서 로그인정보를 가지고올 때 required 옵션을 명시하지 않으면 기본적으로 true
		// ->로그인이 되어있지않으면 에러가 발생
		// ->로그인이 되어있지 않은 상태에서 에러를 발생시키지 않으려면 (required = false)옵션을 추가
		// 		-> 로그인이 되어있으면 로그인 한 회원정보/로그인이 되어있지 않으면  null
		if(member == null) {
			return -10;
		}else {			
			int memberNo = member.getMemberNo();
			int result = noticeService.likePush(noticeCommentNo, isLike,memberNo);
			return result;
		}
		
	}
}

