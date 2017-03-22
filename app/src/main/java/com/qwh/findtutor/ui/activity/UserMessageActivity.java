package com.qwh.findtutor.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.bean.UserInfoBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.utils.FileUtil;
import com.qwh.findtutor.utils.PreferenceUtil;
import com.qwh.findtutor.utils.UploadUtil;
import com.qwh.findtutor.utils.Utils;
import com.qwh.findtutor.view.AvatarImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.edt_user_message_class_adress)
    EditText edtUserMessageClassAdress;
    @Bind(R.id.edt_user_message_sign)
    EditText edtUserMessageSign;

    List<String> list = new ArrayList<>();
    @Bind(R.id.edt_user_message_nianji)
    EditText edtUserMessageNianji;
    @Bind(R.id.btn_user_message_nianji)
    Button BtnUserMessageNianji;
    @Bind(R.id.edt_user_message_contact)
    EditText edtUserMessageContact;

    private UserInfoBean.DataBean user;

    private String urlpath = null;
    private boolean isChangeHead = false;//是否更改头像

    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;
    private String nickname;
    private String sex;
    private String adress;
    private String classAdress;
    private String sign;
    private String contact;
    private String education_bg;

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
//                .load(user.getIcon())
//                .asBitmap()
//                .centerCrop()
//                .error(R.drawable.img_user)
//                .into(new BitmapImageViewTarget(ivUserMessageHead) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        ivUserMessageHead.setImageBitmap(resource);
//                    }
//                });

        tvUserMessageAccount.setText(PreferenceUtil.getString(SharedSaveConstant.User_Account, ""));
        edtUserMessageNickname.setText(user.getNickname());
        if (user.getSex().equals("0")) {
            userMessageRbtnMan.setChecked(true);
        } else {
            userMessageRbtnGril.setChecked(true);
        }
        btnUserMessageAdress.setText(user.getAddress());
        edtUserMessageContact.setText(user.getTel());
        edtUserMessageClassAdress.setText(user.getClass_address());
        edtUserMessageSign.setText(user.getDetail());

        if (user.getType().equals("1")) {
            edtUserMessageNianji.setText(user.getEducation_bg());
            BtnUserMessageNianji.setVisibility(View.GONE);
        } else {
            BtnUserMessageNianji.setText(user.getEducation_bg());
            edtUserMessageNianji.setVisibility(View.GONE);
            BtnUserMessageNianji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(UserMessageActivity.this)
                            .setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("HomeFragment", "onClick: " + list.get(i));
                                    BtnUserMessageNianji.setText(list.get(i));
                                }
                            })
                            .show();
                }
            });

        }

        ivUserMessageHead.setBtnClickedColor("#00AAAA"); //设置按钮点击后的颜色
        ivUserMessageHead.setTitleColor("#000000");  //设置标题的颜色
        ivUserMessageHead.setTitleLineColor("#e5e5e5"); //设置标题下的分割线的颜色
        ivUserMessageHead.setAfterCropListener(new AvatarImageView.AfterCropListener() {
            @Override
            public void afterCrop(Bitmap photo) {
                urlpath = FileUtil.saveFile(UserMessageActivity.this, IMAGE_FILE_NAME, photo);
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                //client.ENCODING_GZIP
                params.put("id", PreferenceUtil.getString(SharedSaveConstant.User_Id, ""));
                //添加文件
                try {
                    File f = new File(urlpath);
                    if (f.exists()) {
                        Log.i("AsyncHttp", "Yes");
                        params.put("icon", f);
                    } else {
                        Log.i("AsyncHttp", "No");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Log.i("AsyncHttp", "afterCrop: "+params.toString());
                 /*//////////////
		         * /把文件上传*/
                client.post(apiServer.URL_User_Message_Update_ICON, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        try {
                            //获取返回内容
                            String resp = new String(bytes, "utf-8");
                            Log.i("AsyncHttp", resp);
                            //在这里处理返回的内容，例如解析json什么的...

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                        //在这里处理连接失败的处理...
                        Log.i("AsyncHttp", "onFailure: ");
                    }
                });

            }
        });
        userMessageSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int rbtnId = group.getCheckedRadioButtonId();
                if (rbtnId == R.id.user_message_rbtn_gril) {
                    sex = "1";
                } else {
                    sex = "0";
                }
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
        list.add("幼儿园");
        list.add("一年级");
        list.add("二年级");
        list.add("三年级");
        list.add("四年级");
        list.add("五年级");
        list.add("六年级");
        list.add("初  一");
        list.add("初  二");
        list.add("初  三");
        list.add("高  一");
        list.add("高  二");
        list.add("高  三");
        List<Param> params = new ArrayList<>();
        params.add(new Param("id", PreferenceUtil.getString(SharedSaveConstant.User_Id, "")));
        OkHttpUtils.post(apiServer.URL_User_Message, new OkHttpUtils.ResultCallback<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                user = data.getData().get(0);
                initView();
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);
    }

    @OnClick({R.id.iv_user_message_back, R.id.btn_update, R.id.btn_user_message_adress})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_user_message_back:
                finish();
                break;
            case R.id.btn_update:
                nickname = edtUserMessageNickname.getText().toString();
                adress = btnUserMessageAdress.getText().toString();
                classAdress = edtUserMessageClassAdress.getText().toString();
                contact = edtUserMessageContact.getText().toString();
                sign = edtUserMessageSign.getText().toString();
                if (user.getType().equals("1"))
                    education_bg = edtUserMessageNianji.getText().toString();
                else
                    education_bg = BtnUserMessageNianji.getText().toString();
                SubMessage();
                break;
            case R.id.btn_user_message_adress:
                Utils.showCityPick(this, btnUserMessageAdress);
                break;
        }
    }

    private void SubMessage() {
        List<Param> params = new ArrayList<>();
        params.add(new Param("id", PreferenceUtil.getString(SharedSaveConstant.User_Id, "")));
        if (nickname != null)
            params.add(new Param("nickname", nickname));
        if (sex != null)
            params.add(new Param("sex", sex));
        if (contact != null)
            params.add(new Param("contact", contact));
        if (classAdress != null)
            params.add(new Param("class_address", classAdress));
        if (adress != null)
            params.add(new Param("address", adress));
        if (sign != null)
            params.add(new Param("detail", sign));
        if (education_bg != null)
            params.add(new Param("education_bg", education_bg));
        Log.i("education_bg", "SubMessage: " + education_bg);
//        params.add(new Param("icon", ));
        OkHttpUtils.post(apiServer.URL_User_Message_Update, new OkHttpUtils.ResultCallback<CommonBean>() {
            @Override
            public void onSuccess(CommonBean data) {
                toast(data.getSummary());
                if (data.getCode() == 200)
                    finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);
    }


}
