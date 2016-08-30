package com.example.view.engine;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.example.view.utils.DensityUtil;
import com.example.view.utils.DrawableUtils;
import com.example.view.utils.Logger;
import com.example.view.utils.ReadXmlUtils;

public class YDResource {
	
	private static final String TAG = "YDResource";
	//layout_xxx级标称值
	private  SoftReference<HashMap<String,ParamValue>> wkMap;
	//字符串键值对
	private  SoftReference<HashMap<String,String>> wkstrings;
	//属性标称值
	private  SoftReference<HashMap<String,ParamValue>> wkViewMap;
	//颜色键值对
	private  SoftReference<HashMap<String,String>> wkColorMap;
	//数值键值对
	private  SoftReference<HashMap<String,String>> wkDimenMap;
	//ID键值对
	private  SoftReference<HashMap<String,View>> wkIdMap;
	//View键值对
	private SoftReference<HashMap<View,SoftReference<HashMap<String,View>>>> wkViewIDMap;
	
	public SoftReference<HashMap<String,String>> wkDrawableHMap;
	public SoftReference<HashMap<String,String>> wkDrawableMMap;
	public SoftReference<HashMap<String,String>> wkDrawableLMap;
	public SoftReference<HashMap<String,String>> wkDrawableXHMap,wkDrawableXXHMap,wkDrawableMap,wkDrawableXXXHMap;
	private HashMap<String, Integer> idMap;
	public static String rootpath="";
	public String vga="drawable-hdpi";
	public static boolean assetsFlag=true;
	private Context mContext;
	private YDResource(){}
	private static YDResource resource=new YDResource();

	public static YDResource getInstance(){
		return resource;
	}
	/**
	 * 初始化资源根路径 
	 * @param mContext 上下文
	 * @param path 路径
	 */
	public void initResourcePath(Context mContext,String path){		
		if(path.equals("")||path==null){
			//data/data/packageName/files/res
			rootpath="res";
			assetsFlag=true;
		}else{
			rootpath=path;
			assetsFlag=false;
		}
		this.mContext=mContext;
	}
	
	public int getIDWithString(String key){
		if(idMap==null){
			idMap=new HashMap<String, Integer>();
		}
		Integer id=idMap.get(key);
		if(idMap.get(key)==null){
			return -1;
		}
		return id.intValue();
	}
	
	public boolean setIDWithString(String key,int id){
		if(idMap==null){
			idMap=new HashMap<String, Integer>();
		}
		if(idMap.get(key)==null){
		   idMap.put(key,Integer.valueOf(id));
		   return true;
		}else{
		   return false;
		}
	}
	
	public void setViewIDMap(View v,SoftReference<HashMap<String,View>> Map){
		HashMap<View,SoftReference<HashMap<String, View>>> map;
		if(wkViewIDMap==null||wkViewIDMap.get()==null){
			map=new HashMap<View,SoftReference<HashMap<String, View>>>();
		}else{
			map=wkViewIDMap.get();
		}
		map.put(v, Map);
		wkViewIDMap=new SoftReference<HashMap<View,SoftReference<HashMap<String,View>>>>(map);
	}
	
	public SoftReference<HashMap<String,View>> getIDMap(View v){
		HashMap<View,SoftReference<HashMap<String, View>>> map;
		if(wkViewIDMap==null||wkViewIDMap.get()==null){
			return null;
		}else{
			map=wkViewIDMap.get();
			return map.get(v);
		}
	}
	
	public void setViewId(String s,View v){
		HashMap<String, View> map;
		if(wkIdMap==null||wkIdMap.get()==null){
			map=new HashMap<String, View>();
		}else{
			map=wkIdMap.get();
		}
		map.put(s, v);
		wkIdMap=new SoftReference<HashMap<String,View>>(map);
	}

