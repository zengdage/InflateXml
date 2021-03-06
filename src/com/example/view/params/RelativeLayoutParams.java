package com.example.view.params;

import java.util.HashMap;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("NewApi")
public class RelativeLayoutParams extends Params{
    public int mOrientation=0;
    public final static String TAG="LinearLayoutParams";
    public Context context;
    public RelativeLayoutParams(Context context){
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
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				}
				break;
			case layout_centerVertical:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.CENTER_VERTICAL);
				}
				break;
			case layout_centerInparent:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.CENTER_IN_PARENT);
				}
				break;			
			case layout_alignParentBottom:
				if(attrs.getAttributeBooleanValue(i, false)){
				   params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				}
				break;
			case layout_alignParentLeft:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				}
				break;
			case layout_alignParentRight:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				}
				break;
			case layout_alignParentTop:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				}
				break;	
			case layout_alignParentEnd:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.ALIGN_PARENT_END);
				}
				break;
			case layout_alignParentStart:
				if(attrs.getAttributeBooleanValue(i, false)){
				  params.addRule(RelativeLayout.ALIGN_PARENT_START);
				}
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
							
			case layout_alignBaseline:
				params.addRule(RelativeLayout.ALIGN_BASELINE,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignEnd:
				params.addRule(RelativeLayout.ALIGN_END,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignStart:
				params.addRule(RelativeLayout.ALIGN_START,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignBottom:
				params.addRule(RelativeLayout.ALIGN_BOTTOM,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignLeft:
				params.addRule(RelativeLayout.ALIGN_LEFT,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignRight:
				params.addRule(RelativeLayout.ALIGN_RIGHT,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_alignTop:
				params.addRule(RelativeLayout.ALIGN_TOP,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_toLeftOf:
				params.addRule(RelativeLayout.LEFT_OF,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_toRightOf:
				params.addRule(RelativeLayout.RIGHT_OF,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_above:
				params.addRule(RelativeLayout.ABOVE,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			case layout_below:
				params.addRule(RelativeLayout.BELOW,
						YDResource.getInstance().getIDWithString(attrs.getAttributeValue(i)));
				break;
			default :
				Log.e(TAG, "未处理的属性:"+name);
				break;
			}
		}
		return params;
	}
}

