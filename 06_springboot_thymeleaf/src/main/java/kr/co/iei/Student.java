package kr.co.iei;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
	private String name;
	private int age;
	private String addr;
	//private String testData;
	
	
	public String getTestData() {
		return "이건 테스트용 데이터";
	}
}
