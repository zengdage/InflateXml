package com.example.view;

import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.inflatexml.R;
import com.example.view.utils.DensityUtil;

/**
 * @author Codefarmer@sina.com
 */
public abstract class YDRefreshView extends LinearLayout {
	private static final String TAG = "PullToRefreshView";
	// refresh states
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	// pull state
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;
	/**
	 * last y
	 */
	private int mLastMotionY;
	/**
	 * lock
	 */
	private boolean mLock;
	/**
	 * header view
	 */
	private android.widget.RelativeLayout mHeaderView;
	/**
	 * footer view
	 */
	private android.widget.RelativeLayout mFooterView;
	/**
	 * list or grid
	 */
	private AdapterView<?> mAdapterView;
	/**
	 * scrollview
	 */
	private ScrollView mScrollView;
	/**
	 * header view height
	 */
	private int mHeaderViewHeight;
	/**
	 * footer view height
	 */
	private int mFooterViewHeight;
	/**
	 * header view image
	 */
	private ImageView mHeaderImageView;
	/**
	 * footer view image
	 */
	private ImageView mFooterImageView;
	/**
	 * header tip text
	 */
	private TextView mHeaderTextView;
	/**
	 * footer tip text
	 */
	private TextView mFooterTextView;
	/**
	 * header refresh time
	 */
	private TextView mHeaderUpdateTextView;
	/**
	 * footer refresh time
	 */
	// private TextView mFooterUpdateTextView;
	/**
	 * header progress bar
	 */
	private ProgressBar mHeaderProgressBar;
	/**
	 * footer progress bar
	 */
	private ProgressBar mFooterProgressBar;
	/**
	 * layout inflater
	 */
//	private LayoutInflater mInflater;
	/**
	 * header view current state
	 */
	private int mHeaderState;
	/**
	 * footer view current state
	 */
	private int mFooterState;
	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */
	private int mPullState;
	/**
	 * 变为向下的箭头,改变箭头方向
	 */
	private RotateAnimation mFlipAnimation;
	/**
	 * 变为逆向的箭头,旋转
	 */
	private RotateAnimation mReverseFlipAnimation;
	/**
	 * footer refresh listener
	 */
	private OnFooterRefreshListener mOnFooterRefreshListener;
	/**
	 * footer refresh listener
	 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;
	/**
	 * last update time
	 */
//	private String mLastUpdateTime;
	/**
	 * 设置是否支持下拉刷新，默认不允许，即当前默认为普通的View
	 */
	private boolean isPullRefreshAllowed=false;
	public YDRefreshView(Context context, AttributeSet attrs) {
		super(context);
		
		init();
		addAdapterView(attrs);
		onFinishInflate();
	}

	public YDRefreshView(Context context) {
		super(context);
		
		init();
		addAdapterView(null);
		onFinishInflate();
	}
	public AdapterView getAdapterView(){
		return mAdapterView;
	}
	public ScrollView getScrollView(){
		return mScrollView;
	}
	/**
	 * 判断是否支持下拉刷新
	 * @return
	 */
	public boolean isPullRefreshAllowed(){
		return isPullRefreshAllowed;
	}
	/**
	 * 设置支持下拉刷新
	 */
	public void setPullRefreshAllowed(boolean bool){
		if(!bool){
			mHeaderView.setVisibility(View.GONE);
			mFooterView.setVisibility(View.GONE);
		}else{
			mHeaderView.setVisibility(View.VISIBLE);
			mFooterView.setVisibility(View.VISIBLE);
		}
		isPullRefreshAllowed=bool;
	}
	/**
	 * 要实现下拉刷新功能，此方法必须被子类重载
	 * @param attrs
	 */
	public void addAdapterView(AttributeSet attrs){}
	
