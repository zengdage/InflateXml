package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

public class YDGradientDrawable extends GradientDrawable{
	private Context context;
	public YDGradientDrawable(Context context){
		super();
		this.context=context;
	}
	
	public void setAttributeSet(AttributeSet attrs,String itemName){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		int x=0,y=0;
		int width=0,height=0;
		int dashGap=0,dashWidth=0;
		int strokeColor=0xffffff;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case radius:
				this.setCornerRadius(attrs.getAttributeIntValue(i,0));
				break;
			case topLeftRadius:
			case topRightRadius:
				break;
			case bottomLeftRadius:
				break;
			case bottomRightRadius:
			    break;
			case angle:
				break;
			case gradientRadius:
				this.setGradientRadius(attrs.getAttributeIntValue(i,0));
				break;
			case type:
				String type=attrs.getAttributeValue(i);
				if (type.equalsIgnoreCase("linear")) {
					this.setGradientType(GradientDrawable.LINEAR_GRADIENT);
				}else if(type.equalsIgnoreCase("radial")) {
					this.setGradientType(GradientDrawable.RADIAL_GRADIENT);
				}else if(type.equalsIgnoreCase("sweep")) {
					this.setGradientType(GradientDrawable.SWEEP_GRADIENT);
				}
			case useLevel:
				this.setUseLevel(attrs.getAttributeBooleanValue(i,false));
				break;
			case centerX:
				x=attrs.getAttributeIntValue(i,0);
				if(y>=0)
					   this.setGradientCenter(x, y); 
				break;
			case centerY:
				y=attrs.getAttributeIntValue(i,0);
				if(x>=0)
				   this.setGradientCenter(x, y); 
				break;
			case endColor:
				break;
			case left:
				break;
			case top:
				break;
			case right:
				break;
			case bottom:
				break;
			case width:
				width=attrs.getAttributeIntValue(i,0);
				if(itemName.equalsIgnoreCase("size")){
				   if(i==count-1){
					this.setSize(width, height);
					width=0;
					height=0;
				   }
				}else if(itemName.equalsIgnoreCase("stroke")){
					if(i==count-1){
					     this.setStroke(width, strokeColor, dashWidth, dashGap);
					  }
				}
				break;
			case height:
				height=attrs.getAttributeIntValue(i,0);
				if(i==count-1){
					this.setSize(width, height);
					width=-1;
					height=-1;
				}
				break;
			case color:
				String cString=attrs.getAttributeValue(i);
			  if(itemName.equalsIgnoreCase("solid")){
				  this.setColor(YDResource.getInstance().getIntColor(cString));
			  }else if(itemName.equalsIgnoreCase("stroke")){
				  strokeColor=YDResource.getInstance().getIntColor(cString);
				  if(i==count-1){
				     this.setStroke(width, strokeColor, dashWidth, dashGap);
				  }
			  }
			case dashWidth:
				dashWidth=attrs.getAttributeIntValue(i,0);
				if(i==count-1){
				     this.setStroke(width, strokeColor, dashWidth, dashGap);
				  }
				break;
			case dashGap:
				dashGap=attrs.getAttributeIntValue(i,0);
				if(i==count-1){
				     this.setStroke(width, strokeColor, dashWidth, dashGap);
				  }
				break;
			  default :
				 
				break;
			}
			
        }


   }
	
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	    int type;
	    final int innerDepth = parser.getDepth() + 1;
	    String shape=attrs.getAttributeValue("http://schemas.android.com/apk/res/android","shape");
	    if(shape.equalsIgnoreCase("rectangle")){
	    	 this.setShape(GradientDrawable.RECTANGLE);
	    }else if(shape.equalsIgnoreCase("oval")){
	    	this.setShape(GradientDrawable.OVAL);
	    }else if(shape.equalsIgnoreCase("line")){
	    	this.setShape(GradientDrawable.LINE);
	    }else if(shape.equalsIgnoreCase("ring")){
	    	this.setShape(GradientDrawable.RING);
	    }
	    int depth;
	    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
	            && ((depth = parser.getDepth()) >= innerDepth
	            || type != XmlPullParser.END_TAG)) {
	        if (type != XmlPullParser.START_TAG) {
	            continue;
	        }
	        if (depth > innerDepth) {
	            continue;
	        }
	        setAttributeSet(attrs,parser.getName());
	    }
	    //onStateChange(getState());
	}


}

