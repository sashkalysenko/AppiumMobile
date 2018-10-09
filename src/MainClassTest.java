import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass MainClass = new MainClass();

    @Test
    public void testGetLocalNumber(){
        Assert.assertEquals("MainClass.getLocalNumber() response != 14",
                14, MainClass.getLocalNumber());
    }

    @Test
    public void testGetClassNumber(){
        Assert.assertTrue("MainClass.getClassNumber() has returned response which <= 45",
                MainClass.getClassNumber() > 45 );

    }

    @Test
    public void testGetClassString(){
        if(MainClass.getClassString().contains("hello") || MainClass.getClassString().contains("Hello")){
        }
        else{
            Assert.fail("MainClass.class_string doesn't contain any of the following substrings: 'hello', 'Hello'");
        }

    }

}
