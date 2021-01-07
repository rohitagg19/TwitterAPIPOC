package utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    String propertyFilePath;
    InputStream input;

    public PropertyUtil() {
        try{
            this.propertyFilePath = "src/test/resources/properties/Constant.properties";
            this.input = new FileInputStream(propertyFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String readPropertyData(String propertyName) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(this.propertyFilePath));
            return prop.getProperty(propertyName).trim();
        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        }
    }

    public String  getAPI_key(){
        return this.readPropertyData("API_key");
    }
    
    public String  getAPI_secret_key(){
        return this.readPropertyData("API_secret_key");
    }

    public String getAccessToken(){
        return this.readPropertyData("AccessToken");
    }

    public String getAccessTokenSecret(){
        return this.readPropertyData("AccessTokenSecret");
    }
    
}
