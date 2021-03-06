import com.gargoylesoftware.htmlunit.javascript.host.Touch;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/AppiumMobile/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can't find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can't find search input",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Can't find search field",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X-button",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X-button is visible",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find page title",
                15
        );
        String article_title = title_element.getAttribute("text");

        Assert.assertEquals("Unexpected title","Java (programming language)", article_title);
    }

    @Test
    public void testSwipeArticle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find page title",
                15
        );
    }

    @Test
    public void testSearchArticles()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Futurama",
                "Can't find search input",
                5
        );
        Assert.assertTrue("Found less than 2 articles", getAmountFoundArticles() >= 1);
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X-button",
                5
        );
        Assert.assertTrue("Search list is not clear", getAmountFoundArticles() == 0);
    }

    @Test
    public void testCheckWordMatchedInSearch()
    {
        String search_query = "Barcelona";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_query,
                "Can't find search input",
                5
        );
        List<String> articles = getArticles();
        for(int i=0; i<articles.size(); i++)
        {
            Assert.assertTrue(
                    "Search query " + search_query + " not found in article",
                    articles.get(i).toLowerCase().contains(search_query.toLowerCase()));
        }
    }

    @Test
    public void saveFirstArticleToMyList()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find page title",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find button to open article options",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can't find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can't find 'Got it' tip overlay",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can't find input to set name of article folder",
                5
        );

        String name_of_folder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Can't put text into article folder input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can't press OK button",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X link",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't find navigation button to My lists",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Can't find created folder",
                10
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't  delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        String search_line = "Linkin Park Diskography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Can't find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Can't find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0);

    }

    @Test
    public void testAmountOfEmptySearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        String search_line = "dszvrafassvy";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Can't find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";
        waitForElementPresent(
                By.xpath(empty_result_label),
                "Can't find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                        "We've found some results by request " + search_line
        );

    }

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Can't find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        String title_before_rotation = waitForElementAndGetAttibute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttibute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15
        );

        Assert.assertEquals("Article title has been changed after rotation",
                title_before_rotation, title_after_rotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttibute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15
        );

        Assert.assertEquals("Article title has been changed after rotation",
                title_before_rotation, title_after_second_rotation);

    }

    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Can't find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find article after returning from background",
                5
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_meesage, long timeOutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_meesage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private void verifyTextInWebElement(WebElement element, String text)
    {
        String actual_value = element.getAttribute("text");
        Assert.assertTrue("Web element " + element + " doesn't contain the text - " + text,
                actual_value == text);
    }

    private int getAmountFoundArticles()
    {
        List<WebElement> found_elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        return found_elements.size();
    }

    private List<String> getArticles()
    {
        List<String> articles = new ArrayList<String>();
        List<WebElement> article_elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for(int i=0; i<article_elements.size(); i++)
        {
            articles.add(i, article_elements.get(i).getAttribute("text"));
        }
        return articles;
    }

    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();

    }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(600)
                .moveTo(left_x, middle_y)
                .release()
                .perform();

    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if(amount_of_elements > 0)
        {
            String default_message = "An element '" +  by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttibute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
