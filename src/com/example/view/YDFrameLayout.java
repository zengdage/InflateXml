package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

public class YDFrameLayout extends android.widget.FrameLayout {

	private static final String TAG = "FrameLayout";
	public YDFrameLayout(Context context, AttributeSet attrs) {
		super(context);
		setLayoutParams(generateLayoutParams(attrs));
	}
	@SuppressLint("NewApi")
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params=this.generateDefaultLayoutParams();
		HashMap<String,ParamValue> map=YDResource.getInstance().getLayoutMap();
		params.width=-2;
		params.height=-2;
		int count = attrs.getAttributeCount();
		for(int i=0;i<count;i++){
			String name=attrs.getAttributeName(i);
			ParamValue key=map.get(name);
			if(key==null){
				continue;
			}
			String val;
			switch (key) {
			case layout_width:
				String width=attrs.getAttributeValue(i);				
				if(width.startsWith("f")||width.startsWith("m")){
					params.width=LayoutParams.MATCH_PARENT;
					break;
				}
				if(width.startsWith("w")){
					params.width=LayoutParams.WRAP_CONTENT;
					break;
				}
				Log.i(TAG, "设置宽度"+params.width);
				params.width=YDResource.getInstance().calculateRealSize(width);
				break;
			case layout_height:
				String height=attrs.getAttributeValue(i);
				if(height.startsWith("f")||height.startsWith("m")){
					params.height=LayoutParams.MATCH_PARENT;;
					break;
				}
				if(height.startsWith("w")){
					params.height=LayoutParams.WRAP_CONTENT;;
					break;
				}
				params.height=YDResource.getInstance().calculateRealSize(height);
				break;
			case layout_centerHorizontal:
				params.gravity=Gravity.CENTER_HORIZONTAL;
				break;
			case layout_centerVertical:
				params.gravity=Gravity.CENTER_VERTICAL;
				break;
			case layout_margin:
				int margins=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.setMargins(margins,margins,margins,margins);
				break;
			case padding:
				int paddings=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				setPadding(paddings,paddings,paddings,paddings);
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
				}
				break;
			case theme:
				break;
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
	
}
