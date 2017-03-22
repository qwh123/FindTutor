package com.qwh.findtutor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.qwh.findtutor.R;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.AllUserBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.test.TeacherBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.ui.activity.DropMenu.DropMenuAdapter;
import com.qwh.findtutor.ui.activity.DropMenu.entity.FilterUrl;
import com.qwh.findtutor.ui.fragment.HomeFragment;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherActivity extends Activity implements OnFilterDoneListener {


    @Bind(R.id.mFilterContentView)
    RecyclerView recyclerviewTeacherType;
    String title;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
//    @Bind(R.id.mFilterContentView)
//    TextView mFilterContentView;

    private List<AllUserBean.DataBean.UserBean> mAllData = new ArrayList<>();
    private List<AllUserBean.DataBean.UserBean> mData1 = new ArrayList<>();
    private CommonAdapter<AllUserBean.DataBean.UserBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        initFilterDropDownView();
    }

    public void initView() {

        recyclerviewTeacherType.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<AllUserBean.DataBean.UserBean>(this, R.layout.item_teacher, mAllData) {
            @Override
            public void setData(ViewHolder holder, AllUserBean.DataBean.UserBean data) {
                if (TextUtils.isEmpty(data.getIcon()))
                    holder.setImageResource(R.id.iv_item_teacher_img, R.drawable.img_user);
                else {
                    holder.setImageWithUrl(R.id.iv_item_teacher_img, data.getIcon());
                }
                holder.setText(R.id.tv_item_teacher_name, "教员姓名:" + data.getNickname());
                holder.setText(R.id.tv_item_teacher_class, "教授课程: " + data.getSubject_id());
                holder.setText(R.id.tv_item_teacher_adress, "地址:" + data.getAddress());
                holder.setText(R.id.tv_item_teacher_level, "备注:" + data.getDetail());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerviewTeacherType.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(3);
        recyclerviewTeacherType.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(TeacherActivity.this, TeacherDetailActivity.class)
                        .putExtra("teacher_name", mAllData.get(position).getNickname())
                        .putExtra("id", mAllData.get(position).getId())
                );
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"科目", "综合排序", "筛选"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));
        if (getIntent().getStringExtra(HomeFragment.KEY_TYPE).equals(HomeFragment.VALUE_TYPE_TEACHER)) {
            initData(null, null, null);
        } else if (getIntent().getStringExtra(HomeFragment.KEY_TYPE).equals(HomeFragment.VALUE_TYPE_COURSE)) {
            try {
                FilterUrl.instance().position = 0;
                FilterUrl.instance().positionTitle = getIntent().getStringExtra("positionTitle");
                if (FilterUrl.instance().position != 2) {
                    dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
                }
                initData(null, null, FilterUrl.instance().positionTitle);
//                getData(FilterUrl.instance().positionTitle);//获取特定
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 2) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }
        Log.i("teacheractivity", "onFilterDone: position:"+FilterUrl.instance().position);
        Log.i("teacheractivity", "onFilterDone: positionTitle:"+FilterUrl.instance().positionTitle);
        dropDownMenu.close();
        Log.i("dropdown:", "youchoose:==>" + FilterUrl.instance().toString());
        initData(null, null, FilterUrl.instance().positionTitle);
//        getData(FilterUrl.instance().getChoose());
        FilterUrl.instance().clear();
//        mFilterContentView.setText(FilterUrl.instance().toString());
    }

    private void getData(String content) {
        Log.i("dropdown:", "youchoose1:==>" + content);
        mData1.clear();
        if (TextUtils.isEmpty(content)) {//获取所有
            for (AllUserBean.DataBean.UserBean data : mAllData) {
                mData1.add(data);
            }
        } else
            for (AllUserBean.DataBean.UserBean data : mAllData) {
                if (content.contains(data.getSubject_id())) {//获取特定
                    mData1.add(data);
                }
            }
        if (mData1.size() < 1)
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }

    /**
     * @param sex        性别
     * @param address    地址
     * @param subject_id 科目
     */
    public void initData(String sex, String address, String subject_id) {
        List<Param> params = new ArrayList<>();
        params.add(new Param("type", "1"));
        if (!TextUtils.isEmpty(sex))
            params.add(new Param("sex", sex));
        if (!TextUtils.isEmpty(address))
            params.add(new Param("address", address));
        if (!TextUtils.isEmpty(subject_id))
            params.add(new Param("subject_id", subject_id));
        OkHttpUtils.post(apiServer.URL_Get_Teacher, new OkHttpUtils.ResultCallback<AllUserBean>() {
            @Override
            public void onSuccess(AllUserBean data) {
                mAllData = data.getData().getUser();
                initView();
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);

//        mData.add(new TeacherBean("0x001", R.drawable.img_user, "张老师", "小学语文", "仓山区，鼓楼区", "汉语甲级"));
//        mData.add(new TeacherBean("0x002", R.drawable.img_user, "张老师", "小学数学", "鼓楼区", "精通函数"));
//        mData.add(new TeacherBean("0x003", R.drawable.img_user, "丘老师", "小学语文", "仓山区，鼓楼区", "汉语甲级"));
//        mData.add(new TeacherBean("0x004", R.drawable.img_user, "李老师", "小学数学", "仓山区", "精通函数"));
//        mData.add(new TeacherBean("0x005", R.drawable.img_user, "丘老师", "初中英语", "仓山区，鼓楼区", "英语六级，留过学"));
//        mData.add(new TeacherBean("0x006", R.drawable.img_user, "范老师", "小学语文", "仓山区", "汉语甲级"));
//        mData.add(new TeacherBean("0x007", R.drawable.img_user, "张老师", "初中英语", "仓山区，鼓楼区", "英语六级"));
//        mData.add(new TeacherBean("0x008", R.drawable.img_user, "范老师", "小学语文", "鼓楼区", "汉语甲级"));
//        mData.add(new TeacherBean("0x009", R.drawable.img_user, "黄老师", "初中英语", "仓山区，鼓楼区", "英语六级"));
//        mData.add(new TeacherBean("0x0010", R.drawable.img_user, "黄老师", "高中物理", "仓山区，鼓楼区", "物理科毕业"));
//        mData.add(new TeacherBean("0x0011", R.drawable.img_user, "林老师", "高中物理", "仓山区", "物理科毕业"));
//        mData.add(new TeacherBean("0x0012", R.drawable.img_user, "林老师", "高中物理", "仓山区", "物理科毕业"));
//        mData.add(new TeacherBean("0x0013", R.drawable.img_user, "张老师", "艺术", "仓山区，鼓楼区", "省级舞蹈大赛一等奖"));
    }

    @OnClick(R.id.iv_teacher_type_back)
    public void onClick(View view) {
        if (dropDownMenu.isShowing()) {
            dropDownMenu.close();
            return;
        }
        finish();
    }
}
