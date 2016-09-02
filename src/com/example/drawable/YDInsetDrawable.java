package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;



public class YDInsetDrawable extends InsetDrawable{
	private Context context;
	public YDInsetDrawable(Context context){
        super(null,1);
		this.context=context;
	}
	
	public void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
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
			case level:
				this.setLevel(attrs.getAttributeIntValue(i,1));
				break;
			case insetTop:
				break;
			case insetLeft:
				break;
			case insetBottom:
				break;
			case insetRight:
				break;
			default:
				break;
			}
       }
   }
	
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
		setAttributeSet(attrs);
	}
}

