package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.DayDetailDesignBean;
import com.tec.travelagency.home.entity.PathSelfDetailBean;
import com.tec.travelagency.home.entity.PathSelfOrderBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/6 14:58
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class PathDetailFragment extends BaseFragment {


    @BindView(R.id.day_rv)
    RecyclerView dayRv;
    private RVBaseAdapter<DayDetailDesignBean> mBaseAdapter;

    PathSelfOrderBean mPathSelfOrderBean;


    public PathDetailFragment(PathSelfOrderBean pathSelfOrderBean) {
        mPathSelfOrderBean = pathSelfOrderBean;
    }


    /**
     * 更新界面，由寄主Activity 发出
     */
    @Subscribe
    public void changeUI(List<DayDetailDesignBean> list) {

        mBaseAdapter = new RVBaseAdapter<DayDetailDesignBean>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        mBaseAdapter.setDataByRemove(list);
        dayRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dayRv.setAdapter(mBaseAdapter);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_path_detail_layout;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
