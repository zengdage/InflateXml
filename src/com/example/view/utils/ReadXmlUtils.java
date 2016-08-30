package com.example.view.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import com.example.view.engine.YDResource;

import android.content.Context;
import android.util.Xml;

public class ReadXmlUtils {
   
	/**
	 * 根据输入流获取到键值对
	 * @param is 获取到的输入流
	 * @param tag 标签
	 * @return 
	 */
   private static HashMap<String,String> readXml(InputStream is,String tag){
	   XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			HashMap<String,String> map=new HashMap<String, String>();
			for(int event=parser.getEventType();event!=XmlPullParser.END_DOCUMENT;event=parser.next()){
				if(event==XmlPullParser.START_TAG){
						if(tag.equals(parser.getName())){
							String name=parser.getAttributeValue(0);
							String value=parser.nextText();
							map.put(name, value);
						}
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
   }
   
	public HashMap<String,String> readStringsXml(String path){
		try {
			FileInputStream is=new FileInputStream(path);
			return readXml(is,"string");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 /**
		 * 获取统一字符串，放入键值对当中
		 * @return 所有的字符串名称
		 */
	public static HashMap<String,String> readStringsXml(Context context){
			InputStream is=null;
			try {
				 if(YDResource.assetsFlag){
				   is=context.getAssets().open("res/values/strings.xml");
				 }else{
				   is=new FileInputStream(YDResource.rootpath+"/values/strings.xml");
				 }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return readXml(is, "string");
	}
	   
	public static HashMap<String,String> readDimensXml(Context context){
			InputStream is=null;
			try {
				 if(YDResource.assetsFlag){
				   is= context.getAssets().open("res/values/dimens.xml");
				 }else{
				   is=new FileInputStream(YDResource.rootpath+"/values/dimens.xml");
				 }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return readXml(is,"dimen");
		}
		
	public static  HashMap<String,String> readColorsXml(Context context){
			InputStream is=null;
			try {
				 if(YDResource.assetsFlag){
				   is=context.getAssets().open("res/values/color.xml");
				 }else{
				   is=new FileInputStream(YDResource.rootpath+"/values/color.xml");
				 }
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			return readXml(is,"color");
		}
}
