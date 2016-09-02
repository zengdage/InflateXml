package com.example.view.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.drawable.YDAnimationDrawable;
import com.example.drawable.YDBitmapDrawable;
import com.example.drawable.YDClipDrawable;
import com.example.drawable.YDColorDrawable;
import com.example.drawable.YDGradientDrawable;
import com.example.drawable.YDInsetDrawable;
import com.example.drawable.YDLayerDrawable;
import com.example.drawable.YDLevelListDrawable;
import com.example.drawable.YDRotateDrawable;
import com.example.drawable.YDScaleDrawable;
import com.example.drawable.YDStateListDrawable;
import com.example.drawable.YDTransitionDrawable;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Xml;

public class DrawableInflate {
	public static Drawable createFromXml(Context context,XmlPullParser parser)
			throws XmlPullParserException, IOException{
		AttributeSet attrs = Xml.asAttributeSet(parser);
	    int type;
	    while ((type=parser.next()) != XmlPullParser.START_TAG &&
	            type != XmlPullParser.END_DOCUMENT) {
	        // Empty loop
	    }
	    if (type != XmlPullParser.START_TAG) {
	        throw new XmlPullParserException("No start tag found");
	    }
	    Drawable drawable = createFromXmlInner(context, parser, attrs);
	    if (drawable == null) {
	        throw new RuntimeException("Unknown initial tag: " + parser.getName());
	    }
	    return drawable;
	}
	
	
	public static Drawable createFromXmlInner(Context context, XmlPullParser parser, AttributeSet attrs)
			throws XmlPullParserException, IOException{
			    Drawable drawable=null;
			    final String name = parser.getName();
			    if (name.equals("selector")) {
			        drawable = new YDStateListDrawable(context);
			    } else if (name.equals("level-list")) {
			        drawable = new YDLevelListDrawable(context);
			    } else if (name.equals("layer-list")) {
			        drawable = new YDLayerDrawable(context);
			    } else if (name.equals("transition")) {
			        drawable = new YDTransitionDrawable(context);
			    } else if (name.equals("color")) {
			        drawable = new YDColorDrawable(context);
			    } else if (name.equals("shape")) {
			        drawable = new YDGradientDrawable(context);
			    } else if (name.equals("scale")) {
			        drawable = new YDScaleDrawable(context);
			    } else if (name.equals("clip")) {
			        drawable = new YDClipDrawable(context);
			    } else if (name.equals("rotate")) {
			        drawable = new YDRotateDrawable(context);
			    } else if (name.equals("animated-rotate")) {
			       // drawable = new AnimatedRotateDrawable();            
			    } else if (name.equals("animation-list")) {
			        drawable = new YDAnimationDrawable(context);
			    } else if (name.equals("inset")) {
			        drawable = new YDInsetDrawable(context);
			    } else if (name.equals("bitmap")) {
			        drawable = new YDBitmapDrawable(context);
			        if (context.getResources()  != null) {
			           ((BitmapDrawable) drawable).setTargetDensity(context.getResources().getDisplayMetrics());
			        }
			    } else if (name.equals("nine-patch")) {
			       // drawable = new NinePatchDrawable();
			        if (context.getResources() != null) {
			            ((NinePatchDrawable) drawable).setTargetDensity(context.getResources().getDisplayMetrics());
			         }
			    } else {
			        throw new XmlPullParserException(parser.getPositionDescription() +
			                ": invalid drawable tag " + name);
			    }

			    drawable.inflate(context.getResources(), parser, attrs);
			    return drawable;
			}
	
	
	
	
	      
}
