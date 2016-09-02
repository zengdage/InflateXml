package com.example.view.utils;

import java.lang.reflect.Field;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView.ScaleType;

public class AttrsUtil {
	/**
	 * 设置重心
	 * @param gravity
	 * @return
	 */
	public  static int getGravity(String gravity){
		Log.i("YDResource gravity", gravity);
		String [] s=gravity.toUpperCase().split("\\|");
		int sum=Gravity.TOP;
		try {
			Class clazz = Class.forName("android.view.Gravity");
			for (int i = 0; i < s.length; i++) {
				Field f=clazz.getField(s[i]);
				sum|=f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	/**
	 * 设置重心
	 * @param gravity
	 * @return
	 */
	public  static int getScaleGravity(String gravity){
		String [] s=gravity.toUpperCase().split("\\|");
		int sum=Gravity.TOP;
		try {
			Class clazz = Class.forName("android.view.Gravity");
			for (int i = 0; i < s.length; i++) {
				Field f=clazz.getField(s[i]);
				sum|=f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	/**
	 * 获取到资源ID
	 * @param tid
	 * @return
	 */
	public  static int getIdentifier(String tid){
		String packagename="com.example.testviewtree";
		StringBuilder sb=new StringBuilder();
		sb.append(packagename);
		sb.append(".R$");
		int rid=0;
		String[] classes=tid.split("\\.");
		System.out.println(classes.toString());
		sb.append(classes[1]);
		try {
			Class<?> innerClass=Class.forName(sb.toString());
			Object obj = innerClass.newInstance();
	        Field field = innerClass.getDeclaredField(classes[2]);
	        field.setAccessible(true);
	        rid=(Integer) field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rid;
	}
	
	public static ScaleType getScaleType(String scaleType){
		if(scaleType.equals("center")){
			return ScaleType.CENTER;
		}else if(scaleType.equals("centerCrop")){
			return ScaleType.CENTER_CROP;
		}else if(scaleType.equals("centerInside")){
			return ScaleType.CENTER_INSIDE;
		}else if(scaleType.equals("fitCenter")){
			return ScaleType.FIT_CENTER;
		}else if(scaleType.equals("firEnd")){
			return ScaleType.FIT_END;
		}else if(scaleType.equals("firStart")){
			return ScaleType.FIT_START;
		}else if(scaleType.equals("firXY")){
			return ScaleType.FIT_XY;
		}else if(scaleType.equals("matrix")){
			return ScaleType.MATRIX;
		}
		return null;
	}
	
	/**
	 * 设置输入类型
	 * @param 输入类型
	 * @return
	 */
	public  static int getInputType(String inputType){
		Log.i("YDResource InputType", inputType);
		String [] s=inputType.toUpperCase().split("\\|");
		int sum=InputType.TYPE_CLASS_TEXT;
		try {
			Class clazz = Class.forName("android.text.InputType");
			for (int i = 0; i < s.length; i++) {
				Field f=clazz.getField(s[i]);
				sum|=f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
}
