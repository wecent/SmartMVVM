package com.wecent.common.binding.command;

/**
 * @desc: 执行的命令回调, 用于ViewModel与xml之间的数据绑定
 * @author: wecent
 * @date: 2020/5/8
 */
public class BindingCommand<T> {

    private BindingAction0 execute;
    private BindingAction1<T> consumer;
    private BindingFunction0<Boolean> canExecute0;

    public BindingCommand(BindingAction0 execute) {
        this.execute = execute;
    }

    /**
     * @param execute 带泛型参数的命令绑定
     */
    public BindingCommand(BindingAction1<T> execute) {
        this.consumer = execute;
    }

    /**
     * @param execute     触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommand(BindingAction0 execute, BindingFunction0<Boolean> canExecute0) {
        this.execute = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * @param execute     带泛型参数触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommand(BindingAction1<T> execute, BindingFunction0<Boolean> canExecute0) {
        this.consumer = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (execute != null && canExecute0()) {
            execute.call();
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    public void execute(T parameter) {
        if (consumer != null && canExecute0()) {
            consumer.call(parameter);
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        return canExecute0.call();
    }
}
