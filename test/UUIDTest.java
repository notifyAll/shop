import com.qgyshop.util.UUIDUtils;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by vivid on 2017/3/25.
 * 随机码的测试
 */
public class UUIDTest {
    @Test
    public void test1(){
        for (int i = 0; i < 3; i++) {
            System.out.println(UUID.randomUUID().toString());
            //输出格式
//            4543b9ff-8a87-4d4e-b8d9-4ef0fea7b982
//            357a7c47-8ee1-478f-be85-df99d0c2bf4d
//            643b10a4-63eb-43a1-8dab-d8ca21e5261a
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(UUIDUtils.getUUID());
//            1316be7e720a4f7eb036a36f6bf95542
//            bbfb114afbe945da99630a7f99bb5738
//            27b41402dc0f425788319c94cf4dc4cd
        }
    }
}
