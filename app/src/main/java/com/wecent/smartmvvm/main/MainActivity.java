package com.wecent.smartmvvm.main;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wecent.common.base.BaseActivity;
import com.wecent.common.utils.AppUtils;
import com.wecent.common.utils.FragmentUtils;
import com.wecent.common.utils.StatusBarUtils;
import com.wecent.common.utils.ToastUtils;
import com.wecent.smartmvvm.BR;
import com.wecent.smartmvvm.R;
import com.wecent.smartmvvm.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * @desc: MainActivity
 * @author: wecent
 * @date: 2020/4/17
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final int EXIT_INTERVAL_TIME = 2000;
    private long mExitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "商城", "消息", "我的"};
    private int[] mNormalIcon = {
            R.drawable.ic_main_home_normal, R.drawable.ic_main_mall_normal,
            R.drawable.ic_main_news_normal, R.drawable.ic_main_mine_normal};
    private int[] mSelectedIcon = {
            R.drawable.ic_main_home_selected, R.drawable.ic_main_mall_selected,
            R.drawable.ic_main_news_selected, R.drawable.ic_main_mine_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public ActivityMainBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public MainViewModel getViewModel() {
        return getActivityViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public int getViewModelId() {
        return BR.ViewModel;
    }

    @Override
    public void bindData() {
        StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.config_color_white));
        StatusBarUtils.setStatusBarLightMode(this, true);
        mFragments.add(new HomeFragment());
        mFragments.add(new MallFragment());
        mFragments.add(new NewsFragment());
        mFragments.add(new MineFragment());
        FragmentUtils.add(getSupportFragmentManager(), mFragments, mBinding.flMain.getId(), 0);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MainTabEntity(mTitles[i], mSelectedIcon[i], mNormalIcon[i]));
        }
        mBinding.tbMain.setTabData(mTabEntities);
        mBinding.tbMain.setCurrentTab(0);
        mBinding.tbMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                FragmentUtils.showHide(mFragments.get(position), mFragments);
                mBinding.tbMain.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_INTERVAL_TIME) {
                ToastUtils.showShort("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                AppUtils.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
