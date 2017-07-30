import org.junit.Test;

import java.util.Random;

/**
 * Created by vivid on 2017/3/25.
 * 随机数的测试
 */
public class Randowtest {
    @Test
    public void test1(){
        Random random=new Random();

        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt()+"  ");
//            170414122  1574091774  1115160022  1118486465  1587233033  -1046379640  -915985209  1163367301  1837872325  952272699
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt(5)+"  ");
//            0  1  3  3  3  1  2  4  4  2
        }
        System.out.println();

    }

}
