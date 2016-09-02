package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

public class YDStateListDrawable extends StateListDrawable{
	private Context context;
	public YDStateListDrawable(Context context){
		super();
		this.context=context;
	}
	
	public void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		int[] states = new int[count];
		int j=0;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
		       /*	final int stateResId = attrs.getAttributeNameResource(i);
	                if (stateResId == 0) 
	        	           break;
	                 states[j++] = attrs.getAttributeBooleanValue(i, false)
	                    ? stateResId
	                    : -stateResId;*/
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case pressed:
			    states[j++]=attrs.getAttributeBooleanValue(i, false)?android.R.attr.state_pressed:-android.R.attr.state_pressed;
				break;
			case enabled:
				states[j++]=attrs.getAttributeBooleanValue(i, false)?android.R.attr.state_enabled:-android.R.attr.state_enabled;
				break;
			case window_focused:
				states[j++]=attrs.getAttributeBooleanValue(i, false)?android.R.attr.state_window_focused:-android.R.attr.state_window_focused;
				break;
			case focused:
				states[j++]=attrs.getAttributeBooleanValue(i, false)?android.R.attr.state_focused:-android.R.attr.state_focused;
				break;
			case selected:
				states[j++]=attrs.getAttributeBooleanValue(i, false)?android.R.attr.state_selected:-android.R.attr.state_selected;
				break;
			case drawable:
				String bString=attrs.getAttributeValue(i);
				if(bString.startsWith("@color/")||bString.startsWith("#")){
				    drawable=new ColorDrawable(YDResource.getInstance().getIntColor(bString));
				}else if(bString.startsWith("@drawable/")){
					//颜色drawable背景
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
				break;
			case color:
				String colorString=attrs.getAttributeValue(i);
				if(colorString.startsWith("@color/")||colorString.startsWith("#"))
				    drawable=new ColorDrawable(YDResource.getInstance().getIntColor(colorString));
				break;
			default:
				break;
			}
       }
	   states = StateSet.trimStateSet(states, j);
	   this.addState(states, drawable);
   }
	@Override
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	    int type;
	    final int innerDepth = parser.getDepth() + 1;
	    int depth;
	    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
	            && ((depth = parser.getDepth()) >= innerDepth
	            || type != XmlPullParser.END_TAG)) {
	        if (type != XmlPullParser.START_TAG) {
	            continue;
	        }
	        if (depth > innerDepth || !parser.getName().equals("item")) {
	            continue;
	        }
	        setAttributeSet(attrs);
	    }
	}


}
