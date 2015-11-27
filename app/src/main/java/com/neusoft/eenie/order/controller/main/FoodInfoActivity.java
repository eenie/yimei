package com.neusoft.eenie.order.controller.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.order.R;

import org.xutils.x;

public class FoodInfoActivity extends AppCompatActivity {


    Toolbar menuToobar;

    ImageView imageFoodPic;

    TextView textFoodName;
    TextView textFoodPrice;
    TextView textFoodCount;
    TextView textFoodInfo;

    FoodBean foodBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        foodBean = (FoodBean) getIntent().getSerializableExtra("foodBean");
        findView();
        menuToobar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        menuToobar.setTitle("菜品详情");
        setSupportActionBar(menuToobar);

        if (foodBean != null) {
            textFoodName.setText(foodBean.getFoodName());
            textFoodPrice.setText("价格：￥" + String.valueOf(foodBean.getFoodPrice()) + ".00");
            textFoodInfo.setText(foodBean.getRemark());
            textFoodCount.setText(String.valueOf(foodBean.getCount()));
            x.image().bind(imageFoodPic, foodBean.getFoodPicture().getFileUrl(FoodInfoActivity.this));
        }



    }

    private void findView() {
        menuToobar = (Toolbar) findViewById(R.id.menuToobar);
        imageFoodPic = (ImageView) findViewById(R.id.imageFoodPic);
        textFoodName = (TextView) findViewById(R.id.textFoodName);
        textFoodPrice = (TextView) findViewById(R.id.textFoodPrice);
        textFoodCount = (TextView) findViewById(R.id.textFoodCount);
        textFoodInfo = (TextView) findViewById(R.id.textFoodInfo);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//        overridePendingTransition(0, R.anim.slide_bottom_out);
    }

}
