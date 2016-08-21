package com.example.view;

import java.util.HashMap;

import com.example.view.engine.ParamValue;
import com.example.view.engine.ResourceUtil;
import com.example.view.engine.YDResource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

public class YDEditText extends android.widget.EditText {

	private static final String TAG = "EditText";
	public YDEditText(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
	}
	
  @SuppressLint("NewApi")
public void setAttributeSet(AttributeSet attrs){
	  HashMap<String,ParamValue> map=YDResource.getInstance().getViewMap();
		int count =attrs.getAttributeCount();
		for(int i=0;i<count ;i++){
			ParamValue key=map.get(attrs.getAttributeName(i));
			if(key==null){
				continue;
			}
			switch (key) {
			case text:
				String value=YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setText(value);
				Log.i(TAG, value);
				break;
			case textColor:
				this.setTextColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case textColorHint:
				this.setTextColor(YDResource.getInstance().getIntColor(attrs.getAttributeValue(i)));
				break;
			case textSize:
				String val1=attrs.getAttributeValue(i);
				if(!TextUtils.isEmpty(val1)){
					this.setTextSize(YDResource.getInstance().calculateTextSize(val1));
				}
				break;
			case textStyle:
				String textStyle=attrs.getAttributeValue(i);
				if("bold".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
				}else if("normal".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
				}else if("italic".equalsIgnoreCase(textStyle)){
				   this.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
				}
				break;
			case hint:
				String hint=YDResource.getInstance().getString(attrs.getAttributeValue(i));
				this.setHint(hint);
				Log.i(TAG, hint);
				break;
			case visibility:
				String val2=attrs.getAttributeValue(i);
				if(!TextUtils.isEmpty(val2)){
					if(val2.equals("invisible")){
						this.setVisibility(View.INVISIBLE);
					}else if(val2.equalsIgnoreCase("gone")){
						this.setVisibility(View.GONE);
					}
				}
				break;
			case height:
				this.setHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case width:
				this.setWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case maxHeight:
				this.setMaxHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case maxWidth:
				this.setMaxWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case minHeight:
				this.setMinimumHeight(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case minWidth:
				this.setMinimumWidth(YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case padding:
				int paddings=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				this.setPadding(paddings,paddings,paddings,paddings);
				break;
			case paddingTop:
				this.setPadding(
						this.getPaddingLeft(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingRight(),
						this.getPaddingBottom());
				break;
			case paddingBottom:
				this.setPadding(
						this.getPaddingLeft(),
						this.getPaddingTop(),
						this.getPaddingRight(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)));
				break;
			case paddingLeft:
				this.setPadding(
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingTop(),
						this.getPaddingRight(),
						this.getPaddingBottom());
				break;
			case paddingRight:
				this.setPadding(
						this.getPaddingLeft(),
						this.getPaddingTop(),
						YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i)),
						this.getPaddingBottom());
				break;
			case paddingEnd:
				break;
			case paddingStart:
				break;
			case ellipsize:
				String eString=attrs.getAttributeValue(i);
				if(eString.equalsIgnoreCase("start")){
					this.setEllipsize(TruncateAt.START);
				}else if(eString.equalsIgnoreCase("end")){
					this.setEllipsize(TruncateAt.END);
				}else if(eString.equalsIgnoreCase("middle")){
					this.setEllipsize(TruncateAt.MIDDLE);
				}else if(eString.equalsIgnoreCase("marquee")){
					this.setEllipsize(TruncateAt.MARQUEE);
				}
				break;
			case scrollHorizontally:
				this.setHorizontallyScrolling(attrs.getAttributeBooleanValue(i, false));
				break;	
			case fadingEdge:
				this.setHorizontalFadingEdgeEnabled(attrs.getAttributeBooleanValue(i, false));
				break;
			case id:
				String idString =YDResource.getInstance().getID(attrs.getAttributeValue(i));
				//this.setId(YDResource.getInstance().getStringHashCode(idString));
				if(YDResource.getInstance().getIDWithString(idString)==-1){
					int m;
					if(Build.VERSION.SDK_INT>=17){
					   m =View.generateViewId();
					}else{
					   m=ResourceUtil.generateViewId();
					}  
					YDResource.getInstance().setIDWithString(idString, m);
					this.setId(m);
				}
				YDResource.getInstance().setViewId(idString,this);
				break;
			case lines:
				this.setLines(attrs.getAttributeIntValue(i, 1));
				break;
			case singleLine:
				this.setLines(attrs.getAttributeIntValue(i, 1));
				break;
			case gravity:
				this.setGravity(YDResource.getInstance().getGravity(attrs.getAttributeValue(i)));
				break;
			case password:
				if(attrs.getAttributeBooleanValue(i, false)){
				   this.setTransformationMethod(PasswordTransformationMethod.getInstance());
			    }
				break;
			case maxLength:
				this.setFilters(
						new InputFilter[]{new InputFilter.LengthFilter(
								attrs.getAttributeIntValue(i,100))});
				break;
			case focusable:
				this.setFocusable(attrs.getAttributeBooleanValue(i,true));
				break;
			case cursorVisible:
				this.setCursorVisible(attrs.getAttributeBooleanValue(i,true));
				break;
			case phoneNumber:
				this.setInputType(InputType.TYPE_CLASS_PHONE);
				break;
			case numeric:
				//只接受整数输入  
				KeyListener l = null;  
				String numericString=attrs.getAttributeValue(i);
				if(numericString.equals("signed")){
					l=new DigitsKeyListener(true,false);
				}else if(numericString.equals("integer")){
					l = new DigitsKeyListener(false,false);
				}else if(numericString.equals("decimal")){
					l = new DigitsKeyListener(false,true); 
				}else if(numericString.contains("signed")&&numericString.contains("decimal")){
					l = new DigitsKeyListener(true,true); 
				}
				this.setKeyListener(l);
				break;
			case digits:
				int j=Gravity.BOTTOM;
				final String digitString=attrs.getAttributeValue(i);
				NumberKeyListener myKeyListener =
		         new NumberKeyListener() {
		            public int getInputType() {
		              //指定键盘类型
		                 return InputType.TYPE_CLASS_TEXT;
		             }
		             protected char[] getAcceptedChars() {
		                return digitString.toCharArray();
		             }
		         };
				break;
			case inputType:
				this.setInputType(YDResource.getInstance().getInputType(attrs.getAttributeValue(i)));
				break;
			case editable:
				this.setEnabled(attrs.getAttributeBooleanValue(i,true));
				break;
			case alpha:
				this.setAlpha(attrs.getAttributeFloatValue(i,0.5f));
				break;
			case capitalize:
				break;				
			case background:
				String bString=attrs.getAttributeValue(i);
				//显示颜色背景
				if(bString.startsWith("@color/")||bString.startsWith("#")){
				    this.setBackgroundColor(YDResource.getInstance().getIntColor(bString));
				}else if(bString.startsWith("@drawable/")){
					//颜色drawable背景
				}
				break;			
			case style:
				String style=attrs.getAttributeValue(i);
				style=style.substring(style.indexOf("/")+1);
				Log.i("textview","设置属性值");
				int id=YDResource.getInstance().getIdentifier("R.style."+style);
				this.setTextAppearance(getContext(), id);
				break;			
			default:
				break;
			}
  	}
    }
}