	/**
	 * init
	 * 
	 * @description
	 * @param context
	 *            hylin 2012-7-26上午10:08:33
	 */
	private void init() {
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.setOrientation(VERTICAL);
		this.setBackgroundColor(0xffffffff);
		// Load all of the animations we need in code rather than through XML
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

//		mInflater = LayoutInflater.from(getContext());
		// header view 在此添加,保证是第一个添加到linearlayout的最上端
		addHeaderView();
		
		
		
	}

	private void addHeaderView() {
		// header view
		
		mHeaderView = new android.widget.RelativeLayout(getContext());
		LayoutParams hparams=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		hparams.gravity=Gravity.CENTER;
		mHeaderView.setLayoutParams(hparams);
		mHeaderView.setBackgroundColor(Color.WHITE);
		mHeaderView.setPadding(DensityUtil.px2dip(getContext(), 5), DensityUtil.px2dip(getContext(), 15), DensityUtil.px2dip(getContext(), 5), DensityUtil.px2dip(getContext(), 15));
		
		
		
//		mHeaderImageView = (ImageView) mHeaderView.findViewById(R.id.pull_to_refresh_image);
		mHeaderImageView=new ImageView(getContext());
		android.widget.RelativeLayout.LayoutParams iparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
//		iparams.addRule(Gravity.CENTER);
		
		iparams.leftMargin=DensityUtil.px2dip(getContext(), 30);
		iparams.rightMargin=DensityUtil.px2dip(getContext(), 20);
		
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView.setBackgroundResource(R.drawable.ic_pulltorefresh_arrow);
		mHeaderImageView.setLayoutParams(iparams);
		
		mHeaderView.addView(mHeaderImageView);
		
//		mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);
		android.widget.RelativeLayout.LayoutParams tparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
//		tparams.addRule(android.widget.RelativeLayout.LayoutParams,android.widget.RelativeLayout.TRUE);
		
		mHeaderTextView=new TextView(getContext());
		mHeaderTextView.setTextColor(Color.BLACK);
//		mHeaderTextView.setLayoutParams(tparams);
		mHeaderTextView.setText(R.string.pull_to_refresh_footer_pull_label);
		mHeaderTextView.setTextAppearance(getContext(), android.R.attr.textAppearance);
		mHeaderTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		mHeaderTextView.setId(1);
		mHeaderTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		
		mHeaderView.addView(mHeaderTextView,tparams);
		
		android.widget.RelativeLayout.LayoutParams ttparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT,DensityUtil.px2dip(getContext(), 30));
//		mHeaderUpdateTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
		mHeaderUpdateTextView=new TextView(getContext());
//		ttparams.addRule(Gravity.CENTER);
		ttparams.addRule(android.widget.RelativeLayout.ALIGN_BOTTOM, mHeaderTextView.getId());
//		mHeaderUpdateTextView.setLayoutParams(ttparams);
		mHeaderUpdateTextView.setText("更新于："+new Date().toLocaleString());
		mHeaderUpdateTextView.setTextAppearance(getContext(), android.R.attr.textAppearance);
		mHeaderUpdateTextView.setVisibility(View.GONE);
		mHeaderUpdateTextView.setGravity(Gravity.CENTER_HORIZONTAL);
	
		mHeaderView.addView(mHeaderUpdateTextView,ttparams);
		
		android.widget.RelativeLayout.LayoutParams pparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
//		mHeaderProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
		
		pparams.leftMargin=DensityUtil.px2dip(getContext(), 30);
		pparams.rightMargin=DensityUtil.px2dip(getContext(), 20);
		pparams.topMargin=DensityUtil.px2dip(getContext(), 10);
		
		mHeaderProgressBar=new ProgressBar(getContext());
		mHeaderProgressBar.setVisibility(View.GONE);
		mHeaderProgressBar.setIndeterminate(true);
		mHeaderProgressBar.setLayoutParams(pparams);
		mHeaderProgressBar.setScrollBarStyle(android.R.attr.progressBarStyleSmall);
		
