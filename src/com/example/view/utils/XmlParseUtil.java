package com.example.view.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.example.view.engine.YDResource;

public class XmlParseUtil {
	/**
     * 获取xml文件转换器
     * @param resource xml文件路径
     * @return
     * @throws Exception 
     */
    public static XmlPullParser getXmlPullParser(String resource,Context mContext){
    	XmlPullParser parser=Xml.newPullParser();
    	InputStream is=null;
    	try {
    		if(YDResource.assetsFlag){
  		        is=mContext.getAssets().open(resource);
    		}else{
    			is=new FileInputStream(resource);
    		}
    		parser.setInput(is, "utf-8");
		} catch (Exception e) {
			e.printStackTrace(); 
		}
    	return parser;
    }
    
    
}
