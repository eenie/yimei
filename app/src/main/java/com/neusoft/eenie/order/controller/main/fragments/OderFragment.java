package com.neusoft.eenie.order.controller.main.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.eenie.bmoblibrary.bmob.bean.OrderBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.base.BaseV4Fragment;
import com.neusoft.eenie.order.controller.main.OrderInfoActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class OderFragment extends BaseV4Fragment {

    ListView recyOrderList;
    OrderListAdapter recyOrderListAdapter;
    List<OrderBean> orderBeanList;
    SwipeRefreshLayout swipeLayout;

    public OderFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_oder, container, false);

        findView(contentView);

        initOBJ();
        recyOrderListAdapter = new OrderListAdapter();
        recyOrderList = (ListView) contentView.findViewById(R.id.recyOrderList);
        recyOrderList.setAdapter(recyOrderListAdapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updata();
            }
        });

        recyOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent foodIntent = new Intent(getContext(), OrderInfoActivity.class);
                foodIntent.putExtra("orderBean", orderBeanList.get(position));
                startActivity(foodIntent);
            }
        });


        updata();

        return contentView;
    }


    private void findView(View rootView) {
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
    }

    private void initOBJ() {

        orderBeanList = new ArrayList<>();

    }


    private class OrderListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class Holder {
            TextView textBackup;
            TextView textState;
            TextView textOrderNum;
            TextView textScore;
            TextView textPrice;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = getLayoutInflater(Bundle.EMPTY).inflate(R.layout.item_order_list, null);
                holder.textBackup = (TextView) convertView.findViewById(R.id.textBackup);
                holder.textState = (TextView) convertView.findViewById(R.id.textState);
                holder.textOrderNum = (TextView) convertView.findViewById(R.id.textOrderNum);
                holder.textScore = (TextView) convertView.findViewById(R.id.textScore);
                holder.textPrice = (TextView) convertView.findViewById(R.id.textPrice);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }


//            Log.e("textBackup", orderBeanList.get(position).getUser().getUsername());
            holder.textBackup.setText("亿美在线");
            holder.textOrderNum.setText("订单号：" + String.valueOf(orderBeanList.get(position).getOrdernum()));
            holder.textScore.setText("可获得积分：" + String.valueOf(orderBeanList.get(position).getScore()));
            holder.textPrice.setText("消费：￥" + String.valueOf(orderBeanList.get(position).getTotalPrice()) + ".00");
            switch (orderBeanList.get(position).getState()) {
                case 0:
                    holder.textState.setText("等待付款");
                    break;
                case 1:
                    holder.textState.setText("付款成功");
                    break;
                case 2:
                    holder.textState.setText("商家接单");
                    break;
                case 3:
                    holder.textState.setText("交易完成");
                    break;
                case 4:
                    holder.textState.setText("取消订单");
                    break;
            }


            return convertView;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        recyOrderListAdapter.notifyDataSetInvalidated();

    }

    public void updata() {

        UserBean userBean = BaseApplication.bombAPI.getCurrentUser();
        if (userBean != null) {
            BaseApplication.bombAPI.getOrder(userBean, new FindListener<OrderBean>() {
                @Override
                public void onSuccess(List<OrderBean> list) {
                    orderBeanList = list;
                    recyOrderListAdapter.notifyDataSetInvalidated();
                    swipeLayout.setRefreshing(false);
//                    Toast.makeText(getContext(), "数据刷新成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int i, String reason) {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            //没有用户的时候不刷新

        }


    }


}
