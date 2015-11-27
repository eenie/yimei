package com.neusoft.eenie.order.controller.main;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.btp.callback.UploadListener;
import com.neusoft.eenie.bmoblibrary.bmob.BombAPI;
import com.neusoft.eenie.bmoblibrary.bmob.bean.UserBean;
import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.base.BaseApplication;
import com.neusoft.eenie.order.controller.main.fragments.MeFragment;
import com.neusoft.eenie.order.utiles.mUtiles;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;


public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout relativeHead;

    UserBean userBean;

    Toolbar menuToobar;

    Context context;

    ImageView imageHead;

    BombAPI bombAPI;

    Button btnLogOut;

    private final int REQUEST_CODE_PICK_IMAGE = 100;
    private final int RESPON_CODE_PICK_IMAGE = 101;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 200;
    private final int RESPON_CODE_CAPTURE_CAMEIA = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        context = this;

        bombAPI = BaseApplication.getBomAPI();


        userBean = (UserBean) getIntent().getSerializableExtra("userinfo");
        if (userBean == null) {
            return;
        }

        findView();
        initToolbar();

        btnLogOut.setOnClickListener(this);
        relativeHead.setOnClickListener(this);

    }


    private void showHeadDialog() {
        AlertDialog.Builder builder = initDialog("修改头像");
        builder.setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                switch (which) {
                    case 0:
                        getImageFromAlbum();

                        break;
                    case 1:
                        getImageFromCamera();

                        break;
                }
            }
        });
        builder.create().show();
    }


    private AlertDialog.Builder initDialog(String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View titleView = getLayoutInflater().inflate(R.layout.custom_userinfo_dialog_title, null);
        builder.setCustomTitle(titleView);
        ((TextView) titleView.findViewById(R.id.textTitle)).setText(title);
        return builder;

    }


    private void findView() {

        menuToobar = (Toolbar) findViewById(R.id.menuToobar);
        relativeHead = (RelativeLayout) findViewById(R.id.relativeHead);
        imageHead = (ImageView) findViewById(R.id.imageHead);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

    }

    private void initToolbar() {
        menuToobar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        menuToobar.setTitle("个人信息");
        setSupportActionBar(menuToobar);
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


    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }


    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.relativeHead:
                showHeadDialog();
                break;
            case R.id.btnLogOut:
                BaseApplication.userLogout();
                setResult(MeFragment.USERINFO_REQUESTCODE_CHANGE);
                finish();

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CODE_PICK_IMAGE) {


            //设置图片压缩参数


            Log.e("REQUEST_CODE_PICK_IMAGE", "相册");
            if (data != null) {
                Uri imageURI = data.getData();
                //给imageView加载图片


                ContentResolver resolver = getContentResolver();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, imageURI);


                    imageHead.setImageBitmap(mUtiles.getRoundCornerBitmap(bitmap, 60));

                } catch (IOException e) {
                    e.printStackTrace();
                }



//                loadImageWrapView(imageHead, imageURI.getPath());
                bombAPI.upHeadImage(imageURI.getPath(), new UploadListener() {
                    @Override
                    public void onSuccess(String fileName, String url, BmobFile bmobFile) {
                        Log.e("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + bmobFile.getUrl());
                    }

                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onError(int code, String reason) {

                    }
                });

            }


        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Log.e("REQUEST_CODE_PICK_IMAGE", "照相机");
            if (data != null) {
                Bitmap thunbnail = data.getParcelableExtra("data");

                System.out.println(thunbnail);


                //给imageView加载图片
//                loadImageWrapView(imageHead, imageURI.getPath());
            }
        }

    }


    public void loadImageWrapView(ImageView imageView, String imagePath) {
        ImageOptions.Builder imageBuilder = new ImageOptions.Builder();
        imageBuilder.setSize(0, 0);
        ImageOptions options = imageBuilder.build();
        x.image().bind(imageView, imagePath, options);

    }


}
