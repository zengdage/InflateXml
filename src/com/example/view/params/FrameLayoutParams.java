package com.example.view.params;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class FrameLayoutParams extends Params{
   public FrameLayoutParams(Context context){
	   super(context);
   }
    public int mOrientation=0;
    public final static String TAG="LinearLayoutParams";
    public Context context;
   
    protected FrameLayout.LayoutParams  generateDefaultLayoutParams() {
    	
        if (mOrientation == 0) {
            return new FrameLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        } else if (mOrientation == 1) {
            return new FrameLayout.LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return null;
    }
    @SuppressLint("NewApi")
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		FrameLayout.LayoutParams params=generateDefaultLayoutParams();
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
			case layout_margin:
				int margins=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.setMargins(margins,margins,margins,margins);
				break;
			case layout_marginLeft:
				params.leftMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginRight:
				params.rightMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginTop:
				params.topMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginBottom:
				params.bottomMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginEnd:
				if(android.os.Build.VERSION.SDK_INT>17){
					params.setMarginEnd(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				}
				break;
			case layout_marginStart:
				if(android.os.Build.VERSION.SDK_INT>17){
					params.setMarginStart(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				}
				break;
			case layout_gravity:
				params.gravity=YDResource.getInstance().getGravity(attrs.getAttributeValue(i));
				break;
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
}


