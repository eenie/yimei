package com.neusoft.eenie.order.controller.main;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.OrderBean;
import com.neusoft.eenie.order.R;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends AppCompatActivity {


    OrderBean orderBean;
    ListView foodListInfo;
    Button btnPay;
    Toolbar menuToobar;
    FoodListInfoAdapter foodListInfoAdapter;

    List<FoodBean> foodBeanList;

    TextView textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        orderBean = (OrderBean) getIntent().getSerializableExtra("orderBean");

        foodListInfo = (ListView) findViewById(R.id.foodListInfo);
        menuToobar = (Toolbar) findViewById(R.id.menuToobar);
        btnPay = (Button) findViewById(R.id.btnPay);
        textTotal = (TextView) findViewById(R.id.textTotal);
        initToolbar();

        if (orderBean != null) {
            foodBeanList = orderBean.getFoodBeanList();
            textTotal.setText("￥" + String.valueOf(orderBean.getTotalPrice()) + ".00");

        } else {
            foodBeanList = new ArrayList<>();
        }

        foodListInfoAdapter = new FoodListInfoAdapter();
        foodListInfo.setAdapter(foodListInfoAdapter);

    }


    private void initToolbar() {
        menuToobar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        menuToobar.setTitle("订单详情");
        setSupportActionBar(menuToobar);
    }

    private class FoodListInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return foodBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class Holder {
            TextView textName;
            TextView textCount;
            TextView textPrice;
            TextView textPoit;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if (convertView == null) {
                 holder= new Holder();
                convertView = getLayoutInflater().inflate(R.layout.item_food_info, null);
                holder.textName = (TextView) convertView.findViewById(R.id.textName);
                holder.textPrice = (TextView) convertView.findViewById(R.id.textPrice);
                holder.textPoit = (TextView) convertView.findViewById(R.id.poit);
                holder.textCount = (TextView) convertView.findViewById(R.id.textCount);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }


            holder.textName.setText(foodBeanList.get(position).getFoodName());
            holder.textCount.setText("x " +String.valueOf(foodBeanList.get(position).getCount()));
            holder.textPrice.setText("￥" + String.valueOf(foodBeanList.get(position).getFoodPrice()));

            if (position % 2 == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.textPoit.setBackground(getResources().getDrawable(R.drawable.shape_poite));
                }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.textPoit.setBackground(getResources().getDrawable(R.drawable.shape_poite_green));
                }
            }

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
