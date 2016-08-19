package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
/**
 * @author Codefarmer@sina.com
 */
public class YDRelativeLayout extends android.widget.RelativeLayout{
	
	private static final String TAG = "YDRelativeLayout";
	public YDRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public YDRelativeLayout(Context context, AttributeSet attrs) {
		super(context);
		/*for(int i=0;i<attrs.getAttributeCount();i++){
			Log.i("relative", attrs.getAttributeName(i)+"="+attrs.getAttributeValue(i));
		}*/
		setLayoutParams(generateLayoutParams(attrs));
		Log.i(TAG, ""+isShown());
	}

	public YDRelativeLayout(Context context) {
		super(context);
	}
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params=(LayoutParams) this.generateDefaultLayoutParams();
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
				params.width=YDResource.getInstance().calculateRealSize(width);
				break;
			case layout_height:
				String height=attrs.getAttributeValue(i);
				if(height.startsWith("f")||height.startsWith("m")){
					params.width=LayoutParams.MATCH_PARENT;
					break;
				}
				if(height.startsWith("w")){
					params.width=LayoutParams.WRAP_CONTENT;
					break;
				}
				params.height=YDResource.getInstance().calculateRealSize(height);
				break;
			case layout_centerHorizontal:
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(this.CENTER_HORIZONTAL,this.TRUE);
				}
				break;
			case layout_centerVertical:
				if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_VERTICAL, this.TRUE);
	    		}
				break;
			case layout_margin:
				params.bottomMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.leftMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.rightMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				params.topMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginLeft :
				params.leftMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginRight :
				params.rightMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_alignParentRight :
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, this.TRUE);
				}
				break;
			default:
				break;
			}
	    /*	Log.i("RelativeLayout : ", attrs.getAttributeName(i)+"="+attrs.getAttributeValue(i));
	    	
	    	if(name.equals("layout_width")){
	    		String value=attrs.getAttributeValue(i);
	    		if(value.equalsIgnoreCase("fill_parent")){
	    			params.width=Params.FILL_PARENT;
	    			
	    		}else if(value.equalsIgnoreCase("wrap_content")){
	    			params.width=Params.WRAP_CONTENT;
	    		}else{
	    			params.height=attrs.getAttributeIntValue(i, -2);
	    		}
	    	}else if(name.equals("layout_height")){
	    		String value=attrs.getAttributeValue(i);
	    		if(value.equalsIgnoreCase("fill_parent")){
	    			params.height=Params.FILL_PARENT;
	    		}else if(value.equalsIgnoreCase("wrap_content")){
	    			params.height=Params.WRAP_CONTENT;
	    		}else{
	    			params.height=attrs.getAttributeIntValue(i, -2);
	    		}
	    		
	    	}else if(name.equalsIgnoreCase("layout_centerHorizontal")){
	    		if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_HORIZONTAL,this.TRUE);
	    		}
	    	}else if(name.equalsIgnoreCase("layout_centerVertical")){
	    		if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_VERTICAL, this.TRUE);
	    		}
	    	}
	    	
    	}*/
		
		}
		return params;
	}
	
}
