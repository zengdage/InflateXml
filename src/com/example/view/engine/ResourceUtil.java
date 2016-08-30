package com.example.view.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.view.params.AbsoluteLayoutParams;
import com.example.view.params.FrameLayoutParams;
import com.example.view.params.LinearLayoutParams;
import com.example.view.params.Params;
import com.example.view.params.RelativeLayoutParams;
import com.example.view.params.TableLayoutParams;
import com.example.view.utils.DensityUtil;
import com.example.view.utils.DrawableUtils;

public class ResourceUtil {	
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

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
	     * 创建配置信息
	     * @param context
	     * @param name
	     * @return
	     */
	    public static Params createParams(Context context,String name){
	    	Params params=null;
	    	if(name.equals("com.example.view.YDLinearLayout")){
	    		params=new LinearLayoutParams(context);
	    	}
	    	else if(name.equals("com.example.view.YDRelativeLayout")){
	    		params=new RelativeLayoutParams(context);
	    	}
	    	else if(name.equals("com.example.view.YDFrameLayout")){
	    		params=new FrameLayoutParams(context);
	    	}
	       else if(name.equals("com.example.view.YDTableLayout")){
	    	   params=new TableLayoutParams(context);
	    	}
	       else if(name.equals("com.example.view.YDAbsoluteLayout")){
	    	   params=new AbsoluteLayoutParams(context);
	    	}
	    	return params;
	    }

}
