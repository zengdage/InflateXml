package com.example.view.utils;
/**
 * @author Codefarmer@sina.com
 */
public class Logger {
	public static boolean debug = true;
	
    public static void e(String tag, String msg){
   	 if (debug) {
			android.util.Log.e(tag, msg);
		}
    }
    
    public static void e(String tag, String msg, Throwable tr){
   	 if (debug) {
			android.util.Log.e(tag, msg, tr);
		}
    }
    
    public static void d(String tag, String msg){
   	 if (debug) {
			android.util.Log.d(tag, msg);
		}
    }
    
    public static void d(String tag, String msg, Throwable tr){
   	 if (debug) {
			android.util.Log.d(tag, msg, tr);
		}
    }
    
    public static void v(String tag, String msg){
   	 if (debug) {
			android.util.Log.v(tag, msg);
		}
    }
    
    public static void v(String tag, String msg, Throwable tr){
   	 if (debug) {
			android.util.Log.v(tag, msg, tr);
		}
    }
    
    public static void w(String tag, String msg){
   	 if (debug) {
			android.util.Log.w(tag, msg);
		}
    }
    
    public static void w(String tag, String msg, Throwable tr){
   	 if (debug) {
			android.util.Log.w(tag, msg, tr);
		}
    }
    
    public static void i(String msg){
   	 if (debug) {
   		SecurityManager manager=new SecurityManager();
   		
   		Throwable t=new Throwable();
   		StackTraceElement[]ste=t.getStackTrace();
   		String tag1=ste[1].getFileName();
		android.util.Log.e(tag1, msg);
		}
    }
    
    public static void i(String tag, String msg, Throwable tr){
   	 if (debug) {
			android.util.Log.i(tag, msg, tr);
		}
    }
}
