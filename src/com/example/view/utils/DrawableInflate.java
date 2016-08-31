package com.example.view.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.StateListDrawable;
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
			        drawable = new StateListDrawable();
			    } else if (name.equals("level-list")) {
			        drawable = new LevelListDrawable();
			    } else if (name.equals("layer-list")) {
			       // drawable = new LayerDrawable();
			    } else if (name.equals("transition")) {
			       // drawable = new TransitionDrawable();
			    } else if (name.equals("color")) {
			        drawable = new ColorDrawable();
			    } else if (name.equals("shape")) {
			        drawable = new GradientDrawable();
			    } else if (name.equals("scale")) {
			       // drawable = new ScaleDrawable();
			    } else if (name.equals("clip")) {
			       // drawable = new ClipDrawable();
			    } else if (name.equals("rotate")) {
			        drawable = new RotateDrawable();
			    } else if (name.equals("animated-rotate")) {
			       // drawable = new AnimatedRotateDrawable();            
			    } else if (name.equals("animation-list")) {
			        drawable = new AnimationDrawable();
			    } else if (name.equals("inset")) {
			       // drawable = new InsetDrawable();
			    } else if (name.equals("bitmap")) {
			        //noinspection deprecation
			        drawable = new BitmapDrawable(context.getResources());
			        if (context.getResources()  != null) {
			           ((BitmapDrawable) drawable).setTargetDensity(context.getResources() .getDisplayMetrics());
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
