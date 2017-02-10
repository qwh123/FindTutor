package com.qwh.findtutor.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.utils.FileUtil;
import com.qwh.findtutor.utils.Utils;
import com.qwh.findtutor.view.AvatarImageView;

import butterknife.Bind;
import butterknife.OnClick;

public class UserMessageActivity extends BaseActivity {
    private static final String IMAGE_FILE_NAME = "avatar.jpg";

    @Bind(R.id.iv_user_message_head)
    AvatarImageView ivUserMessageHead;
    @Bind(R.id.tv_user_message_account)
    TextView tvUserMessageAccount;
    @Bind(R.id.edt_user_message_nickname)
    EditText edtUserMessageNickname;
    @Bind(R.id.user_message_rbtn_man)
    RadioButton userMessageRbtnMan;
    @Bind(R.id.user_message_rbtn_gril)
    RadioButton userMessageRbtnGril;
    @Bind(R.id.user_message_sex)
    RadioGroup userMessageSex;
    @Bind(R.id.btn_user_message_adress)
    Button btnUserMessageAdress;
    @Bind(R.id.edt_user_message_way)
    EditText edtUserMessageWay;
    @Bind(R.id.edt_user_message_sign)
    EditText edtUserMessageSign;

    private String urlpath = null;
    private boolean isChangeHead = false;//是否更改头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayout() {
        return R.layout.user_message;
    }

    @Override
    public void initView() {
//        Glide.with(this)
//                .load(data.getIcon())
//                .asBitmap()
//                .centerCrop()
//                .error(R.drawable.img_user)
//                .into(new BitmapImageViewTarget(ivUserMessageHead) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        ivUserMessageHead.setImageBitmap(resource);
//                    }
//                });

        ivUserMessageHead.setBtnClickedColor("#00AAAA"); //设置按钮点击后的颜色
        ivUserMessageHead.setTitleColor("#000000");  //设置标题的颜色
        ivUserMessageHead.setTitleLineColor("#e5e5e5"); //设置标题下的分割线的颜色
        ivUserMessageHead.setAfterCropListener(new AvatarImageView.AfterCropListener() {
            @Override
            public void afterCrop(Bitmap photo) {
                urlpath = FileUtil.saveFile(UserMessageActivity.this, IMAGE_FILE_NAME, photo);
                isChangeHead = true;
                Log.i("img_result", "afterCrop:==> 设置头像成功path："+urlpath);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在拍照、选取照片、裁剪Activity结束后，调用的方法
        if (ivUserMessageHead != null) {
            ivUserMessageHead.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void getData() {

    }

    @OnClick({R.id.iv_user_message_back, R.id.btn_update, R.id.btn_user_message_adress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_message_back:
                finish();
                break;
            case R.id.btn_update:
                toast("update");
                break;
            case R.id.btn_user_message_adress:
                Utils.showCityPick(this, btnUserMessageAdress);
                break;
        }
    }



}
