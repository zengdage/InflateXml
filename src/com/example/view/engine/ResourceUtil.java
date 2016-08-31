package com.example.view.engine;

import android.content.Context;

import com.example.view.params.AbsoluteLayoutParams;
import com.example.view.params.FrameLayoutParams;
import com.example.view.params.LinearLayoutParams;
import com.example.view.params.Params;
import com.example.view.params.RelativeLayoutParams;
import com.example.view.params.TableLayoutParams;
import com.example.view.utils.IDUtils;

public class ResourceUtil {	

	 public static int generateViewId() {
		 return IDUtils.generateViewId();        
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
