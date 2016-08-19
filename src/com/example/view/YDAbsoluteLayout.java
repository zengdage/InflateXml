package com.example.view;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

@SuppressLint("NewApi")
public class YDAbsoluteLayout extends android.widget.AbsoluteLayout implements Cloneable{

	private static final String TAG = "YDLinearLayout";
    private Context context;
	public YDAbsoluteLayout(Context context, AttributeSet attrs) {
		super(context);
		this.context=context;
		setLayoutParams(generateLayoutParams(attrs));
	}

	@Override  
    public ViewGroup clone() {  
        YDAbsoluteLayout stu = null;  
        try{  
            stu = (YDAbsoluteLayout)super.clone();   //浅复制  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return stu;  
    }  
	
	 @Override
	    protected LayoutParams generateDefaultLayoutParams() {
	        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
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
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
}
