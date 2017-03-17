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
import android.widget.ImageView;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.SearchResultBean;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.utils.PreferenceUtil;
import com.qwh.findtutor.utils.SpacesItemDecoration;
import com.qwh.findtutor.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    private List<SearchResultBean.DataBean> mData = new ArrayList<>();
    private CommonAdapter<SearchResultBean.DataBean> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

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
        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
        rvSearch.addItemDecoration(decoration);
        mAdapter = new CommonAdapter<SearchResultBean.DataBean>(this, R.layout.item_main_rv, mData) {
            @Override
            public void setData(ViewHolder holder, SearchResultBean.DataBean data) {
                holder.setImageWithUrl(R.id.item_main_rv_icon, data.getIcon());
                holder.setText(R.id.item_main_rv_name, data.getNickname());
                holder.setText(R.id.item_main_rv_level, data.getEducation_bg());
                holder.setText(R.id.item_main_rv_adress, data.getAddress());
//                holder.setText(R.id.item_main_rv_summary, data.get());
            }
        };
        rvSearch.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(SearchActivity.this, TeacherDetailActivity.class)
                        .putExtra("teacher_name", mData.get(position).getNickname())
                        .putExtra("id", mData.get(position).getId())

                );
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

                    initData(edtSearchContent.getText().toString().trim());
//                    getData(edtSearchContent.getText().toString().trim());

                    edtSearchContent.setText("");
                } else {
                    finish();
                }

                break;
        }
    }


    public void initData(String content) {
        List<Param> params = new ArrayList<>();
        if (TextUtils.isEmpty(content))
            return;
        if (isNumeric(content) && Utils.judgePhoneNums(content))
            params.add(new Param("tel", content));
        else
            params.add(new Param("nickname", content));
        OkHttpUtils.post(apiServer.URL_Search, new OkHttpUtils.ResultCallback<SearchResultBean>() {
            @Override
            public void onSuccess(SearchResultBean data) {
                mData = data.getData();
                if (mData != null && mData.size() > 0)
                    initView();
                else {
                    Toast.makeText(SearchActivity.this, "未找到相关信息", Toast.LENGTH_SHORT).show();
                }
//                mData1=data.get
//                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);

    }


    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
