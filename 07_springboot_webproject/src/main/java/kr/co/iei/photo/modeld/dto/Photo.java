package kr.co.iei.photo.modeld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {
	private int photoNo;
	private String photoWriter;
	private String photoTitle;
	private String photoContent;
	private String photoImg;
	
}
