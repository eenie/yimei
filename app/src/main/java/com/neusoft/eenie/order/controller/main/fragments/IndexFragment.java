package com.neusoft.eenie.order.controller.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodCategoryBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.base.BaseV4Fragment;
import com.neusoft.eenie.order.controller.main.FoodInfoActivity;
import com.neusoft.eenie.order.utiles.OrderManager;
import com.neusoft.eenie.order.utiles.mUtiles;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class IndexFragment extends BaseV4Fragment implements View.OnClickListener {


    ViewPager pageBinner;

    RecyclerView recyFoodList;

    Context context;


    BombAPI bombAPI;

    SaveListener saveListener;


    List<ImageView> ads;


    BinnerAdapter binnerAdapter;


    Spinner spinnerLimit;
    Spinner spinnerChoosed;
    RecyFoodListAdapter recyFoodListAdapter;
    List<FoodBean> foodBeanList;
    List<String> foodCate;
    List<FoodCategoryBean> foodCategoryBeanList;
    OrderManager orderManager;


    public IndexFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        bombAPI = new BombAPI(context);
        orderManager = new OrderManager();


        View contentView = inflater.inflate(R.layout.fragment_index, container, false);
        findView(contentView);

        initInterface();

        initOBJ();


        //获取菜品类型信息
        mUtiles.showDialog(getContext(),"正在加载菜品..." ).show();
        bombAPI.getFoodCategory(0, new FindListener<FoodCategoryBean>() {
            @Override
            public void onSuccess(List<FoodCategoryBean> list) {
                foodCategoryBeanList = list;
                FoodCategoryBean foodCategoryBean = new FoodCategoryBean();
                foodCategoryBean.setCategoryName("");
                foodCate = new ArrayList<String>();
                for (FoodCategoryBean fcb : list) {
                    foodCate.add(fcb.getCategoryName());
                }
                foodCategoryBeanList.add(0, foodCategoryBean);
                foodCate.add(0, "全部");
                spinnerChoosed.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_layout, foodCate));
            }

            @Override
            public void onError(int code, String reason) {

            }
        });


        //获取食物信息
//        bombAPI.queryAllFood(0, new FindListener<FoodBean>() {
//            @Override
//            public void onSuccess(List<FoodBean> list) {
//                foodBeanList = list;
//                recyFoodListAdapter.notifyDataSetChanged();
//                System.out.println(list.size());
//            }
//
//            @Override
//            public void onError(int code, String reason) {
//
//            }
//        });

        spinnerLimit.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_layout, new String[]{"默认排行", "销量排行", "分享排行", "点赞排行", "评分排行"}));
        recyFoodList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyFoodList.setAdapter(recyFoodListAdapter);
        recyFoodList.setItemAnimator(new DefaultItemAnimator());


        spinnerChoosed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {



                bombAPI.queryFoodByCategory(foodCategoryBeanList.get(position), 0, new FindListener<FoodBean>() {
                    @Override
                    public void onSuccess(List<FoodBean> list) {
                        foodBeanList = list;
                        recyFoodListAdapter.notifyDataSetChanged();
                        mUtiles.dismissProgressDialog();

                    }

                    @Override
                    public void onError(int code, String reason) {
                        mUtiles.dismissProgressDialog();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mUtiles.dismissProgressDialog();
            }
        });


        return contentView;
    }

    private void initOBJ() {
        ads = new ArrayList<>();
        binnerAdapter = new BinnerAdapter();
        recyFoodListAdapter = new RecyFoodListAdapter();
        foodBeanList = new ArrayList<>();
        foodCate = new ArrayList<>();
        foodCategoryBeanList = new ArrayList<>();
    }

    private void findView(View rootView) {


        pageBinner = (ViewPager) rootView.findViewById(R.id.pageBinner);
        recyFoodList = (RecyclerView) rootView.findViewById(R.id.recyFoodList);
        spinnerLimit = (Spinner) rootView.findViewById(R.id.spinnerLimit);
        spinnerChoosed = (Spinner) rootView.findViewById(R.id.spinnerChoosed);


    }

    private void initInterface() {
        saveListener = new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("SaveListener", "onSuccess");
            }

            @Override
            public void onFailure(int code, String reason) {

                Log.e("onFailure reason", reason);
            }
        };

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

        }
    }


    private class RecyFoodListAdapter extends RecyclerView.Adapter {

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView btnSub;
            TextView btnPlus;
            TextView textCount;
            TextView textFoodName;
            ImageView foodPic;
            TextView textFoodPrice;



            public ViewHolder(View itemView) {

                super(itemView);
                btnSub = (TextView) itemView.findViewById(R.id.btnSub);
                btnPlus = (TextView) itemView.findViewById(R.id.btnPlus);
                textFoodName = (TextView) itemView.findViewById(R.id.textFoodName);
                textFoodPrice = (TextView) itemView.findViewById(R.id.textFoodPrice);
                foodPic = (ImageView) itemView.findViewById(R.id.foodPic);
                textCount = (TextView) itemView.findViewById(R.id.textCount);
                btnPlus.setOnClickListener(this);
                btnSub.setOnClickListener(this);
                foodPic.setOnClickListener(this);


//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(), getAdapterPosition() + "", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnPlus:
                        textCount.setText(String.valueOf(orderManager.addOrderBean(BaseApplication.orderBeanListData, foodBeanList.get(getPosition()))));
                        break;
                    case R.id.btnSub:
                        textCount.setText(String.valueOf(orderManager.subOrderBean(BaseApplication.orderBeanListData, foodBeanList.get(getPosition()))));
                        break;
                    case R.id.foodPic:
                        Intent intent = new Intent(getContext(), FoodInfoActivity.class);
                        intent.putExtra("foodBean", foodBeanList.get(getPosition()));
//                        startActivity(intent);

//                        ActivityOptionsCompatICS options = ActivityOptionsCompatICS.
//                                makeSceneTransitionAnimation(getActivity(), view, view.getId());
//
//                        ActivityCompatICS.startActivity(getActivity(), intent, options.toBundle());

//                        ((ImageView) view).setDrawingCacheEnabled(true);
//                        Bitmap bitmap = ((ImageView) view).getDrawingCache();
//                        ((ImageView) view).setDrawingCacheEnabled(false);
//                        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap, 0, 0);

                        ActivityOptionsCompat options =ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);

                        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());


                        break;

                }
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_food_list, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            //设置内容
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.textFoodName.setText(foodBeanList.get(position).getFoodName());
            viewHolder.textFoodPrice.setText("价格:￥" + String.valueOf(foodBeanList.get(position).getFoodPrice()) + ".00");

            if (BaseApplication.orderBeanListData.contains(foodBeanList.get(position))) {
                viewHolder.textCount.setText(
                        String.valueOf(
                                BaseApplication.orderBeanListData.get(BaseApplication.orderBeanListData.lastIndexOf(foodBeanList.get(position))).getCount()));
            } else {
                viewHolder.textCount.setText("0");
            }

            x.image().bind(viewHolder.foodPic, foodBeanList.get(position).getFoodPicture().getFileUrl(getContext()));

//            System.out.println(foodBeanList.get(position).getFoodPicture().getFileUrl(getContext()));

//            foodBeanList.get(position).getFoodPicture().loadImage(getContext(), viewHolder.foodPic);

        }

        @Override
        public int getItemCount() {
            return foodBeanList.size();
        }

    }


    private class BinnerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            view = ads.get(0);

            //判断是否为第一次加载
            if (view.getParent() != null) {


            } else {

            }
            return true;
        }


    }


}
