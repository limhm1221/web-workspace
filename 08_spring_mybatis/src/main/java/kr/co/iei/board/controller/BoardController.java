package kr.co.iei.board.controller;


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
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.board.model.dto.BoardDTO;
import kr.co.iei.board.model.dto.BoardFileDTO;
import kr.co.iei.board.model.dto.BoardPageData;
import kr.co.iei.board.model.service.BoardService;
import kr.co.iei.utill.FileUtils;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private FileUtils fileUtil;
	@Value("${file.root}")
	private String root;
	
	@GetMapping(value="/list")
	public String boardList(int reqPage,Model model) {
		BoardPageData bpd = boardService.selectBoardList(reqPage);
		model.addAttribute("list",bpd.getList());
		model.addAttribute("pageNavi",bpd.getPageNavi());
		return "board/list";
	}
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "board/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String write(BoardDTO board, MultipartFile[] upfile) {
		ArrayList<BoardFileDTO> fileList = new ArrayList<BoardFileDTO>();
		if(!upfile[0].isEmpty()) {
			String savepath =root+"/board";
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				BoardFileDTO boardFile = new BoardFileDTO();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				fileList.add(boardFile);
				
			}
		}
		int result = boardService.insertBoard(board,fileList);
		if(result == fileList.size()+1) {
			return "redirect:/board/list?reqPage=1";
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping(value="/view2")
	public String view(int boardNo, Model model) {
		
		BoardDTO board= boardService.selectOneBoard(boardNo);
		model.addAttribute("board",board);
		System.out.println(board);
		return "board/view";
	}
	@GetMapping(value="/updateFrm")
	public String updateFrm(int boardNo, Model model) {
	BoardDTO board = boardService.selectOneBoard(boardNo);
	model.addAttribute("board",board);
	return "board/updateFrm";
	}	
	
	@PostMapping(value="/update")
	public String update(BoardDTO board,MultipartFile[] upfile,int[] delFileNo) {
		List<BoardFileDTO> fileList= new ArrayList<BoardFileDTO>();
		String savepath = root+"/board/";
		
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				BoardFileDTO boardFile = new BoardFileDTO();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				boardFile.setBoardNo(board.getBoardNo());
				fileList.add(boardFile);
			}
		}
		List<BoardFileDTO> delFileList = boardService.updateBoard(board,fileList,delFileNo);
		if(delFileList != null) {
			for(BoardFileDTO boardFile : delFileList) {
				File delFile = new File(savepath+boardFile.getFilepath());
				delFile.delete();
			}
		}
		return "redirect:/board/view2?boardNo="+board.getBoardNo();
	}
}
