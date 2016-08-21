package com.example.view.params;

import java.util.HashMap;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint("NewApi")
public class LinearLayoutParams extends Params{
    public int mOrientation=0;
    public final static String TAG="LinearLayoutParams";
    public Context context;
    public LinearLayoutParams(Context context){
    	super(context);
    	this.context=context;
    }
    
    protected LayoutParams generateDefaultLayoutParams() {
    	
        if (mOrientation == 0) {
            return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        } else if (mOrientation == 1) {
            return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return null;
    }
    @SuppressLint("NewApi")
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
			case layout_centerHorizontal:
				params.gravity=Gravity.CENTER_HORIZONTAL;
				//params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				break;
			case layout_centerVertical:
				//params.addRule(RelativeLayout.CENTER_VERTICAL);
				params.gravity=Gravity.CENTER_VERTICAL;
				break;
			case layout_centerInparent:
				//params.addRule(RelativeLayout.CENTER_IN_PARENT);
				//params.gravity=Gravity.CENTER;
				break;
			case layout_weight:
				//params.weight=attrs.getAttributeFloatValue(i, 0);
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
				//params.addRule(RelativeLayout.);
				break;
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
}
