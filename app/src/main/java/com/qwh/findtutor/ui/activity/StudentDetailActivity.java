package com.qwh.findtutor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentDetailActivity extends AppCompatActivity {

    @Bind(R.id.btn_info_detail_about)
    Button btnInfoAbout;
    @Bind(R.id.toolbar_info)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        ButterKnife.bind(this);
        initToolBar();

    }

    private void initToolBar() {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(getIntent().getStringExtra("student_title"));
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.mipmap.em_back));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.btn_info_detail_join, R.id.btn_info_detail_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_info_detail_join:

                String tel = "18850105250";
                if (!Utils.judgePhoneNums(tel)) {
                    Toast.makeText(this, "电话号码格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Utils.ShowCallPhoneDialog(this, tel);
                break;
            case R.id.btn_info_detail_about:
                btnInfoAbout.setText("取消关注");
                break;
        }
    }
}
