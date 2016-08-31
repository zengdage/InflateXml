package com.example.view.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.util.Xml;

import com.example.view.engine.YDResource;

public class DrawableUtils {
	
	 public static void getDrawableMap(Context context){
		   try{
			   String []fileNames=context.getAssets().list("res");
			   for(int i=0;i<fileNames.length;i++){
		        	if(fileNames[i].startsWith("drawable")){
		        		getDrawablMapByVGA(context,fileNames[i]);
		        	}
		       }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	   }
	  
	  public static SoftReference<HashMap<String,String>> getDrawablMapByVGA(Context context,String vga){
		  if(vga.equalsIgnoreCase("drawable-hdpi")){
			  if(YDResource.getInstance().wkDrawableHMap==null ||YDResource.getInstance().wkDrawableHMap.get()==null){
				  YDResource.getInstance().wkDrawableHMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableHMap;
			  }else{
				  return YDResource.getInstance().wkDrawableHMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable-mdpi")){
			  if(YDResource.getInstance().wkDrawableMMap==null || YDResource.getInstance().wkDrawableMMap.get()==null){
				  YDResource.getInstance().wkDrawableMMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableMMap;
			  }else{
				  return YDResource.getInstance().wkDrawableMMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable-ldpi")){
			  if(YDResource.getInstance().wkDrawableLMap==null || YDResource.getInstance().wkDrawableLMap.get()==null){
				  YDResource.getInstance().wkDrawableMMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableLMap;
			  }else{
				  return YDResource.getInstance().wkDrawableLMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable-xhdpi")){
			  if(YDResource.getInstance().wkDrawableXHMap==null || YDResource.getInstance().wkDrawableXHMap.get()==null){
				  YDResource.getInstance().wkDrawableXHMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableXHMap;
			  }else{
				  return YDResource.getInstance().wkDrawableXHMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable-xxhdpi")){
			  if(YDResource.getInstance().wkDrawableXXHMap==null || YDResource.getInstance().wkDrawableXXHMap.get()==null){
				  YDResource.getInstance().wkDrawableXHMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableXXHMap;
			  }else{
				  return YDResource.getInstance().wkDrawableXXHMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable")){
			  if(YDResource.getInstance().wkDrawableMap==null || YDResource.getInstance().wkDrawableMap.get()==null){
				  YDResource.getInstance().wkDrawableMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableMap;
			  }else{
				  return YDResource.getInstance().wkDrawableMap;
			  }
		  }else if(vga.equalsIgnoreCase("drawable-xxxhdpi")){
			  if(YDResource.getInstance().wkDrawableXXXHMap==null || YDResource.getInstance().wkDrawableXXXHMap.get()==null){
				  YDResource.getInstance().wkDrawableXXXHMap=new SoftReference<HashMap<String,String>>(DrawableUtils.readDrawableList(context,vga));
				  return YDResource.getInstance().wkDrawableXXXHMap;
			  }else{
				  return YDResource.getInstance().wkDrawableXXXHMap;
			  }
		  }
		  return null;
	  }

	  public static HashMap<String,String> readDrawableList(Context context,String vga){
		   return saveDrawableInMap(context, vga);
	 }
		  
	  public static String getDrawableName(String name,SoftReference<HashMap<String,String>> SoftMap){
			  try{
		        HashMap<String, String> map=SoftMap.get();
		        return map.get(name);
			  }catch(Exception e){
				  e.printStackTrace();
				  return null;
			  }
	} 
	  
		public static HashMap<String, String> saveDrawableInMap(Context context,String vga){
			HashMap<String, String> map=new HashMap<String, String>();
			try {  
		         String fileNames[] = context.getAssets().list("res/"+vga);//获取assets目录下的所有文件及目录名   
		         for(int i=0;i<fileNames.length;i++){
		        	 map.put(fileNames[i].substring(0,fileNames[i].indexOf(".")-1),fileNames[i]);
		         }
		         return map;
		     } catch (Exception e) {  
		       e.printStackTrace();  
		       return null;
		     }              
		}

		
		public static Drawable getXmlDrawable(Context context,String xmlFileName){
			XmlPullParser parser=XmlParseUtil.getXmlPullParser(xmlFileName, context);
		    try {
				return DrawableInflate.createFromXml(context,parser);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return null;
		}

		/**
		 * 从drawable系列目录下获取drawable
		 * @param context
		 * @param imagename 图像名。不包括后缀名
		 * @param rootpath  根路径
		 * @return 
		 */
		public static Drawable getDrawable(Context context,String imagename,String rootpath){
			Drawable drawable=null;
            //以@drawable/开头
			if(imagename.startsWith("@drawable/")){
				imagename=imagename.substring(10);
				StringBuilder sb=new StringBuilder();
				//得到图像名（包括后缀名），文件路径
				String name=DrawableUtils.findDrawablePath(context, imagename);
				sb.append(rootpath).append("/"+
				    YDResource.getInstance().vga+"/").append(name);
				String last=sb.toString().substring(sb.toString().lastIndexOf("."));
				if(last.equalsIgnoreCase("jpg")||
						last.equalsIgnoreCase("jpeg")||
						last.equalsIgnoreCase("png")){
					//直接获取图片资源
					drawable=getBitmapDrawable(context, sb.toString());
				}else if(last.equalsIgnoreCase("xml")){
					
				}
			}
			return drawable;
		}
		/**
		 * 获取到图片资源
		 * @param context
		 * @param imagename 图片路径（包括后缀名）
		 * @return
		 */
		public static Drawable getBitmapDrawable(Context context,String imagename){
			Drawable drawable=null;
			InputStream is=null;
			 try {
				if(YDResource.assetsFlag){
					  is=context.getAssets().open(imagename);
				}else{
					  is=new FileInputStream(imagename);
				}
				String vga=YDResource.getInstance().vga;
				float num=160.0f;
				if(vga.equals("drawable-xxxhdpi")){
					num=640.0f;
				}
				if(vga.equals("drawable-xxhdpi")){
					num=480.0f;			
				}
				if(vga.equals("drawable-xhdpi")){
					num=320.0f;
				}
				if(vga.equals("drawable-hdpi")){
					num=240.0f;				
				}
				if(vga.equals("drawable-mdpi")){
					num=160.0f;	
				}
				if(vga.equals("drawable-ldpi")){
					num=120.0f;
				}
				if(vga.equals("drawable")){
					num=480.0f;	
				}
				drawable=new BitmapDrawable(context.getResources(),scaleBitmap(context,num,BitmapFactory.decodeStream(is)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		   return drawable;
		}

		 /**
		 * 缩放bitmap图
		 * @param context
		 * @param bitmap 图片
		 * @return 放大后的图片
		 */
		public static Bitmap scaleBitmap(Context context,float imageDensity,Bitmap bitmap){
			float num=DensityUtil.getDensity(context)/imageDensity;
			Matrix matrix = new Matrix();
	    	matrix.postScale(num,num); //长和宽放大缩小的比例
	    	Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
			return resizeBmp;
		}
		
		public Drawable getDrawable(Context context,String imagename,String vga,String rootpath){
			Drawable drawable=null;
			if(imagename.startsWith("@drawable/")){
				imagename=imagename.substring(10);
				InputStream is=null;
				StringBuilder sb=new StringBuilder();
				sb.append(rootpath).append(vga).append(imagename).append(".png");
			    try {
					if(YDResource.assetsFlag){
					   is=context.getAssets().open(sb.toString());
					}else{
					   is=new FileInputStream(sb.toString());
				    }
				} catch (IOException e) {
					e.printStackTrace();
				}
		        drawable=new BitmapDrawable(context.getResources(),scaleBitmap(context,240.0f,BitmapFactory.decodeStream(is)));
			}
			return drawable;
		}
	  
	 
	   
		 public static SoftReference<HashMap<String,String>> getDrawablMapByNum(int num){
			 switch (num) {
			case 0:		
				return YDResource.getInstance().wkDrawableLMap;
			case 1:
            	return YDResource.getInstance().wkDrawableMMap;
			case 2:		
            	 return YDResource.getInstance().wkDrawableHMap;
			case 3:
            	return YDResource.getInstance().wkDrawableXHMap;
			case 4:	
            	return YDResource.getInstance().wkDrawableXXHMap;
			case 5:
            	 return YDResource.getInstance().wkDrawableXXXHMap;
			default:
				return YDResource.getInstance().wkDrawableMap;
			}
		  }
		 
		 public static String getDrawablPathByNum(int num){
			 switch (num) {
			case 0:		
				return "drawable-ldpi";
			case 1:
            	return "drawable-mdpi";
			case 2:		
            	 return "drawable-hdpi";
			case 3:
            	return "drawable-xhdpi";
			case 4:	
            	return "drawable-xxhdpi";
			case 5:
            	 return "drawable-xxxhdpi";
			default:
				return "drawable";
			}
		  }
	  
	  public static String findDrawablePath(Context context,String name){
		 int desity=DensityUtil.getDensity(context);
		 int num=2;	
		 int [] desitys={0,120,160,240,320,480};
		 for(int i=desitys.length-1;i>=0;i--){
			 if(desity>desitys[i]){
				 num=i;
			 }
		 }
		 String drawableName=null;
		 for(int i=num;i<desitys.length;i++){
			 if(drawableName!=null){
				 return drawableName;
			 }else{
				 drawableName=DrawableUtils.getDrawableName(name,getDrawablMapByNum(i));
				 YDResource.getInstance().vga=getDrawablPathByNum(num);
			 }
		 }
		 for(int i=num-1;i>=0;i--){
			 if(drawableName!=null){
				 return drawableName;
			 }else{
				 drawableName=DrawableUtils.getDrawableName(name,getDrawablMapByNum(i));
				 YDResource.getInstance().vga=getDrawablPathByNum(num);
			 }
		 }
		/* if((drawableName==null)&&desity>480){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableXXXHMap);
			 YDResource.getInstance().vga="drawable-xxxhdpi";
		 }
		 if((drawableName==null)&&desity>320){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableXXHMap);
			 YDResource.getInstance().vga="drawable-xxhdpi";
		 }
		 if((drawableName==null)&&desity>240){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableXHMap);
			 YDResource.getInstance().vga="drawable-xhdpi";
		 }
		 if((drawableName==null)&&desity>160){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableHMap);
			 YDResource.getInstance().vga="drawable-hdpi";
		 }
		 if((drawableName==null)&&desity>120){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableMMap);
			 YDResource.getInstance().vga="drawable-mdpi";
		 }
		 if((drawableName==null)&&desity>0){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableLMap);
			 YDResource.getInstance().vga="drawable-ldpi";
		 }
		 if((drawableName==null)){
			 drawableName=DrawableUtils.getDrawableName(name,YDResource.getInstance().wkDrawableMap);
			 YDResource.getInstance().vga="drawable";
		 }*/
		 return drawableName;
	  }
}
