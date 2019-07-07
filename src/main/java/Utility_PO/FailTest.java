package Utility_PO;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import java.util.TreeMap;

import static org.testng.Assert.fail;

public class FailTest {
    String snapShotPath = "";
    WebDriver driver;
    TreeMap<String, String> dataStream;
    TreeMap<String, String> msgStream;
    ITestContext ctx;
    Logger testData;
    Logger alertLogger;
    Logger imgPath;

    public FailTest(){
    }

    public FailTest(TreeMap<String, String> dataStream){
        this.dataStream = dataStream;
    }

    public FailTest(WebDriver adriver, ITestContext aCtx) {
        driver = adriver;
        ctx = aCtx;
        alertLogger = new Logger("alertsPtr", ctx);
        testData = new Logger("pointer", ctx);
        imgPath = new Logger("imgPath", ctx);
        dataStream = testData.getLogMap("testData");
//        msgStream = testData.getLogMap("message");
    }

    public boolean FailTest(WebDriver driver, String testName) {
        ScreenShot s = new ScreenShot();
        snapShotPath = s.captureScreen(driver, testName);
        System.out.println(snapShotPath);
        return false;
    }

    public boolean FailTest(WebDriver driver, String testName, Exception e) {
        ScreenShot s = new ScreenShot();
        snapShotPath = s.captureScreen(driver, testName);
        System.out.println(snapShotPath + " " + e);
        return false;
    }

    public void failTest(TreeMap<String, String> dataStream, String message) {
        try {
            ScreenShot s = new ScreenShot();
            if (message.equals(null)) message = "";
            dataStream.put("message", message);
//            image = s.captureScreen(driver, message);
            String image = s.captureScreen(driver, message);
            dataStream.put("image", image);
            System.out.println(image);
        } catch (Exception e) {
            System.out.println(message);
        } finally {
            dataStream.put("result", "FAIL");
            fail();
        }
    }

    public void failTest(String message) {
        try {
            ScreenShot s = new ScreenShot();
            if (message.equals(null)) message = "";
            alertLogger.Log("alerts", message);
            String image = s.captureScreen(driver, message);
            if (dataStream != null){
                dataStream.put("image", image);
            }else{
                imgPath.Log("imgPath", image);
            }
            System.out.println(image);
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(message);
        } finally {
            if (dataStream != null){
                dataStream.put("result", "FAIL");
            }
            fail();
        }
    }

    public void failWebServiceTest(String message){
        if (message.equals(null)){
            message = "";
        }
        System.out.println(message);
        dataStream.put("result", "FAIL");
        dataStream.put("notes", dataStream.get("notes") + " <span style=\"color : red;\">" + message + "</span>;");
        fail();
    }

    public void warnTest(String message) {
        try {
            ScreenShot s = new ScreenShot();
            if (message.equals(null)) message = "";
            alertLogger.Log("alerts", message);
            String image = s.captureScreen(driver, message);
            if (dataStream != null){
                dataStream.put("image", image);
            }else{
                imgPath.Log("imgPath", image);
            }
            System.out.println(image);
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(message);
        } finally {
            if (dataStream != null){
                dataStream.put("result", "WARN");
            }
        }
    }

    public String getSnapShotPath(){
        return snapShotPath;
    }

}
