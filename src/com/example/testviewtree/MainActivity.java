package com.example.testviewtree;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.example.view.YDTextView;
import com.example.view.YDView;
import com.example.view.engine.YDResource;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			YDResource r=YDResource.getInstance();
			r.initResourcePath(this, "");
			//要求添加文件后缀
			  View v = r.getLayout("activity_main.xml");
			  setContentView(v);
			  YDView ydView=new YDView(this,v);
			  YDTextView text=(YDTextView)ydView.findViewByID("textFirst");
			  text.setText("北京北京北京");
			  
			//v.setBackgroundColor(Color.WHITE);   						  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
