package com.example.view.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IDUtils {

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	/**
	 * 生成控件ID
	 * @return
	 */
	public static int generateViewId() {
	        for (;;) {
	            final int result = sNextGeneratedId.get();
	            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	            int newValue = result + 1;
	            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	            if (sNextGeneratedId.compareAndSet(result, newValue)) {
	                return result;
	            }
	        }
	 }
	    /**
		 * 获取在xml文件中定义的id字符串
		 * @param s android:id="xxxxxxxx"里面的xxxxxxxxx，如@+id/button1
		 * @return  在xml文件中定义的id字符串
		 */
		public static String getID(String s){
			if(s.startsWith("@+id/")){
				s=s.substring(5);
				return s;
			}else if(s.startsWith("@id/")){
				s=s.substring(4);
				return s;
			}
			return null;
		}
		
		public int getStringHashCode(String s){
			return s.hashCode();
		}
		
}
