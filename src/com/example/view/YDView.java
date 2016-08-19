package com.example.view;

import com.example.view.engine.YDResource;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class YDView extends View {
    public View view;
	public YDView(Context context, AttributeSet attrs) {
		super(context);
	}
	
	public YDView(Context context,View view) {
		super(context);
	}
	
	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
	}
	
	public View findViewByID(String idString){
		return YDResource.getInstance().getViewByID(idString);
	}
}
