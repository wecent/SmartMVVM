package com.wecent.common.network.response;

import com.wecent.common.network.exception.BaseException;
import com.wecent.common.network.exception.HttpException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/12
 */
public class ResponseTransformer {

    private static final int CODE_REQUEST_SUCCESS = 200;

    public static <T> ObservableTransformer<BaseResponse<T>, T> convertResponse() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(HttpException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> response) throws Exception {
            int code = response.code;
            String message = response.msg;
            if (code == CODE_REQUEST_SUCCESS) {
                return Observable.just(response.data);
            } else {
                return Observable.error(new BaseException(code, message));
            }
        }
    }
}