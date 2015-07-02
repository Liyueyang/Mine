package cn.liyueyang.liyy.slidetab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.liyueyang.liyy.R;
import cn.liyueyang.liyy.base.BaseFragmentActivity;

public class SlideTabActivity extends BaseFragmentActivity {
    private RadioButton homeFollow, homeRecommend, homeLocation;
    private ViewPager vPager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private MyFragmentAdapter adapter;
    private final int[] array = new int[]{R.id.home_follow, R.id.home_recommend, R.id.home_location};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_tab);

        FollowFragment topFragment = new FollowFragment();
        RecommendFragment hotFragment = new RecommendFragment();
        LocationFragment locationFragment = new LocationFragment();
        list.add(topFragment);
        list.add(hotFragment);
        list.add(locationFragment);

        vPager = (ViewPager) findViewById(R.id.viewpager_home);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
        vPager.setAdapter(adapter);
        vPager.setOffscreenPageLimit(2);
        vPager.setCurrentItem(1);
        vPager.setOnPageChangeListener(pageChangeListener);

        homeFollow = (RadioButton) findViewById(R.id.home_follow);
        homeRecommend = (RadioButton) findViewById(R.id.home_recommend);
        homeLocation = (RadioButton) findViewById(R.id.home_location);

        RadioGroup group = (RadioGroup) findViewById(R.id.home_page_select);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //设置了ViewPager的当前item就会触发ViewPager的SimpleOnPageChangeListener监听函数
                switch (checkedId) {
                    case R.id.home_follow:
                        vPager.setCurrentItem(0);
                        break;
                    case R.id.home_recommend:
                        vPager.setCurrentItem(1);
                        break;
                    case R.id.home_location:
                        vPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            change(array[position]);
        }
    };

    /**
     * 改变背景颜色，背景图片
     */
    private void change(int checkedId) {
        //改变背景图片
        homeFollow.setBackgroundResource(R.drawable.icon_top_normal);
        homeRecommend.setBackgroundResource(R.drawable.icon_recommend_normal);
        homeLocation.setBackgroundResource(R.drawable.icon_location_normal);

        //改变字体颜色
        homeFollow.setTextColor(getResources().getColor(R.color.white_normal));
        homeRecommend.setTextColor(getResources().getColor(R.color.white_normal));
        homeLocation.setTextColor(getResources().getColor(R.color.white_normal));

        switch (checkedId) {
            case R.id.home_follow:
                homeFollow.setBackgroundResource(R.drawable.icon_top_select);
                homeFollow.setTextColor(getResources().getColor(R.color.balck_normal));
                homeFollow.setChecked(true);
                break;
            case R.id.home_recommend:
                homeRecommend.setBackgroundResource(R.drawable.icon_recommend_select);
                homeRecommend.setTextColor(getResources().getColor(R.color.balck_normal));
                homeRecommend.setChecked(true);
                break;
            case R.id.home_location:
                homeLocation.setBackgroundResource(R.drawable.icon_location_select);
                homeLocation.setTextColor(getResources().getColor(R.color.balck_normal));
                homeLocation.setChecked(true);
                break;
        }
    }
}
