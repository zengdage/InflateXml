package com.example.view;

import java.util.HashMap;

import com.example.inflatexml.R;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
/**
 * @author Codefarmer@sina.com
 */
public class YDImageView extends android.widget.ImageView {

	
	public YDImageView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
	}

	
	public void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> map=new HashMap<String, ParamValue>();
		map.put("src", ParamValue.src);
		
		
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case src:
				this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				YDResource.getInstance().displayImage(attrs.getAttributeValue(i), this);
				break;

			default:
				break;
			}
		}
	}
}
