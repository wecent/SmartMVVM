package com.wecent.common.binding.command;


import io.reactivex.functions.Function;

/**
 * @desc: 执行的命令事件转换
 * @author: wecent
 * @date: 2020/5/8
 */
public class ResponseCommand<T, R> {

    private BindingFunction0<R> execute;
    private Function<T, R> function;
    private BindingFunction0<Boolean> canExecute;

    /**
     * like {@link BindingCommand},but ResponseCommand can return result when command has executed!
     *
     * @param execute function to execute when event occur.
     */
    public ResponseCommand(BindingFunction0<R> execute) {
        this.execute = execute;
    }


    public ResponseCommand(Function<T, R> execute) {
        this.function = execute;
    }


    public ResponseCommand(BindingFunction0<R> execute, BindingFunction0<Boolean> canExecute) {
        this.execute = execute;
        this.canExecute = canExecute;
    }


    public ResponseCommand(Function<T, R> execute, BindingFunction0<Boolean> canExecute) {
        this.function = execute;
        this.canExecute = canExecute;
    }


    public R execute() {
        if (execute != null && canExecute()) {
            return execute.call();
        }
        return null;
    }

    private boolean canExecute() {
        if (canExecute == null) {
            return true;
        }
        return canExecute.call();
    }


    public R execute(T parameter) throws Exception {
        if (function != null && canExecute()) {
            return function.apply(parameter);
        }
        return null;
    }
}
