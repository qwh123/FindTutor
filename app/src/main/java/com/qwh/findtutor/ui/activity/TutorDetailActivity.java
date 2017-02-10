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
import android.widget.TextView;
import android.widget.Toast;

import com.qwh.findtutor.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorDetailActivity extends AppCompatActivity {

    @Bind(R.id.btn_tutor_detail_join)
    Button btnTutorDetailJoin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        ButterKnife.bind(this);
        initToolBar();

    }

    private void initToolBar() {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(getIntent().getStringExtra("teacher_name"));
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.icon_back));
    }


    @OnClick(R.id.btn_tutor_detail_join)
    public void onClick(View view) {
        Toast.makeText(this, "join", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutor_detail_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                startActivity(new Intent(TutorDetailActivity.this, CommentActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