		mHeaderView.addView(mHeaderProgressBar);
		mHeaderView.requestLayout();
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		
		mHeaderView.setVisibility(View.GONE);
		
		addView(mHeaderView, params);
	}

	private void addFooterView() {
		// footer view
//		mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
		mFooterView = new YDRelativeLayout(getContext());
		LayoutParams hparams=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		hparams.gravity=Gravity.CENTER;
		mFooterView.setLayoutParams(hparams);
		mFooterView.setBackgroundColor(Color.WHITE);
		mFooterView.setPadding(DensityUtil.px2dip(getContext(), 5), DensityUtil.px2dip(getContext(), 10), DensityUtil.px2dip(getContext(), 5), DensityUtil.px2dip(getContext(), 10));
		
		
		
//		mFooterImageView = (ImageView) mFooterView.findViewById(R.id.pull_to_load_image);
		mFooterImageView=new ImageView(getContext());
		android.widget.RelativeLayout.LayoutParams iparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		iparams.leftMargin=DensityUtil.px2dip(getContext(), 30);
		iparams.rightMargin=DensityUtil.px2dip(getContext(), 20);
		
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView.setBackgroundResource(R.drawable.ic_pulltorefresh_arrow_up);
		mFooterImageView.setLayoutParams(iparams);
		
		mFooterView.addView(mFooterImageView);
		
		mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_load_text);
		android.widget.RelativeLayout.LayoutParams tparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		tparams.addRule(android.widget.RelativeLayout.CENTER_IN_PARENT, android.widget.RelativeLayout.TRUE);
		mFooterTextView=new TextView(getContext());
		mFooterTextView.setTextColor(Color.BLACK);
		mFooterTextView.setLayoutParams(tparams);
		mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
		mFooterTextView.setTextAppearance(getContext(), android.R.attr.textAppearanceMedium);
		mFooterTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		mFooterTextView.setId(0x153);
		mFooterTextView.setGravity(Gravity.CENTER);
		mFooterView.addView(mFooterTextView);
		
		mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_load_progress);
		android.widget.RelativeLayout.LayoutParams pparams=new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
