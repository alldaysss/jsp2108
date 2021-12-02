package conn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 오늘(현재)날짜 DB의 날짜와의 시간차이를 계산하여 돌려주는 클래스
public class TimeDiff {
	// 1. '오늘날짜'와 'DB의 날짜'를 '문자'로 변경 : 날짜형식기호에 맞도록 설계
	// 2. 날짜 연산을 위해 날짜형식으로 변경
	// 3. 날짜차이를 시간으로 계산하기 위해 '숫자'형식으로 변환
	
	// 외부로 부터 날짜를 입력받아 연산처리 후 정수값을 돌려준다.
	public int timeDiff(String wCdate) {
		// 1-1. 날짜형식을 문자(2021111231253)형식으로 맞추기 위한 준비 작업
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm"); // HH:24시간제 , hh:1~12(AM/PM)
		String strToday = sdf.format(cal.getTime());
		
		// 2-1. 날짜 연산을 위한 문자형식의 날짜를 날짜형식으로 변경한다.
		Date today = null;
		try {
			today = sdf.parse(strToday);					
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//					  0         1
		//					  0123456789012345678901
		// 1-2. DB에 저장된 날짜(2021-11-23 13:04:25.5)를 앞과 같은 방법으로 처리한다.
		String strWdate = wCdate.substring(0,4)+wCdate.substring(5,7)+wCdate.substring(8,10)+wCdate.substring(11,13)+wCdate.substring(14,16);
		
		// 2-2. 날짜 연산을 위해 DB에서 가져온 문자변환형식자료를 날짜형식으로 변환한다.
		Date dateWdate = null;
		try {
			dateWdate = sdf.parse(strWdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 3. 날짜형식으로 변경된 두 자료(today/dateWdate)를 숫자형식(getTime)으로 변경처리한다.(시간으로 연산처리하기 위함) - 결과값은 ms형식으로 반환
		long diffTime = (today.getTime() - dateWdate.getTime()) / (60 * 60* 1000) + 1;
		
		return (int) diffTime; // 연산된 결과를 시간(1~24)으로 돌려준다.
	}
}
