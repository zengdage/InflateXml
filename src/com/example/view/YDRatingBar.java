package com.example.view;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;
import com.example.view.utils.DrawableUtils;

@SuppressLint("NewApi")
public class YDRatingBar extends android.widget.RatingBar{
	private boolean isMarquee=false;
	private static final String TAG = "TextView";
	private Context context;
	public YDRatingBar(Context context, AttributeSet attrs) {
		super(context);
		this.context=context;
		setAttributeSet(attrs);
	}
	@Override
	public boolean isFocused() {
		if(isMarquee){
			return true;
		}else{
			return super.isFocused();
		}
	}
	
	public void setAttributeSet(AttributeSet attrs){		
		HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			
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
			
			case padding:
				int paddings=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				this.setPadding(paddings,paddings,paddings,paddings);
				break;
			case paddingTop:
				this.setPadding(
						this.getPaddingLeft(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingRight(),
						this.getPaddingBottom());
				break;
			case paddingBottom:
				this.setPadding(
						this.getPaddingLeft(),
						this.getPaddingTop(),
						this.getPaddingRight(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case paddingLeft:
				this.setPadding(
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingTop(),
						this.getPaddingRight(),
						this.getPaddingBottom());
				break;
			case paddingRight:
				this.setPadding(
						this.getPaddingLeft(),
						this.getPaddingTop(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingBottom());
				break;
			case paddingEnd:
				break;
			case paddingStart:
				break;
			
			case minHeight:
				this.setMinimumHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case minWidth:
				this.setMinimumWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case id:
				String idString =YDResource.getInstance().getID(attrs.getAttributeValue(i));
				//this.setId(YDResource.getInstance().getStringHashCode(idString));
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
			case numStars:
				this.setNumStars(attrs.getAttributeIntValue(i,5));
			case theme:
				break;
			default:
				break;
			}
    	}
    }
  }