//		mHeaderProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
		
		pparams.leftMargin=DensityUtil.px2dip(getContext(), 30);
		pparams.rightMargin=DensityUtil.px2dip(getContext(), 20);
		pparams.topMargin=DensityUtil.px2dip(getContext(), 10);
		
		mFooterProgressBar=new ProgressBar(getContext());
		mFooterProgressBar.setVisibility(View.GONE);
		mFooterProgressBar.setIndeterminate(true);
		mFooterProgressBar.setLayoutParams(pparams);
		mFooterProgressBar.setScrollBarStyle(android.R.attr.progressBarStyle);
		
		mFooterView.addView(mFooterProgressBar);
		
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
		
		mFooterView.setVisibility(View.GONE);
		// int top = getHeight();
		// params.topMargin
		// =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
		// getHeight()什么时候会赋值,稍候再研究一下
		// 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
		addView(mFooterView, params);
	}

	
	public void onFinishInflate() {
//		super.onFinishInflate();
		// footer view 在此添加保证添加到linearlayout中的最后
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView,GridView and so on;or init ScrollView
	 * 
	 * @description hylin 2012-7-30下午8:48:12
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			view.setVisibility(View.VISIBLE);
			Log.i(TAG, "find View:"+view.toString() + "==="+view.isShown());
			if (view instanceof AdapterView<?>) {
				Log.i(TAG, "find the adapterView");
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 是向下运动,< 0是向上运动
			int deltaY = y - mLastMotionY;
			
			if (isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	/*
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mLock) {
			return true;
		}
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent已经记录
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				// PullToRefreshView执行下拉
				Log.i(TAG, " pull down!parent view move!");
				headerPrepareToRefresh(deltaY);
				// setHeaderPadding(-mHeaderViewHeight);
			} else if (mPullState == PULL_UP_STATE) {
				// PullToRefreshView执行上拉
				Log.i(TAG, "pull up!parent view move!");
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// 开始刷新
					headerRefreshing();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					// 开始执行footer 刷新
					footerRefreshing();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 是否应该到了父View,即PullToRefreshView滑动
	 * 
	 * @param deltaY
	 *            , deltaY > 0 是向下运动,< 0是向上运动
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY) {
		if (!isPullRefreshAllowed||mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		//对于ListView和GridView
//		Log.i(TAG, " adapterview"+(mAdapterView != null));
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {

				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
//					Log.i(TAG, "do not have data in adapterview");
					return false;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
//					Log.i(TAG, "pull down ");
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8) {//这里之前用3可以判断,但现在不行,还没找到原因
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 对于ScrollView
		if (mScrollView != null) {
			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 准备刷新,手指移动过程,还没有释放
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			// mHeaderImageView.
			mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
			mHeaderState = PULL_TO_REFRESH;
		}
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			mFooterTextView
					.setText(R.string.pull_to_refresh_footer_release_label);
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = RELEASE_TO_REFRESH;
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
			mFooterState = PULL_TO_REFRESH;
		}
	}

	/**
	 * 修改Header view top margin的值
	 * 
	 * @description
	 * @param deltaY
	 * @return hylin 2012-7-31下午1:14:31
	 */
	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		//这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
		//表示如果是在上拉后一段距离,然后直接下拉
		if(deltaY>0&&mPullState == PULL_UP_STATE&&Math.abs(params.topMargin) <= mHeaderViewHeight){
			return params.topMargin;
		}
		//同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if(deltaY<0&&mPullState == PULL_DOWN_STATE&&Math.abs(params.topMargin)>=mHeaderViewHeight){
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	/**
	 * header refreshing
	 * 
	 * @description hylin 2012-7-31上午9:10:12
	 */
	private void headerRefreshing() {
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * footer refreshing
	 * 
	 * @description hylin 2012-7-31上午9:09:59
	 */
	private void footerRefreshing() {
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		mFooterImageView.setVisibility(View.GONE);
		mFooterImageView.clearAnimation();
		mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);
		mFooterTextView
				.setText(R.string.pull_to_refresh_footer_refreshing_label);
		if (mOnFooterRefreshListener != null) {
			mOnFooterRefreshListener.onFooterRefresh(this);
		}
	}

	/**
	 * 设置header view 的topMargin的值
	 * 
	 * @description
	 * @param topMargin
	 *            ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
	 *            hylin 2012-7-31上午11:24:06
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	/**
	 * header view 完成更新后恢复初始状态
	 * 
	 * @description hylin 2012-7-31上午11:54:23
	 */
	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
		mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
		mHeaderProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mHeaderState = PULL_TO_REFRESH;
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	/**
	 * footer view 完成更新后恢复初始状态
	 */
	public void onFooterRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
		mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
		mFooterProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mFooterState = PULL_TO_REFRESH;
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderUpdateTextView.setText(lastUpdated);
		} else {
			mHeaderUpdateTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取当前header view 的topMargin
	 * 
	 * @description
	 * @return hylin 2012-7-31上午11:22:50
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

//	/**
//	 * lock
//	 * 
//	 * @description hylin 2012-7-27下午6:52:25
//	 */
//	private void lock() {
//		mLock = true;
//	}
//
//	/**
//	 * unlock
//	 * 
//	 * @description hylin 2012-7-27下午6:53:18
//	 */
//	private void unlock() {
//		mLock = false;
//	}

	/**
	 * set headerRefreshListener
	 * 
	 * @description
	 * @param headerRefreshListener
	 *            hylin 2012-7-31上午11:43:58
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		mOnFooterRefreshListener = footerRefreshListener;
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 */
	public interface OnFooterRefreshListener {
		public void onFooterRefresh(YDRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(YDRefreshView view);
	}
}
