package com.example.view.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
/**
 * @author Codefarmer@sina.com
 */
public class DensityUtil {
	  
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = getDisplayMetrics(context).density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale =  getDisplayMetrics(context).density; 
        return (int) (pxValue / scale + 0.5f);  
    } 
    
    public static  DisplayMetrics getDisplayMetrics(Context context){
    	WindowManager manager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	Display display=manager.getDefaultDisplay();
    	
    	DisplayMetrics metrics=new DisplayMetrics();
    	display.getMetrics(metrics);
    	return metrics;
    }
    
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, Context context) {
    	final float fontScale=getDisplayMetrics(context).scaledDensity;
     return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, Context context) {
    	final float fontScale=getDisplayMetrics(context).scaledDensity;
     return (int) (spValue * fontScale + 0.5f);
    }
    
    /**
	 * 获取屏幕密度
	 * @param context
	 * @return
	 */
	public static int getDensity(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}
   
}
