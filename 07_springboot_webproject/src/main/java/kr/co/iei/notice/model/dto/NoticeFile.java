package kr.co.iei.notice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeFile {
	private int fileNo;
	private int noticeNo;
	private String filename;
	private String filepath;
	

}
