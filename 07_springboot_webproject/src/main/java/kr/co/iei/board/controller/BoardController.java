package kr.co.iei.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardFile;
import kr.co.iei.board.model.service.BoardService;
import kr.co.iei.notice.model.dto.Notice;
import kr.co.iei.notice.model.dto.NoticeFile;
import kr.co.iei.notice.model.dto.NoticeListData;
import kr.co.iei.util.FileUtils;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/list")
	public String list(Model model, int reqPage) {
		List list = boardService.selectBoardeList(reqPage);
		model.addAttribute("list",list);
		
		return "/board/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		
		return "board/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String write(Board b, MultipartFile[] upfile,Model model) {
		System.out.println(b);
		System.out.println("업로드 된 파일의 수 : "+upfile.length);
		
		List<BoardFile> fileList= new ArrayList<BoardFile>();
		
		if(!upfile[0].isEmpty()) {
			//C:/Temp/upload/notice/
			String savepath = root+"/board/";
			for(MultipartFile file : upfile) {
				//사용자가 업로드한 파일 이름 출력 
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				BoardFile boardFile = new BoardFile();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				fileList.add(boardFile);
			}
		}

		int result = boardService.insertboard(b,fileList);
		if(result > 0) {
			model.addAttribute("title", "작성성공!");
			model.addAttribute("msg", "공지사항 작성에 성공했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/notice/list?reqPage=1");
			return "common/msg";
			
		}
		return "redirect:/board/writeFrm";
	}

	}

