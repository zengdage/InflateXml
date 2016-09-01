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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.AttributeSet;

public class YDLevelListDrawable extends LevelListDrawable{
	private Context context;
	public YDLevelListDrawable(Context context){
		super();
		this.context=context;
	}
	
	public void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        int minLevel=0,maxLevel=0;
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
			case maxLevel:
				maxLevel=attrs.getAttributeIntValue(i,0);
				break;
			case minLevel:
				minLevel=attrs.getAttributeIntValue(i,0);
				break;
			case drawable:
				String bString=attrs.getAttributeValue(i);
                if(bString.startsWith("@drawable/")){
					//颜色drawable背景
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
				break;
			default:
				break;
			}
       }
	   this.addLevel(minLevel, maxLevel, drawable);
	   
   }
	
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
	    //onStateChange(getState());
	}


}
