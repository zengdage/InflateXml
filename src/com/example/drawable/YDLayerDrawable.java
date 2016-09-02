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
	private YDLayerDrawable(Context context){
		super(null);
		this.context=context;
	}
    @Override
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	   /* int type;
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
	    }*/
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
	

	
	@SuppressLint("NewApi")
	public static void setAttributeSet(AttributeSet attrs,Context context,int index,List<int []> listInt,List<Drawable> listDrawable){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
        int top=0,right=0,left=0,bottom=0;
        int []array=new int[6];
        array[0]=index;
        array[1]=0;
        array[2]=0;
        array[3]=0;
        array[4]=0;
        array[5]=View.NO_ID;
		Drawable drawable=null;
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case top:
				array[2]=attrs.getAttributeIntValue(i,0);
				break;
			case right:
				array[3]=attrs.getAttributeIntValue(i,0);
				break;
			case left:
				array[1]=attrs.getAttributeIntValue(i,0);
				break;
			case bottom:
				array[4]=attrs.getAttributeIntValue(i,0);
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
	    int type;
	    final int innerDepth = parser.getDepth() + 1;
	    int depth;
	    int index=0;
	    List<int []> listInt = new ArrayList<int []>();
	    List<Drawable> listDrawable = new ArrayList<Drawable>();
	    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
	            && ((depth = parser.getDepth()) >= innerDepth
	            || type != XmlPullParser.END_TAG)) {
	        if (type != XmlPullParser.START_TAG) {
	            continue;
	        }
	        if (depth > innerDepth || !parser.getName().equals("item")) {
	            continue;
	        }
	        setAttributeSet(attrs,context,index,listInt,listDrawable);
	        index++;
	    }
	    Drawable[] drawableArray=(Drawable[]) listDrawable.toArray();
	    LayerDrawable drawable2=new LayerDrawable(drawableArray);
	    for(int i=0;i<listInt.size();i++){
	    	int []padding=listInt.get(i);
	    	drawable2.setLayerInset(padding[0],padding[1],padding[2], padding[3], padding[4]);
	    	drawable2.setId(padding[0], padding[5]);
	    }
	    return drawable2;
	}
}

