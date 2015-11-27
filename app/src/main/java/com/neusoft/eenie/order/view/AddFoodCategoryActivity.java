package com.neusoft.eenie.order.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodCategoryBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;

import cn.bmob.v3.listener.SaveListener;

public class AddFoodCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAdd;
    EditText editName;
    BombAPI bombAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_category);
        editName = (EditText) findViewById(R.id.editName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        bombAPI = BaseApplication.getBomAPI();
        btnAdd.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:







//                if (!editName.getText().toString().isEmpty()) {
//
//                    FoodCategoryBean foodCategoryBean = new FoodCategoryBean();
//
//                    foodCategoryBean.setCategoryName(editName.getText().toString());
//
//                    bombAPI.addFoodCategory(foodCategoryBean, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            editName.setText("");
//                        }
//
//                        @Override
//                        public void onFailure(int code, String reason) {
//
//                        }
//                    });
//
//                }


                break;
        }
    }
}
