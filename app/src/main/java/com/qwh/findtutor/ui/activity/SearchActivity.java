package com.qwh.findtutor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.TutorBean;
import com.qwh.findtutor.utils.SpacesItemDecoration;
import com.qwh.findtutor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends Activity {

    @Bind(R.id.edt_search_content)
    EditText edtSearchContent;
    @Bind(R.id.iv_search_del)
    ImageView ivSearchDel;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.rv_search)
    RecyclerView rvSearch;
    private List<TutorBean> mData = new ArrayList<>();
    private List<TutorBean> mData1 = new ArrayList<>();
    private CommonAdapter<TutorBean> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

        edtSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edtSearchContent.getText().toString())) {
                    ivSearchDel.setVisibility(View.GONE);
                    btnSearch.setText(getString(R.string.search_cancel));
                } else {
                    ivSearchDel.setVisibility(View.VISIBLE);
                    btnSearch.setText(getString(R.string.search_confirm));
                }

            }
        });

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<TutorBean>(this, R.layout.item_main_rv, mData1) {
            @Override
            public void setData(ViewHolder holder, TutorBean TutorBean) {
                holder.setImageResource(R.id.item_main_rv_rv_icon, TutorBean.getImg());
                holder.setText(R.id.item_main_rv_rv_name, TutorBean.getName());
                holder.setText(R.id.item_main_rv_rv_type, "教授课程: " + TutorBean.getType());
                holder.setText(R.id.item_main_rv_rv_summary, TutorBean.getSummary());
            }
        };
        mAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        rvSearch.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(SearchActivity.this, TutorDetailActivity.class)
                        .putExtra("teacher_name", mData1.get(position).getName()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }


    @OnClick({R.id.iv_search_del, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_del:
                edtSearchContent.setText("");
//                ivSearchDel.setVisibility(View.INVISIBLE);
//                btnSearch.setText(getString(R.string.search_cancel));
                break;
            case R.id.btn_search:
                Utils.hideSoftInput(edtSearchContent, this);
                if (btnSearch.getText().toString().equals(getString(R.string.search_confirm))) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getData(edtSearchContent.getText().toString().trim());
                    mAdapter.notifyDataSetChanged();
                    edtSearchContent.setText("");
                } else {
                    finish();
                }

                break;
        }
    }


    public void initData() {
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周san讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "数学", "每周san讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "丘老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "丘老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "数学", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are hahah"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "数学", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "数学", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "测试"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "其他", "测试测试"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "其他", "测试测试测试"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "其他", "每周测试测试一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "其他", "每周测试一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "其他", "测试测试测试"));

    }

    public void getData(String searchContent) {
        mData1.clear();
        for (TutorBean bean : mData) {
            if (bean.getName().contains(searchContent))
                mData1.add(bean);
        }
        if (mData1.size() < 1)
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }
}