	public View getViewByID(String s,SoftReference<HashMap<String,View>> wkIdMap){
		HashMap<String, View> map;
		if(wkIdMap==null||wkIdMap.get()==null){
			return null;
		}else{
			map=wkIdMap.get();
			return map.get(s);
		}
	}
	/**
	 * 获取布局标称值
	 * @return 
	 */
	public  HashMap getLayoutMap(){
		if(wkMap==null||wkMap.get()==null){
			HashMap<String,ParamValue> map=new HashMap<String, ParamValue>();
			map.put("layout_width", ParamValue.layout_width);
			map.put("layout_height", ParamValue.layout_height);
			map.put("orientation", ParamValue.orientation);
			map.put("layout_centerHorizontal", ParamValue.layout_centerHorizontal);
			map.put("layout_centerVertical", ParamValue.layout_centerVertical);
			map.put("layout_marginLeft", ParamValue.layout_marginLeft);
			map.put("layout_marginRight", ParamValue.layout_marginRight);
			map.put("layout_margin", ParamValue.layout_margin);
			map.put("layout_gravity", ParamValue.layout_gravity);
			map.put("layout_alignParentRight", ParamValue.layout_alignParentRight);
			map.put("layout_weight", ParamValue.layout_weight);
			wkMap=new SoftReference<HashMap<String,ParamValue>>(map);
		}
		return wkMap.get();
	}
	/**
	 * 获取属性标称值
	 * @return
	 */
	public  HashMap<String,ParamValue> getViewMap(){
		if(wkViewMap==null || wkViewMap.get()==null){
			HashMap<String,ParamValue> map=new HashMap<String, ParamValue>();
			map.put("id", ParamValue.id);
			map.put("text", ParamValue.text);
			map.put("ellipsize",ParamValue.ellipsize);
			map.put("fadingEdge", ParamValue.fadingEdge);
			map.put("scrollHorizontally", ParamValue.scrollHorizontally);
			map.put("textColor",ParamValue.textColor);
			map.put("textSize", ParamValue.textSize);
			map.put("visibility", ParamValue.visibility);
			map.put("textStyle", ParamValue.textStyle);
			map.put("style", ParamValue.style);
			map.put("src", ParamValue.src);			
			map.put("gravity", ParamValue.gravity);
			map.put("padding",ParamValue.padding);
			map.put("background",ParamValue.background);
			wkViewMap=new SoftReference<HashMap<String,ParamValue>>(map);
		}
		return wkViewMap.get();
	}
	/**
	 * 得到结果一律转化为px
	 * @param str
	 * @return
	 */
	public int getDimen(String str){
		if(str.startsWith("@dimen/")){
			if(wkDimenMap==null || wkDimenMap.get()==null){
				wkDimenMap=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readDimensXml(mContext));
			}
			str=str.substring(7);
			str=wkDimenMap.get().get(str);
		}
		return calculateRealSize(str);
	}

	/**
	 * 由字符串获取得颜色
	 * @param val 如"#ffffff或者@color/white"
	 * @return  颜色代表的十六进制数据
	 */
	public  int getIntColor(String val){
		if(val.startsWith("@color/")){
			if(wkColorMap==null || wkColorMap.get()==null){
				wkColorMap=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readColorsXml(mContext));
			}
			val=val.substring(7);
			val=wkColorMap.get().get(val);
		}
		if(!TextUtils.isEmpty(val)){
			if(val.startsWith("#")){
				int length = val.length();
				if(length==7){
					long j=Long.decode(val.replace("#", "#FF"));
					return (int) j;
				}else if(length==9){
					long j=Long.decode(val);
					return (int) j;
				}else{
					Logger.i("返回白色背景");
					return 0xFFffffff;
				}
			}
		}
		return 0xFF000000;
	}
	/**
	 * 
	 * @param s
	 * @return
	 */
	public int calculateTextSize(String s){
		int i=-2;
		try {
			i=Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("sp");
			int j=0;
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.sp2px(j, mContext);
			}
			Log.i(TAG, "计算后的值为"+j);
			return j;
		}
	}
	
	
	/**
	 * 计算真实大小，单位为px
	 * @param s
	 * @return 计算真实大小，单位为px
	 */
	public  int calculateRealSize(String s){
		int i=-2;
		try {
			i=Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("dp");
			int j=0;
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.dip2px(mContext,j);
			}
			tmp = s.indexOf("dip");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.dip2px(mContext,j);
			}
			tmp = s.indexOf("sp");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
				j=DensityUtil.sp2px(j, mContext);
			}
			tmp = s.indexOf("px");
			if(tmp!=-1){
				j=Integer.parseInt(s.substring(0, tmp));
			}
			Log.i(TAG, "计算后的值为"+j);
			return j;
		}
	}
	
	/**
	 * 设置输入类型
	 * @param 输入类型
	 * @return
	 */
	public  int getInputType(String inputType){
		Log.i("YDResource InputType", inputType);
		String [] s=inputType.toUpperCase().split("\\|");
		int sum=InputType.TYPE_CLASS_TEXT;
		try {
			Class clazz = Class.forName("android.text.InputType");
			for (int i = 0; i < s.length; i++) {
				Field f=clazz.getField(s[i]);
				sum|=f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public ScaleType getScaleType(String scaleType){
		if(scaleType.equals("center")){
			return ScaleType.CENTER;
		}else if(scaleType.equals("centerCrop")){
			return ScaleType.CENTER_CROP;
		}else if(scaleType.equals("centerInside")){
			return ScaleType.CENTER_INSIDE;
		}else if(scaleType.equals("fitCenter")){
			return ScaleType.FIT_CENTER;
		}else if(scaleType.equals("firEnd")){
			return ScaleType.FIT_END;
		}else if(scaleType.equals("firStart")){
			return ScaleType.FIT_START;
		}else if(scaleType.equals("firXY")){
			return ScaleType.FIT_XY;
		}else if(scaleType.equals("matrix")){
			return ScaleType.MATRIX;
		}
		return null;
	}
	
	/**
	 * 设置重心
	 * @param gravity
	 * @return
	 */
	public  int getGravity(String gravity){
		Log.i("YDResource gravity", gravity);
		String [] s=gravity.toUpperCase().split("\\|");
		int sum=Gravity.TOP;
		try {
			Class clazz = Class.forName("android.view.Gravity");
			for (int i = 0; i < s.length; i++) {
				Field f=clazz.getField(s[i]);
				sum|=f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	/**
	 * 获取到资源ID
	 * @param tid
	 * @return
	 */
	public  int getIdentifier(String tid){
		String packagename="com.example.testviewtree";
		StringBuilder sb=new StringBuilder();
		sb.append(packagename);
		sb.append(".R$");
		int rid=0;
		String[] classes=tid.split("\\.");
		System.out.println(classes.toString());
		sb.append(classes[1]);
		try {
			Class<?> innerClass=Class.forName(sb.toString());
			Object obj = innerClass.newInstance();
	        Field field = innerClass.getDeclaredField(classes[2]);
	        field.setAccessible(true);
	        rid=(Integer) field.get(obj);
	        Log.i(TAG, "id :"+rid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rid;
	}
	/**
	 * 从values中获取到strings
	 * @param s
	 * @return
	 */
	public  String getString(String s){
		if(!s.startsWith("@string/")){
			return s;
		}
		if(wkstrings==null || wkstrings.get()==null){
			Logger.i("字符串变空了");
			wkstrings=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readStringsXml(mContext));
		}
		s=s.substring(8);
		return wkstrings.get().get(s);
	}
	
	public void initValues(Context context){
		if(wkstrings==null || wkstrings.get()==null){
			wkstrings=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readStringsXml(context));
		}
		if(wkColorMap==null || wkColorMap.get()==null){
			wkColorMap=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readColorsXml(context));
		}
		if(wkDimenMap==null || wkDimenMap.get()==null){
			wkDimenMap=new SoftReference<HashMap<String,String>>(ReadXmlUtils.readDimensXml(context));
		}
		DrawableUtils.getDrawableMap(context);
	}
	
	public String getID(String s){
		if(s.startsWith("@+id/")){
			s=s.substring(5);
			return s;
		}else if(s.startsWith("@id/")){
			s=s.substring(4);
			return s;
		}
		return null;
	}
	
	public int getStringHashCode(String s){
		return s.hashCode();
	}
	
	
	/**
	 * 获取布局View树
	 * @param str 要解析的xml文件名
	 * @return 得到组成View树
	 * @throws Exception 
	 */
	public View getLayout(String str) {
		//第一次均为空
		initValues(mContext);
		YDLayoutInflate inflate=new YDLayoutInflate(mContext);
		StringBuilder sb=new StringBuilder();
		//File.separator即是/符号
		//获得要解析的xml文件绝对路径
		sb.append(rootpath).append(File.separator).append("layout").append(File.separator).append(str);
		if(Logger.debug){
		   Logger.i(sb.toString());
		}
		View view=inflate.inflate(sb.toString(), null);
		int i=0;
		return view;
	}
}
