package com.qwh.findtutor.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseFragment;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.CourseMode;
import com.qwh.findtutor.bean.TestBean;
import com.qwh.findtutor.bean.TutorBean;
import com.qwh.findtutor.ui.activity.DropMenu.CategoryActivity;
import com.qwh.findtutor.ui.activity.SearchActivity;
import com.qwh.findtutor.ui.activity.StudentActivity;
import com.qwh.findtutor.ui.activity.TutorDetailActivity;
import com.qwh.findtutor.utils.OkHttpUtils;
import com.qwh.findtutor.utils.SpacesItemDecoration;
import com.qwh.findtutor.utils.Utils;
import com.qwh.findtutor.view.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment {
    public static String KEY_TYPE = "home_type";
    public static String VALUE_TYPE_TEACHER = "type_teacher";
    public static String VALUE_TYPE_COURSE = "type_course";

    @Bind(R.id.ibtn_home_search)
    ImageButton ibtnSearch;
    @Bind(R.id.tv_home_adress)
    TextView tvAdress;
    @Bind(R.id.banner_home)
    Banner banner;
    @Bind(R.id.id_main_recyclerview)
    RecyclerView idMainRecyclerview;
    @Bind(R.id.rv_home_hot_course)
    RecyclerView rvHotCourse;
    @Bind(R.id.rv_home_course_label)
    RecyclerView rvHotLabel;
    private List<TutorBean> mData;
    private CommonAdapter<TutorBean> mAdapter;
    private CommonAdapter<String> mHotAdapter;
    private CommonAdapter<String> mLabelAdapter;

    String[] images = new String[]{
            "http://img.zcool.cn/community/01ae5656e1427f6ac72531cb72bac5.jpg",
            "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
            "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
            "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
            "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
            "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
            "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};

    private OnHeadlineSelectedListener mCallback;
    // 用来存放fragment的Activtiy必须实现这个接口
    public interface OnHeadlineSelectedListener {
        public void onFragmentSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    protected void initViews() {
        //填充轮播图
        initBanner();
        //填充热门科目数据
        initRvHot();
        //填充科目标签
        initRvLabel();
        //填充推荐表数据
        initRvRec();

    }

    private void initBanner() {
        banner.setBannerStyle(Banner.NUM_INDICATOR_TITLE);
        banner.setIndicatorGravity(Banner.CENTER);
        banner.setDelayTime(5000);//设置轮播间隔时间
        banner.setImages(images);//可以选择设置图片网址，或者资源文件，默认用Glide加载
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                toast("click:" + position);
            }
        });
    }

    private void initRvHot() {
        final List<String> mHotList = new CourseMode().getFilterHot();
        rvHotCourse.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mHotAdapter = new CommonAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mHotList) {
            @Override
            public void setData(ViewHolder holder, String s) {
                holder.setText(android.R.id.text1, s);
            }
        };
        mHotAdapter.notifyDataSetChanged();
        rvHotCourse.setAdapter(mHotAdapter);
        mHotAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
//                startActivity(new Intent(getActivity(), TutorDetailActivity.class)
//                        .putExtra("teacher_name", mHotList.get(position).getName()));
                startActivity(new Intent(getActivity(), CategoryActivity.class)
                        .putExtra(KEY_TYPE, VALUE_TYPE_COURSE)
                        .putExtra("positionTitle", mHotList.get(position)));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    private void initRvLabel() {
        final List<String> mLabel = new CourseMode().getFilterDesc();
        rvHotLabel.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mLabelAdapter = new CommonAdapter<String>(getActivity(), R.layout.item_home_label, mLabel) {
            @Override
            public void setData(ViewHolder holder, String s) {
                holder.setText(R.id.tv_item_home_label_title, s);
                holder.setImageResource(R.id.iv_item_home_label, R.mipmap.ic_launcher);
            }
        };
        mLabelAdapter.notifyDataSetChanged();
        rvHotLabel.setAdapter(mLabelAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(3);
        rvHotLabel.addItemDecoration(decoration);
        mLabelAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
//                startActivity(new Intent(getActivity(), TutorDetailActivity.class)
//                        .putExtra("teacher_name", mHotList.get(position).getName()));
                final List<String> list = new CourseMode().getFilterType().get(position).getChild();
                if (list == null || list.size() == 0) {
                    startActivity(new Intent(getActivity(), CategoryActivity.class)
                            .putExtra(KEY_TYPE, VALUE_TYPE_COURSE)
                            .putExtra("positionTitle", mLabel.get(position)));
                    return;
                }
                showDialog(mLabel.get(position), list);
                Log.i("HomeFragment", "onClick: " + mLabel.get(position));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    private void showDialog(final String s, final List<String> list) {

        new AlertDialog.Builder(getActivity())
                .setTitle("选择科目")
                .setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("HomeFragment", "onClick: " + list.get(i));
                        startActivity(new Intent(getActivity(), CategoryActivity.class)
                                .putExtra(KEY_TYPE, VALUE_TYPE_COURSE)
                                .putExtra("positionTitle", s + list.get(i)));
                    }
                })
                .show();
    }

    private void initRvRec() {
        idMainRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mData == null || mData.size() < 1) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter = new CommonAdapter<TutorBean>(getActivity(), R.layout.item_main_rv, mData) {
            @Override
            public void setData(ViewHolder holder, TutorBean TutorBean) {
                holder.setImageResource(R.id.item_main_rv_rv_icon, TutorBean.getImg());
                holder.setText(R.id.item_main_rv_rv_name, TutorBean.getName());
                holder.setText(R.id.item_main_rv_rv_type, "教授课程: " + TutorBean.getType());
                holder.setText(R.id.item_main_rv_rv_summary, TutorBean.getSummary());
            }
        };
        mAdapter.notifyDataSetChanged();
        idMainRecyclerview.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        idMainRecyclerview.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(getActivity(), TutorDetailActivity.class)
                        .putExtra("teacher_name", mData.get(position).getName()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.tv_home_adress, R.id.rl_home_teacher, R.id.rl_home_student, R.id.ibtn_home_search})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ibtn_home_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_home_adress:
//                startActivity(new Intent(getActivity(), CategoryActivity.class));
                Utils.setChooseCity(getActivity(), tvAdress);
                break;
            case R.id.rl_home_teacher:

                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(KEY_TYPE, VALUE_TYPE_TEACHER);
                break;
            case R.id.rl_home_student:
                intent = new Intent(getActivity(), StudentActivity.class);
//                mCallback.onFragmentSelected(1);
                break;
        }
        if (null != intent)
            startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        List<OkHttpUtils.Param> params = new ArrayList<>();
//        params.add(new OkHttpUtils.Param("key", "value"));
        OkHttpUtils.get("http://120.76.239.100/TJClan/TJClan/web/user/hall/get", new call());
        mData = new ArrayList<>();
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good一讲每周一讲每周一讲每周一讲每周一讲每周一讲每周一讲每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good一讲每周一讲每周一讲每周一讲每周一讲每周一讲每周一讲每周一讲"));
        mData.add(new TutorBean(R.drawable.icon_detail_bg, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.drawable.icon_detail_bg, "张老师", "语文", "每周一讲"));
    }

    private class call extends OkHttpUtils.ResultCallback<TestBean> {

        @Override
        public void onSuccess(TestBean response) {
            Log.i("okhttp", "onSuccess: " + response.getSummary());
        }

        @Override
        public void onFailure(Exception e) {
            Log.i("okhttp", "onFailure: " + e.toString());
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        banner.isAutoPlay(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.isAutoPlay(false);
    }
}