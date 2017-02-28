package com.qwh.findtutor.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.test.StudentBean;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class StudentActivity extends BaseActivity {

    @Bind(R.id.rv_student)
    RecyclerView rvStudent;
    private CommonAdapter<StudentBean> mAdapter;
    private List<StudentBean> mStudentBeen;

    @Override
    public int setLayout() {
        return R.layout.activity_student;
    }

    @Override
    public void initView() {
        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<StudentBean>(this, R.layout.item_student, mStudentBeen) {
            @Override
            public void setData(ViewHolder holder, StudentBean bean) {
                holder.setImageResource(R.id.iv_item_student_img, bean.getStu_img());
                holder.setText(R.id.tv_item_student_name, "学员姓名:" + bean.getStu_name());
                //"0"男 ”1“女
                holder.setText(R.id.tv_item_student_sex, " (" + (bean.getStu_sex().equals("0") ? "男" : "女") + ")");
                holder.setText(R.id.tv_item_student_tel, "需求课程:" + bean.getStu_tel());
                holder.setText(R.id.tv_item_student_adress, "上课区域:" + bean.getStu_adress());
                holder.setText(R.id.tv_item_student_level, "学员情况描述:" + bean.getStu_other());
            }
        };
        mAdapter.notifyDataSetChanged();
        rvStudent.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(3);
        rvStudent.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(StudentActivity.this, StudentDetailActivity.class)
                        .putExtra("student_title", mStudentBeen.get(position).getStu_name()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    public void getData() {
        mStudentBeen = new ArrayList<>();
        mStudentBeen.add(new StudentBean("0x01", R.drawable.img_user, "张同学", "0", "语文", "仓山", "理解力比较差"));
        mStudentBeen.add(new StudentBean("0x02", R.drawable.img_user, "丘同学", "0", "数学", "仓山", "逻辑理解力比较差较差"));
        mStudentBeen.add(new StudentBean("0x03", R.drawable.img_user, "李同学", "1", "英语", "仓山", "理解理解力比较差理解力比较差力比较差"));
        mStudentBeen.add(new StudentBean("0x04", R.drawable.img_user, "黄同学", "1", "物理", "仓山", "逻辑较差"));
        mStudentBeen.add(new StudentBean("0x05", R.drawable.img_user, "范同学", "1", "化学", "仓山", "理解理解力比较差力比较差"));
        mStudentBeen.add(new StudentBean("0x06", R.drawable.img_user, "林同学", "1", "生物", "仓山", "逻辑较差"));
        mStudentBeen.add(new StudentBean("0x07", R.drawable.img_user, "吴同学", "0", "政治", "仓山", "理解力比较差"));
        mStudentBeen.add(new StudentBean("0x08", R.drawable.img_user, "苏同学", "0", "自然科学", "仓山", "逻辑较差"));

    }

    @OnClick(R.id.iv_student_back)
    public void onClick() {
        finish();
    }
}
