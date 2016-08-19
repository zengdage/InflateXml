package com.example.view;

import com.example.inflatexml.R;
import com.example.view.YDRefreshView.OnFooterRefreshListener;
import com.example.view.YDRefreshView.OnHeaderRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Codefarmer@sina.com
 */
public class YDScrollView extends YDRefreshView {
	public YDScrollView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	public YDScrollView(Context context) {
		super(context);
	}

	private android.widget.ScrollView mScrollView;
	@Override
	public void addAdapterView(AttributeSet attrs) {
		mScrollView = new android.widget.ScrollView(getContext());
		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mScrollView.setBackgroundColor(0x00000000);
		if(attrs!=null){
			
			
			
		}
		addView(mScrollView, params);
	}
	

	
}
