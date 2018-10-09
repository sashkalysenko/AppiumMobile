import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass MainClass = new MainClass();

    @Test
    public void testGetLocalNumber(){
        Assert.assertEquals("MainClass.getLocalNumber() response != 14",
                14, MainClass.getLocalNumber());
    }

}
