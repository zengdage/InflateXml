package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;


public class YDLayerDrawable extends LayerDrawable{
	private Context context;
	public YDLayerDrawable(Context context){
		super(null);
		this.context=context;
	}
	
	@SuppressLint("NewApi")
	public void setAttributeSet(AttributeSet attrs,int index){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        int top=0,right=0,left=0,bottom=0;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case top:
				top=attrs.getAttributeIntValue(i,0);
				break;
			case right:
				right=attrs.getAttributeIntValue(i,0);
				break;
			case left:
				left=attrs.getAttributeIntValue(i,0);
				break;
			case bottom:
				bottom=attrs.getAttributeIntValue(i,0);
				break;
			case id:
				String idString =YDResource.getInstance().getID(attrs.getAttributeValue(i));
					if(YDResource.getInstance().getIDWithString(idString)==-1){
						int m;
						if(Build.VERSION.SDK_INT>=17){
						   m =View.generateViewId();
						}else{
						   m=ResourceUtil.generateViewId();
						}  
						YDResource.getInstance().setIDWithString(idString, m);
					}
				break;
			case drawable:
				String bString=attrs.getAttributeValue(i);
                if(bString.startsWith("@drawable/")){
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
				break;
			default:
				break;
			}
       }
		this.setLayerInset(index, left, top, right, bottom);
		this.setDrawableByLayerId(this.getId(index), drawable);
   }
	

	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	    int type;
	    final int innerDepth = parser.getDepth() + 1;
	    int depth;
	    int index=0;
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
	    //onStateChange(getState());
	}

}

