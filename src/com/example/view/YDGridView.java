package com.example.view;

import java.util.HashMap;
import com.example.view.YDRefreshView.OnFooterRefreshListener;
import com.example.view.YDRefreshView.OnHeaderRefreshListener;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.DensityUtil;
import com.example.view.utils.Logger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Codefarmer@sina.com
 */
public class YDGridView extends YDRefreshView {
	private static final String TAG = "GridView";
	
	public YDGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public YDGridView(Context context) {
		super(context);
	}
	
	android.widget.GridView mGridView;
	
	public void addAdapterView(AttributeSet attrs) {
		mGridView = new android.widget.GridView(getContext());
		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		if(attrs!=null){
			setAttributeSet(attrs);
		}
		this.addView(mGridView,params);
		
	}
	private void setAttributeSet(AttributeSet attrs){
		HashMap<String,ParamValue> maps=new HashMap<String,ParamValue>();
		maps.put("verticalSpacing", ParamValue.verticalSpacing);
		maps.put("numColumns", ParamValue.numColumns);
		maps.put("id", ParamValue.id);
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=maps.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case id:
				mGridView.setTag(attrs.getAttributeValue(i));
				break;
			case verticalSpacing:
				mGridView.setVerticalSpacing(DensityUtil.px2dip(getContext(), YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i))));
				break;
			case numColumns:
				mGridView.setNumColumns(attrs.getAttributeIntValue(i, 3));
				break;
			default:
				break;
			}
		}
	}
	
}
