package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;

public class YDLinearLayout extends android.widget.LinearLayout implements Cloneable{

	private static final String TAG = "YDLinearLayout";
    private Context context;
	public YDLinearLayout(Context context, AttributeSet attrs) {
		super(context);
		this.context=context;
		setLayoutParams(generateLayoutParams(attrs));
	}

	@Override  
    public ViewGroup clone() {  
        YDLinearLayout stu = null;  
        try{  
            stu = (YDLinearLayout)super.clone();   //浅复制  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return stu;  
    }  
	
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params=generateDefaultLayoutParams();
		HashMap<String,ParamValue> map=YDResource.getInstance().getLayoutMap();
		
		int count = attrs.getAttributeCount();
		for(int i=0;i<count;i++){
			String name=attrs.getAttributeName(i);
			ParamValue key=map.get(name);
			if(key==null){
				continue;
			}
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
			case background:
				Log.i("LinearLayout", "设置背景颜色");
				this.setBackgroundColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case layout_centerHorizontal:
				params.gravity=Gravity.CENTER_HORIZONTAL;
				break;
			case layout_centerVertical:
				params.gravity=Gravity.CENTER_VERTICAL;
				break;
			case orientation:
				String orientation=attrs.getAttributeValue(i);
					if("horizontal".equalsIgnoreCase(orientation)){
						this.setOrientation(HORIZONTAL);
						Log.i(TAG, "设置了水平的布局");
					}else{
						this.setOrientation(VERTICAL);
					} 
					map.remove("orientation");
				break;
			case layout_weight:
				params.weight=attrs.getAttributeFloatValue(i, 0);
				break;
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
				break;
			case layout_margin:
				int margins=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.setMargins(margins,margins,margins,margins);
				break;
			case padding:
				int paddings=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				setPadding(paddings,paddings,paddings,paddings);
				break;
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
}
