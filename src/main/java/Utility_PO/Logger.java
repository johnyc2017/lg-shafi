package Utility_PO;


import org.testng.ITestContext;
import java.util.HashMap;
import java.util.TreeMap;

//************** usage 1*****************
//Logger log = new Logger(ctx);    <<<<< create log w/ preset name.
//log.setDelimiter("****br****");
//log.Log("this");
//System.out.println(log.getLog());
//
//**************************************

//************** usage 2*****************
//Logger log2 = new Logger("mylog",ctx);   <<<<< create log w/ custom name.
//log2.Log("mylog","this2");
//System.out.println(log2.getLog("mylog"));
//
//**************************************

//************** usage 3*****************
//dataStream.put("Key", "Value");
//Logger logDD = new Logger("matt",ctx,dataStream);
//TreeMap<String, String> mydd = new TreeMap<String, String>();
//mydd = logDD.getLogMap("matt"); <<<<<<get data into treemap.  This creates the mydd as a reference pointer into the CTX object that holds the datastream.
//mydd.put("matt", "this is my message");
//System.out.println(mydd.get("Key")); <<<<<<get data from the treemap
//System.out.println(logDD.getLogMap("matt").get("Key"));   <<<<<<direct access to data logger.
//see below for more notes.
//**************************************

public class Logger {
    ITestContext ctx;
    HashMap<String, String> dataStream = new HashMap<String, String>();
    String delimiter = "<br>";
    public Logger(ITestContext actx){
        ctx = actx;
        ctx.setAttribute("message", dataStream);
    }

    public Logger(String logName, ITestContext actx){
        ctx = actx;
        ctx.setAttribute(logName, dataStream);
    }

    public Logger(String logName, ITestContext actx, TreeMap<String, String> cDataStream){
        ctx = actx;
        ctx.setAttribute(logName,cDataStream);
    }

    public void Log(String message){
        ((HashMap<String, String>) ctx.getAttribute("message")).put(Integer.toString(((HashMap<String, String>) ctx.getAttribute("message")).size() + 1),message);
    }

    public void Log(String YourKey, String message){
        ((HashMap<String, String>) ctx.getAttribute(YourKey)).put(Integer.toString(((HashMap<String, String>) ctx.getAttribute(YourKey)).size() + 1),message);
    }

    //Deprecated.  Don't need this method.  Look at usage 3, line26 and 27 to instantiate a reference pointer into the CTX of the collection/datastream you specified to log your data.
//    public void set(String collection, String key, String message){
//        ((TreeMap<String, String>) ctx.getAttribute(key)).put(Integer.toString(((TreeMap<String, String>) ctx.getAttribute(key)).size() + 1),message);
//    }

    public String getLog(){
        String logger = "";
        int count = 0;
        if (ctx.getAttribute("message") == null){
            System.out.println("null logger");
        }
        else{
            while (count <= ((HashMap<String, String>) ctx.getAttribute("message")).size()){
                if (((HashMap<String, String>) ctx.getAttribute("message")).get(Integer.toString(count))!= null){
                    logger = logger + ((HashMap<String, String>) ctx.getAttribute("message")).get(Integer.toString(count)) + delimiter;
                }
                count = count+1;
            }
        }
        return logger;
    }

    public String getLog(String YourKey){
        String logger = "";
        int count = 0;
        if (ctx.getAttribute(YourKey) == null){
            System.out.println("null logger");
        }else{
            while (count <= ((HashMap<String, String>) ctx.getAttribute(YourKey)).size()){
                if (((HashMap<String, String>) ctx.getAttribute(YourKey)).get(Integer.toString(count))!= null){
                    logger = logger + ((HashMap<String, String>) ctx.getAttribute(YourKey)).get(Integer.toString(count)) + delimiter;
                }
                count = count+1;
            }
        }
        return logger;
    }

    public TreeMap<String, String> getLogMap (String LogName){
        return ((TreeMap<String, String>) ctx.getAttribute(LogName));
    }

    public void setDelimiter(String customDelimiter){
        delimiter = customDelimiter;
    }
}
