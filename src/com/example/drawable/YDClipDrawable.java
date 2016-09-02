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
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.AttrsUtil;
import com.example.view.utils.DrawableUtils;

public class YDClipDrawable extends ClipDrawable{
	private Context context;
	public YDClipDrawable(Context context){
        super(null, 1, 1);
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
			case gravity:
				break;
			case clipOrientation:
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
	public static void setAttributeSet(AttributeSet attrs,Context context,int index,List<int []> listInt,List<Drawable> listDrawable){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        int []array=new int[6];
        array[0]=1; //level
        array[1]=0; //Gravity
        array[2]=ClipDrawable.HORIZONTAL; //Orientation
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case gravity:
				array[1]=AttrsUtil.getScaleGravity(attrs.getAttributeValue(i));
				break;
			case clipOrientation:
				String oString=attrs.getAttributeValue(i);
				if(oString.equalsIgnoreCase("horizontal")){
					array[2]=ClipDrawable.HORIZONTAL;
				}else{
					array[2]=ClipDrawable.VERTICAL;
				}
				break;
			case drawable:
				String bString=attrs.getAttributeValue(i);
                if(bString.startsWith("@drawable/")){
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
				break;
			case level:
				array[0]=attrs.getAttributeIntValue(i,1);
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
	    List<int []> listInt = new ArrayList<int []>();
	    List<Drawable> listDrawable = new ArrayList<Drawable>();	    
	    setAttributeSet(attrs,context,index,listInt,listDrawable);
	    Drawable[] drawableArray=(Drawable[]) listDrawable.toArray();
	    int []data=listInt.get(0);
	    ClipDrawable scaleDrawable=new ClipDrawable(drawableArray[0],data[1], data[2]);
	    scaleDrawable.setLevel((int)data[0]);
	    return scaleDrawable;
	}
}
