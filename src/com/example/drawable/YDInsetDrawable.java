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
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
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
		//setAttributeSet(attrs);
	}
	

	
	@SuppressLint("NewApi")
	public static void setAttributeSet(AttributeSet attrs,Context context,int index,List<int []> listInt,List<Drawable> listDrawable){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        int []array=new int[6];
        array[0]=1;  //level
        array[1]=0;  //insetTop
        array[2]=0;  //insetLeft
        array[3]=0;  //insetBottom
        array[4]=0;  //insetRight
        array[5]=View.NO_ID;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case level:
				array[0]=attrs.getAttributeIntValue(i,1);
				break;
			case insetTop:
				array[1]=attrs.getAttributeIntValue(i,1);
				break;
			case insetLeft:
				array[2]=attrs.getAttributeIntValue(i,1);
				break;
			case insetBottom:
				array[3]=attrs.getAttributeIntValue(i,1);
				break;
			case insetRight:
				array[4]=attrs.getAttributeIntValue(i,1);
				break;
			case id:
				String idString =YDResource.getInstance().getID(attrs.getAttributeValue(i));
					if(YDResource.getInstance().getIDWithString(idString)==-1){
						if(Build.VERSION.SDK_INT>=17){
							array[5] =View.generateViewId();
						}else{
							array[5]=ResourceUtil.generateViewId();
						}  
						YDResource.getInstance().setIDWithString(idString,array[5]);
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
	    InsetDrawable drawable2=new InsetDrawable(drawableArray[0], data[2], data[1], data[4],data[3]);
	    return drawable2;
	}
}

