package com.neusoft.eenie.order.controller.main.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.main.fragments.introducevp.IntroduceBaseFragment;
import com.neusoft.eenie.order.controller.main.fragments.introducevp.IntroduceOneFragment;
import com.neusoft.eenie.order.controller.main.fragments.introducevp.IntroduceThreeFragment;
import com.neusoft.eenie.order.controller.main.fragments.introducevp.IntroduceTwoFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 引导界面
 */
public class IntroduceActivity extends AppCompatActivity {


    //引导页面的载体
    ViewPager introducevp;
    FragmentManager fragmentManager;

//    Fragment的适配器
    IntroduceViewPageAdapter introduceViewPageAdapter;

    //    Fragment集合
    List<IntroduceBaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);


        initFragment();
        fragmentManager = getSupportFragmentManager();
        introduceViewPageAdapter = new IntroduceViewPageAdapter(fragmentManager);
        introducevp = (ViewPager) findViewById(R.id.introducevp);
        introducevp.setAdapter(introduceViewPageAdapter);
        introducevp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //当切换完毕时执行动画
                fragments.get(position).showAnimation();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new IntroduceOneFragment());
        fragments.add(new IntroduceTwoFragment());
        fragments.add(new IntroduceThreeFragment());
    }

    class IntroduceViewPageAdapter extends FragmentPagerAdapter{


        public IntroduceViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }




}
