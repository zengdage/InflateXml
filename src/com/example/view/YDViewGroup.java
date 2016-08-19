package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public abstract class YDViewGroup extends ViewGroup {

	public YDViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public YDViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public YDViewGroup(Context context) {
		super(context);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
	
}
