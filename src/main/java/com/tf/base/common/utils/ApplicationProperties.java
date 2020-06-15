package com.tf.base.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {
	
	static Properties prop =  new  Properties();    

	static  {    
        InputStream in = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties"); 
         try  {    
            prop.load(in);    
        }  catch  (IOException e) {    
            e.printStackTrace();    
        }    
    }    
	
	public String getValueByKey(String key){
		return prop.getProperty(key);
	}
	
  
}
