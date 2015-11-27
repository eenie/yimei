package com.neusoft.eenie.order.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodBean;
import com.neusoft.eenie.bmoblibrary.bmob.bean.FoodCategoryBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class addFoodActivity extends AppCompatActivity {


    EditText foodName;

    Spinner foodCategory;
    EditText price;

    EditText foodInfo;
    Button btnCommit;
    BombAPI bombAPI;


    Handler handler;

    int position = 0;

    List<FoodBean> foodBeanList;

    List<FoodCategoryBean> foodCategoryBeanList;


    UploadFoodRunnable uploadFoodRunnable;
    JSONArray foodList;

    private final String picHost = "http://192.168.1.222:8080/HOrderServer/";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);


        uploadFoodRunnable = new UploadFoodRunnable();

        handler = new Handler();
        bombAPI = BaseApplication.getBomAPI();

        bombAPI.getFoodCategory(100, new FindListener<FoodCategoryBean>() {
            @Override
            public void onSuccess(List<FoodCategoryBean> list) {

                Log.e("onSuccess", "onSuccess");
                foodCategoryBeanList = list;
                String[] str = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    str[i] = foodCategoryBeanList.get(i).getCategoryName();
                    Log.e("onSuccess", str[i]);
                }
                addFood();
                foodCategory.setAdapter(new ArrayAdapter(addFoodActivity.this, android.R.layout.simple_spinner_item, str));
            }

            @Override
            public void onError(int code, String reason) {
                Log.e("onError", reason);
            }
        });

        foodName = (EditText) findViewById(R.id.foodName);
        foodCategory = (Spinner) findViewById(R.id.foodCategory);
        price = (EditText) findViewById(R.id.price);
        foodInfo = (EditText) findViewById(R.id.remark);
        btnCommit = (Button) findViewById(R.id.btnCommit);


        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.post(uploadFoodRunnable);


//                for (FoodBean f : foodBeanList) {
//
//                    Log.e("FoodBean", f.getFoodName());
//
//                    f.getFoodPicture().uploadblock(addFoodActivity.this, new UploadFileListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(addFoodActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//
//                        }
//                    });
//                }


            }
        });


    }


    public void addFood() {
        try {
            InputStream is = getAssets().open("food.txt");
            InputStreamReader reader = new InputStreamReader(is);
            char[] c = new char[1024];
            int len = reader.read(c);
            StringBuffer sb = new StringBuffer();
            while (len > -1) {
                sb.append(new String(c, 0, len));
                len = reader.read(c);
            }
            is.close();
            reader.close();

//            parsePic(sb.toString());


            foodBeanList = jsonParse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parsePic(String content) {

        JSONObject totalFood = null;
        try {
            totalFood = new JSONObject(content);
            JSONArray foodList = totalFood.getJSONArray("menus");
            for (int i = 0; i < foodList.length(); i++) {
                JSONObject food = foodList.getJSONObject(i);
                final String path = picHost + food.getString("pic");
                final String name = food.getString("pic").substring(7);
                new Thread() {
                    @Override
                    public void run() {
                        downPic(path, name);
                    }
                }.start();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public synchronized void downPic(String path, String name) {

        Log.e(path, name);

        File file = new File(getCacheDir() + File.separator + name);
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(5000);

            System.out.println(httpURLConnection.getContentLength());

            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = bis.read(b);
            while (len > -1) {
                fos.write(b, 0, len);
                len = bis.read(b);
            }
            fos.flush();
            fos.close();
            bis.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<FoodBean> jsonParse(String content) {
        foodBeanList = new ArrayList<>();
        try {
            JSONObject totalFood = new JSONObject(content);
            foodList = totalFood.getJSONArray("menus");

            for (int i = 0; i < foodList.length(); i++) {


                JSONObject food = foodList.getJSONObject(i);
                FoodBean foodBean = new FoodBean();

                foodBean.setFoodPrice(food.getInt("price"));
                foodBean.setFoodName(food.getString("menuName"));
                foodBean.setRemark(food.getString("remark"));
                String picName = food.getString("pic").substring(7);
                File file = new File(getCacheDir() + File.separator + picName);
                BmobFile bmobFile = new BmobFile(file);
                foodBean.setFoodPicture(bmobFile);
                System.out.println(picName);
                if (food.getString("category").equals("1201")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(0));
                } else if (food.getString("category").equals("1202")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(1));
                } else if (food.getString("category").equals("1203")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(2));
                } else if (food.getString("category").equals("1204")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(3));
                } else if (food.getString("category").equals("1205")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(4));
                } else if (food.getString("category").equals("1206")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(5));
                } else if (food.getString("category").equals("1207")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(6));
                }
                foodBeanList.add(foodBean);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return foodBeanList;
    }


    private class UploadFoodRunnable implements Runnable {


        @Override
        public void run() {


            try {


                JSONObject food = foodList.getJSONObject(position);
                final FoodBean foodBean = new FoodBean();
                foodBean.setFoodPrice(food.getInt("price"));
                foodBean.setFoodName(food.getString("menuName"));
                foodBean.setRemark(food.getString("remark"));
                String picName = food.getString("pic").substring(7);
                File file = new File(getCacheDir() + File.separator + picName);
                BmobFile bmobFile = new BmobFile(file);



                foodBean.setFoodPicture(bmobFile);
                System.out.println(picName);
                if (food.getString("category").equals("1201")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(0));
                } else if (food.getString("category").equals("1202")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(1));
                } else if (food.getString("category").equals("1203")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(2));
                } else if (food.getString("category").equals("1204")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(3));
                } else if (food.getString("category").equals("1205")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(4));
                } else if (food.getString("category").equals("1206")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(5));
                } else if (food.getString("category").equals("1207")) {
                    foodBean.setFoodCategory(foodCategoryBeanList.get(6));
                }



                foodBean.getFoodPicture().upload(addFoodActivity.this, new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(addFoodActivity.this, "上传成功", Toast.LENGTH_SHORT).show();


                        bombAPI.addFood(foodBean, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(addFoodActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                position++;
                                if (position <= foodBeanList.size()) {
                                    handler.post(UploadFoodRunnable.this);
                                }

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(addFoodActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        });



                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(addFoodActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });






            } catch (JSONException e) {
                e.printStackTrace();
            }


//            final FoodBean foodBean = foodBeanList.get(position);
//            foodBean.getFoodPicture().upload(addFoodActivity.this, new UploadFileListener() {
//                @Override
//                public void onSuccess() {
//                    bombAPI.addFood(foodBean, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(addFoodActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                            position++;
//                            if (position <= foodBeanList.size()) {
//                                handler.post(UploadFoodRunnable.this);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Toast.makeText(addFoodActivity.this, s, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onFailure(int i, String s) {
//                    Toast.makeText(addFoodActivity.this, s, Toast.LENGTH_SHORT).show();
//                }
//            });


        }
    }

}
