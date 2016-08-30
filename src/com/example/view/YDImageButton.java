package com.example.view;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

public class YDImageButton extends android.widget.ImageButton {

	private static final String TAG = "Button";
	private Context context;
	public YDImageButton(Context context, AttributeSet attrs) {
		super(context);
		this.context=context;
		setAttributeSet(attrs); 
	}
	
  @SuppressLint("NewApi")
public void setAttributeSet(AttributeSet attrs){	
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();	
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
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
					this.setId(m);
				}
				YDResource.getInstance().setViewId(idString,this);
				break;
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case visibility:
				String val2=attrs.getAttributeValue(i);
				if(!TextUtils.isEmpty(val2)){
					if(val2.equals("invisible")){
						this.setVisibility(View.INVISIBLE);
					}else if(val2.equalsIgnoreCase("gone")){
						this.setVisibility(View.GONE);
					}
				}
				break;
			case maxHeight:
				this.setMaxHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case maxWidth:
				this.setMaxWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case minHeight:
				this.setMinimumHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case minWidth:
				this.setMinimumWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case alpha:
				this.setAlpha(attrs.getAttributeFloatValue(i,0.5f));
				break;
			case background:
				String bString=attrs.getAttributeValue(i);
				//显示颜色背景
				if(bString.startsWith("@color/")||bString.startsWith("#")){
				    this.setBackgroundColor(YDResource.getInstance().getIntColor(bString));
				}else if(bString.startsWith("@drawable/")){
					//颜色drawable背景
					this.setBackgroundDrawable(DrawableUtils.getDrawable(context, bString,"res"));
				}
				break;
			case src:
				String srcString=attrs.getAttributeValue(i);
				//显示颜色背景
				if(srcString.startsWith("@color/")||srcString.startsWith("#")){
					this.setImageDrawable(new ColorDrawable(YDResource.getInstance().getIntColor(srcString)));
				}else if(srcString.startsWith("@drawable/")){
					//颜色drawable背景
					this.setImageDrawable(DrawableUtils.getDrawable(context, srcString,"res"));
				}
				break;
			case theme:
				break;
			default:
				break;
			}
    	}
    }
}
