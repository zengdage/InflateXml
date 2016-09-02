package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;


public class YDNinePatchDrawable extends NinePatchDrawable{
	private Context context;
	private YDNinePatchDrawable(Context context){
		super(null);
		this.context=context;
	}
	
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	}

	@SuppressLint("NewApi")
	public static Drawable setAttributeSet(AttributeSet attrs,Context context){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		 byte[] chunk1=null ;
		 Bitmap bitmap = null;
		 boolean dither=false;
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
					bitmap=bitmapdrawable.getBitmap();
					chunk1=bitmap.getNinePatchChunk();
				}
				break;
			case dither:
				dither=attrs.getAttributeBooleanValue(i,false);
           }
		}
		NinePatch ninePatch1=new NinePatch(bitmap, chunk1,null);
		NinePatchDrawable n1=new NinePatchDrawable(context.getResources(), ninePatch1);
		n1.setDither(dither);
		return n1;
   }
	
	public static Drawable inflate(Context context, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {	    
	    Drawable drawable=setAttributeSet(attrs,context);   
	    return drawable;
	}
}

