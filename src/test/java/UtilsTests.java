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

}
