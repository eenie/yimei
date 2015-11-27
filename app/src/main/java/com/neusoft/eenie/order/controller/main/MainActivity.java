package com.neusoft.eenie.order.controller.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.konifar.fab_transformation.FabTransformation;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.base.BaseV4Fragment;
import com.neusoft.eenie.order.controller.login.LoginActivity;
import com.neusoft.eenie.order.controller.main.fragments.IndexFragment;
import com.neusoft.eenie.order.controller.main.fragments.MeFragment;
import com.neusoft.eenie.order.controller.main.fragments.OderFragment;
import com.neusoft.eenie.order.utiles.OrderManager;
import com.neusoft.eenie.order.utiles.mUtiles;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    List<BaseV4Fragment> fragments;

    RelativeLayout fragmentRoot;

    ViewPager rootPager;

    RootPagerAdapter rootPagerAdapter;

    FloatingActionButton fabCar;


    RadioButton btnBottomIndex, btnBottomMe, btnBottomOrder;

    RadioGroup bottomBtnGroup;

    FragmentManager fragmentManager;
    int currentFrament = 0;

    View overRay;
    View sheet;

    ListView carFoodList;

    CarListAdapter carListAdapter;


    OrderManager orderManager;

    int totalPrice = 0;

    TextView textTotalPrice;
    Button btnGoPay;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initfindView();


        //初始化frament
        initFragment();


        //初始化对象
        initOBJ();


        //初始化接口
        initInterface();


        //替换actionBar
        setSupportActionBar(toolbar);

        toolbar.setTitle("首页");


        //设置点击监听
        btnBottomIndex.setOnClickListener(this);
        btnBottomMe.setOnClickListener(this);
        btnBottomOrder.setOnClickListener(this);
        fabCar.setOnClickListener(this);
        btnGoPay.setOnClickListener(this);


        carFoodList.setAdapter(carListAdapter);
//        View emptyView = getLayoutInflater().inflate(R.layout.car_empty_layout, null);
//        carFoodList.setEmptyView(emptyView);

//        View emptyView = findViewById(R.id.empty);
//
//        carFoodList.setEmptyView(emptyView);


        //设置ViewPager参数
        rootPager.setOffscreenPageLimit(3);
        rootPager.setAdapter(rootPagerAdapter);
        rootPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                Log.e("onPageSelected", "" + position);


                ((RadioButton) bottomBtnGroup.getChildAt(position)).setChecked(true);
                toolbar.setTitle(((RadioButton) bottomBtnGroup.getChildAt(position)).getText());
                currentFrament = position;

                if (position != 0) {
                    if (fabCar.getVisibility() == View.VISIBLE) {
                        fabCar.setVisibility(View.GONE);
                        hideFab();
                    }
                } else {
                    fabCar.setVisibility(View.VISIBLE);
                    showFab();
                }


