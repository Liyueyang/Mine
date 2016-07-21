package cn.lyy.findyou.widget.navigationview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lyy.findyou.R;
import cn.lyy.findyou.app.MainApplication;

/**
 * 自定义导航栏视图。
 * <p/>
 * <pre class="prettyprint">
 * &#060;CustomNavigationView
 * xmlns:navigation="http://schemas.android.com/apk/res/最顶层包名"
 * android:layout_width="fill_parent"
 * android:layout_height="wrap_content"
 * hehui:custom_navigation_view_background="@color/ly_widget_custom_navigation_bg"
 * hehui:custom_navigation_view_default_text_color="@color/ly_widget_custom_navigation_text_color"
 * hehui:custom_navigation_view_left_iv="@drawable/ly_widget_selector_top_back_button_bg"
 * hehui:custom_navigation_view_left_btn_bg="@drawable/ly_biz_login_ui_logo"
 * hehui:custom_navigation_view_left_btn_text="@string/ly_biz_app_name"
 * hehui:custom_navigation_view_left_tv="@string/ly_biz_app_name"
 * hehui:custom_navigation_view_center_tv="@string/ly_biz_app_name"
 * hehui:custom_navigation_view_right_iv="@drawable/ly_widget_selector_top_back_button_bg"
 * hehui:custom_navigation_view_right_btn_bg="@drawable/ly_biz_login_ui_logo"
 * hehui:custom_navigation_view_right_btn_text="@string/ly_biz_app_name"
 * hehui:custom_navigation_view_right_tv="@string/ly_biz_app_name"
 * hehui:custom_navigation_view_need_custom_background_color="1"
 * hehui:custom_navigation_view_type="7"&#047;&#062;
 * </pre>
 *
 * @author hehui
 */
public class CustomNavigationView extends LinearLayout implements OnClickListener {

    public static enum NavigationType {

        /**
         * 未知类型
         */
        Unknown(-255),

        /**
         * IV | Center | Btn
         */
        First(1),

        /**
         * IV TV | Center | No
         */
        Second(2),

        /**
         * IV TV | Center | IV
         */
        Third(3),

        /**
         * NO | Center | Btn
         */
        Four(4),

        /**
         * IV | Center | NO
         */
        Five(5),

        /**
         * Btn | NO | Btn
         */
        Six(6),

        /**
         * IV TV | Center | IV TV
         */
        Seven(7),

        /**
         * NO | Center | NO
         */
        Eight(8),

        /**
         * IV | Center | IV
         */
        Nine(9),

        /**
         * Btn | Center | Btn
         */
        Ten(10);

        private int mVale;

        public int value() {
            return mVale;
        }

        private NavigationType(int value) {
            mVale = value;
        }

        /**
         * 将整型值转换为 {@link NavigationType} 类型。
         *
         * @param value
         * @return
         */
        public static NavigationType valueOf(int value) {
            switch (value) {
                case 1:
                    return First;
                case 2:
                    return Second;
                case 3:
                    return Third;
                case 4:
                    return Four;
                case 5:
                    return Five;
                case 6:
                    return Six;
                case 7:
                    return Seven;
                case 8:
                    return Eight;
                case 9:
                    return Nine;
                case 10:
                    return Ten;
                default:
                    return Unknown;
            }
        }
    }

    private LinearLayout mNavRootView;

    private ImageView mLeftIv;
    private Button mLeftButton;
    private TextView mLeftTv;
    private TextView mCenterTv;
    private ImageView mRightIv;
    private Button mRightButton;
    private TextView mRightTv;

    private int mLeftIvResId;
    private int mLeftButtonResId;
    private String mLeftButtonContent;
    private String mLeftTvContent;
    private String mCenterTvContent;
    private int mRightIvResId;
    private int mRightButtonResId;
    private String mRightButtonContent;
    private String mRightTvContent;

    private NavigationType mNavigationType;

    private OnNavigationLeftImageViewListener mOnNavigationLeftImageViewListener;
    private OnNavigationLeftTextViewListener mOnNavigationLeftTextViewListener;
    private OnNavigationLeftButtonListener mOnNavigationLeftButtonListener;
    private OnNavigationCenterTextViewListener mOnNavigationCenterTextViewListener;
    private OnNavigationRightImageViewListener mOnNavigationRightImageViewListener;
    private OnNavigationRightButtonListener mOnNavigationRightButtonListener;
    private OnNavigationRightTextViewListener mOnNavigationRightTextViewListener;

