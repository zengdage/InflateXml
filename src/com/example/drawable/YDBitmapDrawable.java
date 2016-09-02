package com.example.drawable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

public class YDBitmapDrawable extends BitmapDrawable{
	private Context context;
	public YDBitmapDrawable(Context context){
		super();
		this.context=context;
	}
	
	public void setBitmap(Bitmap bitmap) {
		ConstantState constantState=this.getConstantState();
		try {
			Field field=constantState.getClass().getDeclaredField("mBitmap");
			field.setAccessible(true);
			field.set(constantState,bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			case src:
				String s=attrs.getAttributeValue(i);
				if(s.startsWith("@drawable/")){
					//颜色drawable背景
					BitmapDrawable bitmapdrawable=(BitmapDrawable)DrawableUtils.getDrawable(context,s,"res");
					this.setBitmap(bitmapdrawable.getBitmap());
				}
				break;
			case dither:
				this.setDither(attrs.getAttributeBooleanValue(i,false));
				break;
			case filter:
				this.setFilterBitmap(attrs.getAttributeBooleanValue(i,false));
				break;
			case tileMode:
				String tileMode=attrs.getAttributeValue(i);
				if(tileMode.equalsIgnoreCase("disabled")){
					//this.setTileModeX(TileMode.);					
				}else if(tileMode.equalsIgnoreCase("clamp")){
					this.setTileModeXY(TileMode.CLAMP,TileMode.CLAMP);					
				}else if(tileMode.equalsIgnoreCase("repeat")){
					this.setTileModeXY(TileMode.REPEAT,TileMode.REPEAT);					
				}else if(tileMode.equalsIgnoreCase("mirror")){
					this.setTileModeXY(TileMode.MIRROR,TileMode.MIRROR);
				}
				break;
			case antialias:
				this.setAntiAlias(attrs.getAttributeBooleanValue(i,false));
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
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
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
	        if (depth > innerDepth) {
	            continue;
	        }
	        setAttributeSet(attrs);
	    }
	    //onStateChange(getState());
	}


}

