package com.example.view;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.example.inflatexml.R;
import com.example.view.YDRefreshView.OnFooterRefreshListener;
import com.example.view.YDRefreshView.OnHeaderRefreshListener;
import com.example.view.utils.Logger;

/**
 * @author Codefarmer@sina.com
 */
public class YDListView extends YDRefreshView {
	private android.widget.ListView mListView;
	public YDListView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	public YDListView(Context context) {
		super(context);
	}
	@Override
	public void addAdapterView(AttributeSet attrs) {
		mListView = new android.widget.ListView(getContext());
		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		mListView.setCacheColorHint(0x00000000);
		if(attrs!=null){
			
			
		}
		addView(mListView, params);
	}
}
