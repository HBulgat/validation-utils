import com.bulgat.validation.test.DateValidatorUsingDateFormat;
import org.junit.Test;

/**
 * @author: Bulgat
 * @createTime: 2024-12-20 16:19
 * @description:
 */
public class DateValidatorUsingDateFormatTest {


    @Test
    public void isValid() {
        final DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");

        System.out.println(validator.isValid("2021-02-28"));
        System.out.println(validator.isValid("2021-02-30"));
        System.out.println(validator.isValid("2021-13-30"));
    }
}
