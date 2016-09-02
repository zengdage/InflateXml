package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;


public class YDAnimationDrawable extends AnimationDrawable{
	private Context context;
	public YDAnimationDrawable(Context context){
		super();
		this.context=context;
	}
	
	public void setAttributeSet(AttributeSet attrs,int index){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		int[] states = new int[count];
		int j=0;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case drawable:
				String bString=attrs.getAttributeValue(i);
				if(bString.startsWith("@color/")||bString.startsWith("#")){
				    drawable=new ColorDrawable(YDResource.getInstance().getIntColor(bString));
				}else if(bString.startsWith("@drawable/")){
					//颜色drawable背景
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
				break;
			case duration:
				//this.setConstantState(state);
				//this.setExitFadeDuration(ms);
				//this.setEnterFadeDuration(ms);
				break;
			default:
				break;
			}
       }

   }
	
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	    int type;
	    final int innerDepth = parser.getDepth() + 1;
	    int depth;
	    int index=0;	    
	    this.setOneShot(attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/android","oneshot",true));
	    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
	            && ((depth = parser.getDepth()) >= innerDepth
	            || type != XmlPullParser.END_TAG)) {
	        if (type != XmlPullParser.START_TAG) {
	            continue;
	        }
	        if (depth > innerDepth || !parser.getName().equals("item")) {
	            continue;
	        }
	        setAttributeSet(attrs,index);
	        index++;
	    }
	}
}

