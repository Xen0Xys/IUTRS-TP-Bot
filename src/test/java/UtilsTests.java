import fr.xen0xys.edtbot.models.Utils;
import org.junit.jupiter.api.Test;

public class UtilsTests {

    @Test
    void uidTest(){
        System.out.println("------------------------");
        System.out.println("UID TEST:");
        for (int i = 0; i < 10; i++) {
            System.out.println(Utils.generateId());
        }
        System.out.println("------------------------");
    }

    @Test
    void DLStatusTest(){
        System.out.println("------------------------");
        System.out.println("Deadline TEST:");
        System.out.println("Target:              1662898672");
        System.out.println("Current time:        " + System.currentTimeMillis());
        System.out.println("Current time / 1000: " + System.currentTimeMillis() / 1000);
        System.out.println(1662898672 - System.currentTimeMillis() / 1000);
        System.out.println(Utils.getDeadlineStatusFromTimestamp(1662898672));
        System.out.println("------------------------");
    }

}
