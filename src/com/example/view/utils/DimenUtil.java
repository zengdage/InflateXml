package com.example.view.utils;

import android.content.Context;
import android.util.Log;

public class DimenUtil {
	private static final String TAG="DimenUtil";
	/**
	 * 计算真实大小，单位为px
	 * @param s
	 * @return 计算真实大小，单位为px
	 */
	public  static int calculateRealSize(String s,Context mContext){
		int i=-2;
		try {
			i=Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("dp");
			int j=0;
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.dip2px(mContext,j);
			}
			tmp = s.indexOf("dip");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.dip2px(mContext,j);
			}
			tmp = s.indexOf("sp");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.sp2px(j, mContext);
			}
			tmp = s.indexOf("px");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
			}
			Log.i(TAG, "计算后的值为"+j);
			return j;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static int calculateTextSize(String s,Context mContext){
		int i=-2;
		try {
			i=Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("sp");
			int j=0;
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.sp2px(j, mContext);
			}
			Log.i(TAG, "计算后的值为"+j);
			return j;
		}
	}
}
