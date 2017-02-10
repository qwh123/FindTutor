package com.qwh.findtutor.ui.activity.DropMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.TeacherBean;
import com.qwh.findtutor.ui.activity.DropMenu.entity.FilterUrl;
import com.qwh.findtutor.ui.activity.TutorDetailActivity;
import com.qwh.findtutor.ui.fragment.HomeFragment;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends Activity implements OnFilterDoneListener {


    @Bind(R.id.mFilterContentView)
    RecyclerView recyclerviewTeacherType;
    String title;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
//    @Bind(R.id.mFilterContentView)
//    TextView mFilterContentView;

    private List<TeacherBean> mData = new ArrayList<>();
    private List<TeacherBean> mData1 = new ArrayList<>();
    private CommonAdapter<TeacherBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        initData();
        initView();
        initFilterDropDownView();
    }

    public void initView() {

        recyclerviewTeacherType.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<TeacherBean>(this, R.layout.item_teacher, mData1) {
            @Override
            public void setData(ViewHolder holder, TeacherBean TeacherBean) {
                holder.setImageResource(R.id.iv_item_teacher_img, TeacherBean.getImg());
                holder.setText(R.id.tv_item_teacher_name, "教员姓名:" + TeacherBean.getName());
                holder.setText(R.id.tv_item_teacher_class, "教授课程: " + TeacherBean.getTeach_class());
                holder.setText(R.id.tv_item_teacher_adress, "授课范围:" + TeacherBean.getTeach_adress());
                holder.setText(R.id.tv_item_teacher_level, "个人描述:" + TeacherBean.getTeach_level());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerviewTeacherType.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerviewTeacherType.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(CategoryActivity.this, TutorDetailActivity.class)
                        .putExtra("teacher_name", mData1.get(position).getName()));
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
            getData("");//获取所有
        } else if (getIntent().getStringExtra(HomeFragment.KEY_TYPE).equals(HomeFragment.VALUE_TYPE_COURSE)) {
            try {
                FilterUrl.instance().position = 0;
                FilterUrl.instance().positionTitle = getIntent().getStringExtra("positionTitle");
                if (FilterUrl.instance().position != 2) {
                    dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
                }
                getData(FilterUrl.instance().positionTitle);//获取特定
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

        dropDownMenu.close();
        Log.i("dropdown:", "youchoose:==>" + FilterUrl.instance().toString());

        getData(FilterUrl.instance().getChoose());
        FilterUrl.instance().clear();
        mAdapter.notifyDataSetChanged();
//        mFilterContentView.setText(FilterUrl.instance().toString());
    }

    private void getData(String content) {
        Log.i("dropdown:", "youchoose1:==>" + content);
        mData1.clear();
        if (TextUtils.isEmpty(content)) {//获取所有
            for (TeacherBean data : mData) {
                mData1.add(data);
            }
        } else
            for (TeacherBean data : mData) {
                Log.i("dropdown:", "youchoose1:==>" + data.getTeach_class());
                if (content.contains(data.getTeach_class()) ) {//获取特定
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

    public void initData() {


        mData.add(new TeacherBean("0x001", R.drawable.img_user, "张老师", "小学语文", "仓山区，鼓楼区", "汉语甲级"));
        mData.add(new TeacherBean("0x002", R.drawable.img_user, "张老师", "小学数学", "鼓楼区", "精通函数"));
        mData.add(new TeacherBean("0x003", R.drawable.img_user, "丘老师", "小学语文", "仓山区，鼓楼区", "汉语甲级"));
        mData.add(new TeacherBean("0x004", R.drawable.img_user, "李老师", "小学数学", "仓山区", "精通函数"));
        mData.add(new TeacherBean("0x005", R.drawable.img_user, "丘老师", "初中英语", "仓山区，鼓楼区", "英语六级，留过学"));
        mData.add(new TeacherBean("0x006", R.drawable.img_user, "范老师", "小学语文", "仓山区", "汉语甲级"));
        mData.add(new TeacherBean("0x007", R.drawable.img_user, "张老师", "初中英语", "仓山区，鼓楼区", "英语六级"));
        mData.add(new TeacherBean("0x008", R.drawable.img_user, "范老师", "小学语文", "鼓楼区", "汉语甲级"));
        mData.add(new TeacherBean("0x009", R.drawable.img_user, "黄老师", "初中英语", "仓山区，鼓楼区", "英语六级"));
        mData.add(new TeacherBean("0x0010", R.drawable.img_user, "黄老师", "高中物理", "仓山区，鼓楼区", "物理科毕业"));
        mData.add(new TeacherBean("0x0011", R.drawable.img_user, "林老师", "高中物理", "仓山区", "物理科毕业"));
        mData.add(new TeacherBean("0x0012", R.drawable.img_user, "林老师", "高中物理", "仓山区", "物理科毕业"));
        mData.add(new TeacherBean("0x0013", R.drawable.img_user, "张老师", "艺术", "仓山区，鼓楼区", "省级舞蹈大赛一等奖"));
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
