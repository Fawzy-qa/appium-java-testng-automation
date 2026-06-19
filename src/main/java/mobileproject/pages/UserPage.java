package mobileproject.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileproject.base.BasePage;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UserPage  extends BasePage {

    public UserPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);    }

}