    public CustomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View navLayout = LayoutInflater.from(context).inflate(R.layout.custom_navigation_layout,
                (ViewGroup) null);
        addView(navLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        findViewById(navLayout);

        initTypedArray(context, attrs);

        initNavigation();

        addListeners();
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.custom_navigation_view_style);

        initNavigationBackgroundColor(context, array);

        int defaultTextColor = array.getColor(R.styleable.custom_navigation_view_style_custom_navigation_view_default_text_color, -1);
        if (defaultTextColor != -1) {
            mCenterTv.setTextColor(defaultTextColor);
            mLeftButton.setTextColor(defaultTextColor);
            mRightButton.setTextColor(defaultTextColor);
            mLeftTv.setTextColor(defaultTextColor);
            mRightTv.setTextColor(defaultTextColor);
        }

        mNavigationType = NavigationType.valueOf(Integer.parseInt(array
                .getString(R.styleable.custom_navigation_view_style_custom_navigation_view_type)));
        mLeftIvResId = array.getResourceId(R.styleable.custom_navigation_view_style_custom_navigation_view_left_iv, -1);
        mLeftButtonResId = array.getResourceId(R.styleable.custom_navigation_view_style_custom_navigation_view_left_btn_bg, -1);
        mLeftButtonContent = array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_left_btn_text);
        mLeftTvContent = array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_left_tv);
        mCenterTvContent = array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_center_tv);
        mRightIvResId = array.getResourceId(R.styleable.custom_navigation_view_style_custom_navigation_view_right_iv, -1);
        mRightButtonResId = array.getResourceId(R.styleable.custom_navigation_view_style_custom_navigation_view_right_btn_bg, -1);
        mRightButtonContent = array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_right_btn_text);
        mRightTvContent = array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_right_tv);
        array.recycle();
    }

    private void initNavigationBackgroundColor(Context context, TypedArray array) {
        if (array.hasValue(R.styleable.custom_navigation_view_style_custom_navigation_view_need_custom_background_color)
                && "1".equals(array.getString(R.styleable.custom_navigation_view_style_custom_navigation_view_need_custom_background_color))) {
            MainApplication app = (MainApplication) context.getApplicationContext();
        } else {
            int backgroundResId = array.getResourceId(R.styleable.custom_navigation_view_style_custom_navigation_view_background, -1);
            if (backgroundResId != -1) {
                mNavRootView.setBackgroundResource(backgroundResId);
            }
            int backgroundColorResId = array.getColor(R.styleable.custom_navigation_view_style_custom_navigation_view_background, -1);
            if (backgroundColorResId != -1) {
                mNavRootView.setBackgroundColor(backgroundColorResId);
            }
        }
    }

    private void findViewById(View navLayout) {
        mNavRootView = (LinearLayout) navLayout.findViewById(R.id.nav_root_view);

        mLeftIv = (ImageView) navLayout.findViewById(R.id.nav_left_iv);
        mLeftButton = (Button) navLayout.findViewById(R.id.nav_left_button);
        mLeftTv = (TextView) navLayout.findViewById(R.id.nav_left_tv);
        mCenterTv = (TextView) navLayout.findViewById(R.id.nav_center_title_tv);
        mRightIv = (ImageView) navLayout.findViewById(R.id.nav_right_iv);
        mRightButton = (Button) navLayout.findViewById(R.id.nav_right_button);
        mRightTv = (TextView) navLayout.findViewById(R.id.nav_right_tv);
    }

    private void initAllGone() {
        mLeftIv.setVisibility(View.GONE);
        mLeftButton.setVisibility(View.GONE);
        mLeftTv.setVisibility(View.GONE);
        mCenterTv.setVisibility(View.GONE);
        mRightIv.setVisibility(View.GONE);
        mRightButton.setVisibility(View.GONE);
        mRightTv.setVisibility(View.GONE);
    }

    private void initNavigation() {
        initAllGone();

        switch (mNavigationType) {
            case First: {
                mLeftIv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);
                mRightButton.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mCenterTv.setText(mCenterTvContent);
                if (mRightButtonResId != -1) {
                    mRightButton.setBackgroundResource(mRightButtonResId);
                }
                mRightButton.setText(mRightButtonContent);
                break;
            }
            case Second: {
                mLeftIv.setVisibility(View.VISIBLE);
                mLeftTv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mLeftTv.setText(mLeftTvContent);
                mCenterTv.setText(mCenterTvContent);
                break;
            }
            case Third: {
                mLeftIv.setVisibility(View.VISIBLE);
                mLeftTv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mLeftTv.setText(mLeftTvContent);
                mCenterTv.setText(mCenterTvContent);
                if (mRightIvResId != -1) {
                    mRightIv.setImageResource(mRightIvResId);
                }
                break;
            }
            case Four: {
                mCenterTv.setVisibility(View.VISIBLE);
                mRightButton.setVisibility(View.VISIBLE);

                mCenterTv.setText(mCenterTvContent);
                if (mRightButtonResId != -1) {
                    mRightButton.setBackgroundResource(mRightButtonResId);
                }
                mRightButton.setText(mRightButtonContent);
                break;
            }
            case Five: {
                mLeftIv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mCenterTv.setText(mCenterTvContent);
                break;
            }
            case Six: {
                mLeftButton.setVisibility(View.VISIBLE);
                mRightButton.setVisibility(View.VISIBLE);

                if (mLeftButtonResId != -1) {
                    mLeftButton.setBackgroundResource(mLeftButtonResId);
                }
                mLeftButton.setText(mLeftButtonContent);
                if (mRightButtonResId != -1) {
                    mRightButton.setBackgroundResource(mRightButtonResId);
                }
                mRightButton.setText(mRightButtonContent);
                break;
            }
            case Seven: {
                mLeftIv.setVisibility(View.VISIBLE);
                mLeftTv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.VISIBLE);
                mRightTv.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mLeftTv.setText(mLeftTvContent);
                mCenterTv.setText(mCenterTvContent);
                if (mRightIvResId != -1) {
                    mRightIv.setImageResource(mRightIvResId);
                }
                mRightTv.setText(mRightTvContent);
                break;
            }
            case Eight: {
                mCenterTv.setVisibility(View.VISIBLE);
                mCenterTv.setText(mCenterTvContent);
                break;
            }
            case Nine: {
                mLeftIv.setVisibility(View.VISIBLE);
                mCenterTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.VISIBLE);

                if (mLeftIvResId != -1) {
                    mLeftIv.setImageResource(mLeftIvResId);
                }
                mCenterTv.setText(mCenterTvContent);
                if (mRightIvResId != -1) {
                    mRightIv.setImageResource(mRightIvResId);
                }
                break;
            }
            case Ten: {
                mCenterTv.setVisibility(View.VISIBLE);
                mLeftButton.setVisibility(View.VISIBLE);
                mRightButton.setVisibility(View.VISIBLE);

                if (mLeftButtonResId != -1) {
                    mLeftButton.setBackgroundResource(mLeftButtonResId);
                }
                mLeftButton.setText(mLeftButtonContent);
                if (mRightButtonResId != -1) {
                    mRightButton.setBackgroundResource(mRightButtonResId);
                }
                mRightButton.setText(mRightButtonContent);
                mCenterTv.setText(mCenterTvContent);
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal type, type: " + mNavigationType.value());
            }
        }
    }

    private void addListeners() {
        switch (mNavigationType) {
            case First: {
                mLeftIv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                mRightButton.setOnClickListener(this);
                break;
            }
            case Second: {
                mLeftIv.setOnClickListener(this);
                mLeftTv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                break;
            }
            case Third: {
                mLeftIv.setOnClickListener(this);
                mLeftTv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                mRightIv.setOnClickListener(this);
                break;
            }
            case Four: {
                mLeftIv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                mRightButton.setOnClickListener(this);
                break;
            }
            case Five: {
                mLeftIv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                break;
            }
            case Six: {
                mLeftButton.setOnClickListener(this);
                mRightButton.setOnClickListener(this);
                break;
            }
            case Seven: {
                mLeftIv.setOnClickListener(this);
                mLeftTv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                mRightIv.setOnClickListener(this);
                mRightTv.setOnClickListener(this);
                break;
            }
            case Eight: {
                mCenterTv.setOnClickListener(this);
                break;
            }
            case Nine: {
                mLeftIv.setOnClickListener(this);
                mCenterTv.setOnClickListener(this);
                mRightIv.setOnClickListener(this);
                break;
            }
            case Ten: {
                mCenterTv.setOnClickListener(this);
                mLeftButton.setOnClickListener(this);
                mRightButton.setOnClickListener(this);
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal type, type: " + mNavigationType.value());
            }
        }
    }

    public void setNavBackgroundColor(int colorId) {
        mNavRootView.setBackgroundColor(colorId);
    }

    public void setCenterTextViewContent(String centerTvConent) {
        mCenterTvContent = centerTvConent;
        mCenterTv.setText(centerTvConent);
    }

    public void setCenterTextSize(int contentSize) {
        mCenterTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    public void setRightBtnVisibility(int visibility) {
        mRightButton.setVisibility(visibility);
    }

    public void setRightImageViewVisibility(int visibility) {
        mRightIv.setVisibility(visibility);
    }

    public void setRightBtnContent(String rightBtnContent) {
        mRightButtonContent = rightBtnContent;
        mRightButton.setText(rightBtnContent);
    }

    public void setLeftBtnContentSize(int contentSize) {
        mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    public void setRightBtnContentSize(int contentSize) {
        mRightButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    public void setLeftTextViewContentSize(int contentSize) {
        mLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    public void setRightTextViewContentSize(int contentSize) {
        mRightTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    public ImageView getRightImageView() {
        return mRightIv;
    }

    public Button getRightButton() {
        return mRightButton;
    }

    public void changeNavigationType(NavigationType navigationType) {
        mNavigationType = navigationType;
        initNavigation();
        addListeners();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.nav_left_iv) {
            if (mOnNavigationLeftImageViewListener != null) {
                mOnNavigationLeftImageViewListener.onLeftImageView(v);
            }
        } else if (viewId == R.id.nav_left_button) {
            if (mOnNavigationLeftButtonListener != null) {
                mOnNavigationLeftButtonListener.onLeftButton(v);
            }
        } else if (viewId == R.id.nav_left_tv) {
            if (mOnNavigationLeftTextViewListener != null) {
                mOnNavigationLeftTextViewListener.onLeftTextView(v);
            }
        } else if (viewId == R.id.nav_center_title_tv) {
            if (mOnNavigationCenterTextViewListener != null) {
                mOnNavigationCenterTextViewListener.onCenterTextView(v);
            }
        } else if (viewId == R.id.nav_right_iv) {
            if (mOnNavigationRightImageViewListener != null) {
                mOnNavigationRightImageViewListener.onRightImageView(v);
            }
        } else if (viewId == R.id.nav_right_button) {
            if (mOnNavigationRightButtonListener != null) {
                mOnNavigationRightButtonListener.onRightButton(v);
            }
        } else if (viewId == R.id.nav_right_tv) {
            if (mOnNavigationRightTextViewListener != null) {
                mOnNavigationRightTextViewListener.onRightTextView(v);
            }
        }
    }

    public interface OnNavigationLeftImageViewListener {
        public void onLeftImageView(View v);
    }

    public interface OnNavigationLeftTextViewListener {
        public void onLeftTextView(View v);
    }

    public interface OnNavigationLeftButtonListener {
        public void onLeftButton(View v);
    }

    public interface OnNavigationCenterTextViewListener {
        public void onCenterTextView(View v);
    }

    public interface OnNavigationRightImageViewListener {
        public void onRightImageView(View v);
    }

    public interface OnNavigationRightButtonListener {
        public void onRightButton(View v);
    }

    public interface OnNavigationRightTextViewListener {
        public void onRightTextView(View v);
    }

    public void setOnNavigationLeftImageViewListener(OnNavigationLeftImageViewListener onNavigationLeftImageViewListener) {
        mOnNavigationLeftImageViewListener = onNavigationLeftImageViewListener;
    }

    public void setOnNavigationLeftTextViewListener(OnNavigationLeftTextViewListener onNavigationLeftTextViewListener) {
        mOnNavigationLeftTextViewListener = onNavigationLeftTextViewListener;
    }

    public void setOnNavigationLeftButtonListener(OnNavigationLeftButtonListener onNavigationLeftButtonListener) {
        mOnNavigationLeftButtonListener = onNavigationLeftButtonListener;
    }

    public void setOnNavigationCenterTextViewListener(
            OnNavigationCenterTextViewListener onNavigationCenterTextViewListener) {
        mOnNavigationCenterTextViewListener = onNavigationCenterTextViewListener;
    }

    public void setOnNavigationRightImageViewListener(
            OnNavigationRightImageViewListener onNavigationRightImageViewListener) {
        mOnNavigationRightImageViewListener = onNavigationRightImageViewListener;
    }

    public void setOnNavigationRightButtonListener(OnNavigationRightButtonListener onNavigationRightButtonListener) {
        mOnNavigationRightButtonListener = onNavigationRightButtonListener;
    }

    public void setOnNavigationRightTextViewListener(OnNavigationRightTextViewListener onNavigationRightTextViewListener) {
        mOnNavigationRightTextViewListener = onNavigationRightTextViewListener;
    }
}