//                if (position == 2) {
//                    toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.takeout_ic_avatar_logged_out));
//
//                } else {
//                    toolbar.setNavigationIcon(null);
//                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initInterface() {


    }

    private void initOBJ() {

        fragmentManager = getSupportFragmentManager();
        rootPagerAdapter = new RootPagerAdapter(fragmentManager);
        carListAdapter = new CarListAdapter();
        orderManager = new OrderManager();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        BaseV4Fragment indexFragment = new IndexFragment();
        BaseV4Fragment oderFragment = new OderFragment();
        BaseV4Fragment meFragment = new MeFragment();
        fragments.add(indexFragment);
        fragments.add(oderFragment);
        fragments.add(meFragment);
    }

    private void initfindView() {
        toolbar = (Toolbar) findViewById(R.id.menuToobar);
        btnBottomIndex = (RadioButton) findViewById(R.id.btnBottomIndex);
        btnBottomOrder = (RadioButton) findViewById(R.id.btnBottomOrder);
        btnBottomMe = (RadioButton) findViewById(R.id.btnBottomMe);
        fragmentRoot = (RelativeLayout) findViewById(R.id.fragmentRoot);
        rootPager = (ViewPager) findViewById(R.id.rootPager);
        bottomBtnGroup = (RadioGroup) findViewById(R.id.bottomGroup);
        fabCar = (FloatingActionButton) findViewById(R.id.fabCar);
        overRay = findViewById(R.id.overRay);
        sheet = findViewById(R.id.sheet);
        carFoodList = (ListView) findViewById(R.id.carFoodList);
        textTotalPrice = (TextView) findViewById(R.id.textTotalPrice);
        btnGoPay = (Button) findViewById(R.id.btnGoPay);
    }


    private class CarListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return BaseApplication.orderBeanListData.size();
        }

        @Override
        public Object getItem(int position) {
            return BaseApplication.orderBeanListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class Holder {

            TextView textName;
            TextView textPrice;
            TextView textSub;
            TextView textAdd;
            TextView textCount;
            TextView textColor;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Holder holder;

            if (convertView == null) {

                holder = new Holder();
                convertView = getLayoutInflater().inflate(R.layout.item_car_list, null);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                holder.textPrice = (TextView) convertView.findViewById(R.id.textPrice);
                holder.textSub = (TextView) convertView.findViewById(R.id.textSub);
                holder.textCount = (TextView) convertView.findViewById(R.id.textCount);
                holder.textAdd = (TextView) convertView.findViewById(R.id.textAdd);
                holder.textColor = (TextView) convertView.findViewById(R.id.textColor);
                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }


            holder.textName.setText(BaseApplication.orderBeanListData.get(position).getFoodName());
            holder.textPrice.setText("￥" + String.valueOf(BaseApplication.orderBeanListData.get(position).getFoodPrice()) + ".00");
            holder.textCount.setText(String.valueOf(BaseApplication.orderBeanListData.get(position).getCount()));

            if (position % 2 == 0) {
                holder.textColor.setBackgroundColor(Color.RED);
            } else {
                holder.textColor.setBackgroundColor(Color.parseColor("#32BB7F"));
            }



            holder.textAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            holder.textSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuTest:
                break;
        }

        return true;
    }

    public void changeFragment(int position) {


        if (position != currentFrament) {

            currentFrament = position;
            rootPager.setCurrentItem(currentFrament, false);
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBottomIndex:
                changeFragment(0);
                break;
            case R.id.btnBottomOrder:
                changeFragment(1);
                break;
            case R.id.btnGoPay:

                if (BaseApplication.bombAPI.getCurrentUser() != null) {

                    orderManager.commitOrderBean(MainActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
//                       Log.e("addOrder", "onSuccess");
                            mUtiles.dismissProgressDialog();
                            cancelFab(null);
                            orderManager.clearOrderBean(BaseApplication.orderBeanListData);
                            carListAdapter.notifyDataSetChanged();
                            showDialog();
                        }

                        @Override
                        public void onFailure(int i, String s) {
//                       Log.e("addOrder", "onFailure");
                            mUtiles.dismissProgressDialog();
                        }
                    });
                } else {

                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            case R.id.btnBottomMe:
                changeFragment(2);
                break;
            case R.id.fabCar:
                carListAdapter.notifyDataSetChanged();
                totalPrice = sumPrice(BaseApplication.orderBeanListData);
                textTotalPrice.setText("合计:￥" + String.valueOf(totalPrice) + ".00元");
                FabTransformation.with(fabCar)
                        .duration(500)
                        .setOverlay(overRay)
                        .transformTo(sheet);
                break;
        }
    }


    private class RootPagerAdapter extends FragmentPagerAdapter {

        public RootPagerAdapter(FragmentManager fm) {
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


    private void hideFab() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_out);
        fabCar.startAnimation(animation);
    }

    private void showFab() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_in);
        fabCar.startAnimation(animation);
    }


    public void cancelFab(View view) {
        if (fabCar.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fabCar)
                    .setOverlay(overRay)
                    .transformFrom(sheet);
        }
    }


    @Override
    public void onBackPressed() {
        if (fabCar.getVisibility() != View.VISIBLE && currentFrament == 0) {
            FabTransformation.with(fabCar)
                    .setOverlay(overRay)
                    .transformFrom(sheet);
            return;
        }
        super.onBackPressed();
    }


    public int sumPrice(List<FoodBean> mfoodBeanList) {
        int sum = 0;
        for (FoodBean f : mfoodBeanList) {
            sum = sum + f.getFoodPrice() * f.getCount();
        }
        return sum;
    }




    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("下单成功！");
        builder.setItems(new String[]{"查看订单", "立即付款"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        rootPager.setCurrentItem(1);
                        break;
                    case 1:


                        break;
                }
            }
        });
        builder.create().show();
    }




}
