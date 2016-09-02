package com.example.drawable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.AttrsUtil;
import com.example.view.utils.DrawableUtils;


public class YDScaleDrawable extends ScaleDrawable{
	private Context context;
	public YDScaleDrawable(Context context){
		super(null,1,0.0f,0.0f);
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
			case scaleGravity:
				break;
			case scaleHeight:
				break;
			case scaleWidth:
				break;
			case level:
				this.setLevel(attrs.getAttributeIntValue(i,1));
				break;
			default:
				break;
			}
       }
   }
	
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
		//setAttributeSet(attrs);
	}

	@SuppressLint("NewApi")
	public static void setAttributeSet(AttributeSet attrs,Context context,int index,List<float []> listInt,List<Drawable> listDrawable){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        float []array=new float[6];
        array[0]=1; //level
        array[1]=0; //Gravity
        array[2]=0; //width
        array[3]=0; //height
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case scaleGravity:
				array[1]=AttrsUtil.getScaleGravity(attrs.getAttributeValue(i));
				break;
			case scaleHeight:
				array[3]=attrs.getAttributeFloatValue(i,1.0f);
				break;
			case scaleWidth:
				array[2]=attrs.getAttributeFloatValue(i,1.0f);
				break;
			case level:
				array[0]=attrs.getAttributeIntValue(i,1);
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
		listInt.add(array);
		listDrawable.add(drawable);
   }
	
	public static Drawable inflate(Context context, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	    int index=0;
	    List<float []> listInt = new ArrayList<float []>();
	    List<Drawable> listDrawable = new ArrayList<Drawable>();	    
	    setAttributeSet(attrs,context,index,listInt,listDrawable);
	    Drawable[] drawableArray=(Drawable[]) listDrawable.toArray();
	    float []data=listInt.get(0);
	    ScaleDrawable scaleDrawable=new ScaleDrawable(drawableArray[0], (int)data[1], data[2], data[3]);
	    scaleDrawable.setLevel((int)data[0]);
	    return scaleDrawable;
	}
}
