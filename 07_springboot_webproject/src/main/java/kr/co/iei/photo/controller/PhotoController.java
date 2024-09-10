package kr.co.iei.photo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.photo.model.service.PhotoService;
import kr.co.iei.photo.modeld.dto.Photo;
import kr.co.iei.util.FileUtils;
@Controller
@RequestMapping(value="/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    
    
    @Value("${file.root}")
    private String root;//application.properties에 설정되어있는 file.root값을 가지고 와서 문자열로 저장
    
    @Autowired
    private FileUtils fileUtils;//파일 업로드를 처리해줄 객체
    
    
    @GetMapping(value="/list")
    public String list(Model model) {
        //전체 게시물 수 조회
        int totalCount = photoService.selectTotalCount();
        model.addAttribute("totalCount",totalCount);
        return "photo/list";
    }
    
    @GetMapping(value="/writeFrm")
    public String writeFrm() {
    	return "photo/writeFrm";
    }
    
    @PostMapping(value="/write")
    public String write(Photo p, MultipartFile imageFile, Model model) {
    	System.out.println(imageFile);
    	String savepath = root+"/photo/";
    	String filepath = fileUtils.upload(savepath, imageFile);
    	p.setPhotoImg(filepath);
    	int result = photoService.insertPhoto(p);
    	if(result > 0) {
    		model.addAttribute("title","작성완료");
    		model.addAttribute("msg","게시글이 작성되었습니다.");
    		model.addAttribute("icon","success");
    	}else {
    		model.addAttribute("title","작성 실패");
    		model.addAttribute("msg"," 문제가 발생했습니다");
    		model.addAttribute("icon","erreor");	
    	}
    	model.addAttribute("loc","/photo/list");
    	return "common/msg";
    }
    
    @ResponseBody
    @GetMapping(value="more")
    public List photoMore(int start, int amount) {
    	List photoList = photoService.selectPhotoList(start, amount);
    	
    	return photoList;
    }
}
