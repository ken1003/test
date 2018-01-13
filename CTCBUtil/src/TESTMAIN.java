import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TESTMAIN {

	public static void main(String[] args) {
		String d = "1511474379000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.parseLong(d));
			
			System.out.println(sdf.format(cal.getTime()));

	}

}
