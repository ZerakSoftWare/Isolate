package com.tneciv.zhihudaily.home.view;


import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tneciv.zhihudaily.api.ZhihuApi;
import com.tneciv.zhihudaily.base.BaseListFragment;
import com.tneciv.zhihudaily.home.model.HomeEventEntity;
import com.tneciv.zhihudaily.home.model.HotEntity;
import com.tneciv.zhihudaily.home.presenter.INewsPresenter;
import com.tneciv.zhihudaily.home.presenter.NewsPresenterCompl;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends BaseListFragment implements IHotView {

    INewsPresenter iNewsPresenter;

    List<HotEntity> hotEntities = new ArrayList<>();

    HotRecyclerAdapter recyclerAdapter;


    public HotFragment() {
    }

    @Override
    public void init() {
        Boolean nightMode = config.getBoolean("dayNightMode", false);
        iNewsPresenter = new NewsPresenterCompl(this);
        recyclerAdapter = new HotRecyclerAdapter(getContext(), hotEntities, nightMode);
    }

    @Override
    public void setRecyclerLayout() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void requestUrl() {
        iNewsPresenter.requestUrl(ZhihuApi.NEWS_HOT);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void updateView(HomeEventEntity.HotEntityList hotEntityList) {
        List<HotEntity> hotEntities = hotEntityList.getHotEntities();
        this.hotEntities.clear();
        this.hotEntities.addAll(hotEntities);
        recyclerAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

}
