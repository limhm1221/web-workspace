package kr.co.iei.utill;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest {
	
	//스케쥴에의해서 자동으로 실행되는 메소드는 매개변수를 받을 수 없음
	//@Scheduled(fixedDelay = 10000)
	public void test1() {
		System.out.println("이건 자동으로 실행되는 메소드");	
	}
	@Scheduled(cron ="10 * * * * *")
	public void test2() {
		System.out.println("cron으로 실행되는 메소드");
	}
	@Scheduled(cron =  "0 0 0 * * *")
	public void test3() {
		
	}
}
