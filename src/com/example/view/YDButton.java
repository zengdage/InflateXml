package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class YDButton extends android.widget.Button {

	private static final String TAG = "Button";
	private Context context;
	public YDButton(Context context, AttributeSet attrs) {
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
			case text:
				String value=YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setText(value);
				break;
			case textColor:
				this.setTextColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case textColorHint:
				this.setTextColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case textSize:
				String val1=attrs.getAttributeValue(i);
				if(!TextUtils.isEmpty(val1)){
					this.setTextSize(YDResource.getInstance().calculateTextSize(val1));
				}
				break;
			case ellipsize:
				String eString=attrs.getAttributeValue(i);
				if(eString.equalsIgnoreCase("start")){
					this.setEllipsize(TruncateAt.START);
				}else if(eString.equalsIgnoreCase("end")){
					this.setEllipsize(TruncateAt.END);
				}else if(eString.equalsIgnoreCase("middle")){
					this.setEllipsize(TruncateAt.MIDDLE);
				}else if(eString.equalsIgnoreCase("marquee")){
					this.setEllipsize(TruncateAt.MARQUEE);
				}
				break;
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case scrollHorizontally:
					this.setHorizontallyScrolling(attrs.getAttributeBooleanValue(i, false));
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
			case textStyle:
				String textStyle=attrs.getAttributeValue(i);
				if("bold".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
				}else if("normal".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
				}else if("italic".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
				}
				break;			
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
				break;
			case height:
				this.setHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case width:
				this.setWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
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
			case hint:
				String hint=YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setHint(hint);
				Log.i(TAG, hint);
				break;
			case lines:
				this.setLines(attrs.getAttributeIntValue(i, 1));
				break;
			case singleLine:
				this.setLines(attrs.getAttributeIntValue(i, 1));
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
			case style:
				String style=attrs.getAttributeValue(i);
				style=style.substring(style.indexOf("/")+1);
				Log.i("button","设置属性值");
				int id=YDResource.getInstance().getIdentifier("R.style."+style);
				this.setTextAppearance(getContext(), id);
				break;
			case src:				
				break;
			default:
				break;
			}
    	}
    }
	
}
