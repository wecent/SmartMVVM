package com.wecent.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.components.support.RxFragment;
import com.wecent.common.BaseApplication;
import com.wecent.common.network.manager.NetworkState;
import com.wecent.common.network.manager.NetworkStateManager;
import com.wecent.common.widget.loading.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/12
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseView<DB, VM> {

    protected LoadingDialog mLoadingDialog;
    protected AppCompatActivity mActivity;
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    @Nullable
    protected VM mViewModel;
    @Nullable
    protected DB mBinding;
    private int mViewModelId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        NetworkStateManager.getInstance().mNetworkStateCallback.observe(this, this::onNetworkStateChanged);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = getBinding(inflater, container);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        bindLiveData();
        bindData();
        bindEvent();
    }

    private void bindView() {
        mViewModelId = getViewModelId();
        mViewModel = getViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) getFragmentViewModelProvider(this).get(modelClass);
        }
        //关联ViewModel
        mBinding.setVariable(mViewModelId, mViewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
        //注入RxLifecycle生命周期
        mViewModel.injectLifecycleProvider(this);
        //注册RxBus
        mViewModel.injectRxBus();
    }

    protected void bindLiveData() {
        //加载对话框显示
        mViewModel.getBaseLiveData().getShowDialogEvent().observe(this, this::showLoadingDialog);
        //加载对话框消失
        mViewModel.getBaseLiveData().getHideDialogEvent().observe(this, v -> hideLoadingDialog());
        //关闭界面
        mViewModel.getBaseLiveData().getFinishEvent().observe(this, v -> getActivity().finish());
        //关闭上一层
        mViewModel.getBaseLiveData().getOnBackEvent().observe(this, v -> getActivity().onBackPressed());
    }

    @SuppressWarnings("EmptyMethod")
    protected void onNetworkStateChanged(NetworkState state) {
        // 子类可以重写该方法，统一的网络状态通知和处理
    }

    protected void showLoadingDialog(String str) {
        mLoadingDialog = new LoadingDialog.Builder(mActivity)
                .setIconType(LoadingDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(str)
                .create();

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    protected ViewModelProvider getApplicationViewModelProvider() {
        return ((BaseApplication) mActivity.getApplicationContext()).getAppViewModelProvider(mActivity);
    }

    protected ViewModelProvider getActivityViewModelProvider(AppCompatActivity activity) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(activity);
        }
        return mActivityProvider;
    }

    protected ViewModelProvider getFragmentViewModelProvider(Fragment fragment) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(fragment);
        }
        return mFragmentProvider;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding != null) {
            mBinding.unbind();
        }
        if (mViewModel != null) {
            mViewModel.removeRxBus();
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
