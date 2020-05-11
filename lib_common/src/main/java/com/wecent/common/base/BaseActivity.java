package com.wecent.common.base;

import android.os.Bundle;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.wecent.common.BaseApplication;
import com.wecent.common.network.manager.NetworkStateManager;
import com.wecent.common.widget.loading.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

/**
 * @desc: BaseActivity
 * @author: wecent
 * @date: 2020/4/12
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView<DB, VM> {

    protected LoadingDialog mLoadingDialog;
    private ViewModelProvider mActivityProvider;
    @Nullable
    protected VM mViewModel;
    @Nullable
    protected DB mBinding;
    private int mViewModelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        bindLiveData();
        getLifecycle().addObserver(NetworkStateManager.getInstance());
        bindData();
        bindEvent();
    }

    private void bindView() {
        mBinding = getBinding(null, null);
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
            mViewModel = (VM) getActivityViewModelProvider(this).get(modelClass);
        }
        //关联ViewModel
        mBinding.setVariable(mViewModelId, mViewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
        //注入RxLifecycle生命周期
        mViewModel.injectLifecycleProvider(this);
    }

    protected void bindLiveData() {
        //加载对话框显示
        mViewModel.getBaseLiveData().getShowDialogEvent().observe(this, this::showLoadingDialog);
        //加载对话框消失
        mViewModel.getBaseLiveData().getHideDialogEvent().observe(this, v -> hideLoadingDialog());
        //关闭界面
        mViewModel.getBaseLiveData().getFinishEvent().observe(this, v -> finish());
        //关闭上一层
        mViewModel.getBaseLiveData().getOnBackEvent().observe(this, v -> onBackPressed());
    }

    protected void showLoadingDialog(String str) {
        mLoadingDialog = new LoadingDialog.Builder(this)
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
        return ((BaseApplication) getApplicationContext()).getAppViewModelProvider(this);
    }

    protected ViewModelProvider getActivityViewModelProvider(AppCompatActivity activity) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(activity);
        }
        return mActivityProvider;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}