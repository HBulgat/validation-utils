import com.bulgat.validation.phone.PhoneUtils;
import org.junit.Test;


/**
 * @author: Bulgat
 * @createTime: 2024-12-20 14:32
 * @description: 电话号码校验工具测试类
 */
public class PhoneUtilsTest {
    @Test
    public void testChineseMobilePhone(){
        String[] phones=new String[]{
                "15929985622","13763991981","18798760984","+8618798760984"
        };
        for (String phone : phones) {
            System.out.println("phone="+phone+",validation="+PhoneUtils.validateChineseMobilePhone(phone));
        }
    }

    @Test
    public void testChineseFixedPhone(){
        String[] phones=new String[]{
                "010-12345678",
                "0731-87654321",
                "0351-7654321"
        };
        for (String phone : phones) {
            System.out.println("phone="+phone+",validation="+PhoneUtils.validateChineseFixedPhone(phone));
        }
    }
}
