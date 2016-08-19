package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public abstract class Params {
	public Params(Context context){
		
	}
	public abstract ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs);

}
