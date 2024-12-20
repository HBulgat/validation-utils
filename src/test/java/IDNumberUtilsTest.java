import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Bulgat
 * @createTime: 2024-12-20 16:09
 * @description:
 */
public class IDNumberUtilsTest {
    @Test
    public void testSdf(){
        String dateStr="2021-13-12";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
           throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year+","+month+","+day);
    }
}
