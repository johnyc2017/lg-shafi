package Utility_PO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenShot {
    public String captureScreen(WebDriver driver, String testName) {

        String filePath = "C:\\temp\\screenshots\\";
        String fileType = ".png";
        try {
            Date now = new Date();
            String date = new SimpleDateFormat("MMM_d_yyyy").format(now);
            String time = new SimpleDateFormat("HH_mm_ss_SSS").format(now);
            TakesScreenshot snapper;
            if  (((RemoteWebDriver) driver).getCapabilities().getCapability("webdriver.remote.sessionid") == null) snapper = (TakesScreenshot)driver;
            else  {
                Augmenter augmenter = new Augmenter();
                snapper = (TakesScreenshot) augmenter.augment(driver);
            }
            File tempScreenshot = snapper.getScreenshotAs(OutputType.FILE);
            File myScreenshotDirectory = new File(filePath + date + "\\");
            if (!myScreenshotDirectory.exists()){
                myScreenshotDirectory.mkdirs();
            }
            File myScreenshot = new File(myScreenshotDirectory, time + " " + testName + fileType);
            FileUtils.moveFile(tempScreenshot, myScreenshot);
            return myScreenshot.getAbsolutePath().toString();

        }
        catch(IOException e) {
            return e.getMessage();
        }
    }
}
