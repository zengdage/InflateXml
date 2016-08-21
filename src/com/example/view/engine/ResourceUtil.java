package com.example.view.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.view.params.AbsoluteLayoutParams;
import com.example.view.params.FrameLayoutParams;
import com.example.view.params.LinearLayoutParams;
import com.example.view.params.Params;
import com.example.view.params.RelativeLayoutParams;
import com.example.view.params.TableLayoutParams;
import com.example.view.utils.DensityUtil;

public class ResourceUtil {	
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

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

	
	public Drawable getBitmapDrawable(Context context,String imagename){
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
	
	public Drawable getDrawable(Context context,String imagename,String rootpath){
		Drawable drawable=null;
		if(imagename.startsWith("@drawable/")){
			imagename=imagename.substring(10);
			InputStream is=null;
			StringBuilder sb=new StringBuilder();
			sb.append(rootpath).append("/"+
			    YDResource.getInstance().vga+"/").append(
			    YDResource.getInstance().findDrawablePath(context, imagename));
			String last=sb.toString().substring(sb.toString().lastIndexOf("."));
			if(last.equalsIgnoreCase("jpg")||
					last.equalsIgnoreCase("jpeg")||
					last.equalsIgnoreCase("png")){
				drawable=getBitmapDrawable(context, sb.toString());
			}
		}
		return drawable;
	}
	
	/*public Drawable getDrawable(Context context,String imagename,String vga,String rootpath){
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
	}*/
	
	
	 public static int generateViewId() {
	        for (;;) {
	            final int result = sNextGeneratedId.get();
	            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	            int newValue = result + 1;
	            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	            if (sNextGeneratedId.compareAndSet(result, newValue)) {
	                return result;
	            }
	        }
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
	 
	   /**
	     * 创建配置信息
	     * @param context
	     * @param name
	     * @return
	     */
	    public static Params createParams(Context context,String name){
	    	Params params=null;
	    	if(name.equals("com.example.view.YDLinearLayout")){
	    		params=new LinearLayoutParams(context);
	    	}
	    	else if(name.equals("com.example.view.YDRelativeLayout")){
	    		params=new RelativeLayoutParams(context);
	    	}
	    	else if(name.equals("com.example.view.YDFrameLayout")){
	    		params=new FrameLayoutParams(context);
	    	}
	       else if(name.equals("com.example.view.YDTableLayout")){
	    	   params=new TableLayoutParams(context);
	    	}
	       else if(name.equals("com.example.view.YDAbsoluteLayout")){
	    	   params=new AbsoluteLayoutParams(context);
	    	}
	    	return params;
	    }

}
