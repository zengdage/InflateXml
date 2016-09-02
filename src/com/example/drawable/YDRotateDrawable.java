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
import android.graphics.drawable.RotateDrawable;
import android.util.AttributeSet;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

@SuppressLint("NewApi")
public class YDRotateDrawable extends RotateDrawable{
	private Context context;
	public YDRotateDrawable(Context context){
		super();
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
				this.setDrawable(drawable);
				break;
			case visible:
				this.setVisible(attrs.getAttributeBooleanValue(i,true),false);
				break;
			case pivotX:
				this.setPivotX(attrs.getAttributeFloatValue(i,0.5f));
				break;
			case pivotY:
				this.setPivotY(attrs.getAttributeFloatValue(i,0.5f));
				break;
			case fromDegrees:
				this.setFromDegrees(attrs.getAttributeFloatValue(i,90f));
				break;
			case toDegrees:
				this.setToDegrees(attrs.getAttributeFloatValue(i,90f));
				break;
			default:
				break;
			}
       }
   }
	
	@Override
	public void inflate(Resources r, XmlPullParser parser,
	        AttributeSet attrs)
	        throws XmlPullParserException, IOException {
	        setAttributeSet(attrs);
	}
}

