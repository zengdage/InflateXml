package com.example.drawable;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;


public class YDTransitionDrawable extends TransitionDrawable{
	private Context context;
	public YDTransitionDrawable(Context context){
		super(null);
	}
	
	@SuppressLint("NewApi")
	public void setAttributeSet(AttributeSet attrs,String itemName,int index){
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case left:
				break;
			case top:
				break;
			case right:
				break;
			case bottom:
				break;
			case drawable:
				Drawable drawable;
				String bString=attrs.getAttributeValue(i);
				if(bString.startsWith("@color/")||bString.startsWith("#")){
				    drawable=new ColorDrawable(YDResource.getInstance().getIntColor(bString));
				}else if(bString.startsWith("@drawable/")){
					//颜色drawable背景
					drawable=DrawableUtils.getDrawable(context, bString,"res");
				}
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
						this.setId(index, m);
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
	    int depth;
	    int index=0;
	    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
	            && ((depth = parser.getDepth()) >= innerDepth
	            || type != XmlPullParser.END_TAG)) {
	        if (type != XmlPullParser.START_TAG) {
	            continue;
	        }
	        if (depth > innerDepth||!parser.getName().equals("item")) {
	            continue;
	        }
	        setAttributeSet(attrs,parser.getName(),index);
	        index++;
	    }
	    //onStateChange(getState());
	}


}


