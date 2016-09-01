package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;

public class YDColorDrawable extends ColorDrawable{
	private Context context;
	public YDColorDrawable(Context context){
		super();
		this.context=context;
	}
	
	@SuppressLint("NewApi")
	public void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		int j=0;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case color:
				String colorString=attrs.getAttributeValue(i);
				if(colorString.startsWith("@color/")||colorString.startsWith("#"))
				    drawable=new ColorDrawable(YDResource.getInstance().getIntColor(colorString));
				this.setColor(YDResource.getInstance().getIntColor(colorString));
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
