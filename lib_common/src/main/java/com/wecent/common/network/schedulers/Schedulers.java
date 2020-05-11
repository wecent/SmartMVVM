package com.wecent.common.network.schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @desc: 通用的线程
 * @author: wecent
 * @date: 2020/4/12
 */
public class Schedulers {

    private static IoTask IO;

    public static Scheduler io(String name) {
        if(IO == null) {
            IO = new IoTask();
        }
        IO.setThreadName(name);
        return RxJavaPlugins.onIoScheduler(RxJavaPlugins.initIoScheduler(IO));
    }

    public static Scheduler from(@NonNull Executor executor) {
        return io.reactivex.schedulers.Schedulers.from(executor);
    }

    public static Scheduler single() {
        return io.reactivex.schedulers.Schedulers.single();
    }

    public static Scheduler computation() {
        return io.reactivex.schedulers.Schedulers.computation();
    }

    static final class IoTask implements Callable<Scheduler> {
        private static RxThreadFactory factory = new RxThreadFactory("IO");
        private static IoScheduler DEFAULT = new IoScheduler(factory);

        public void setThreadName(String name) {
            factory.setPrefix(name);
        }

        @Override
        public Scheduler call() {
            return DEFAULT;
        }
    }

    public static final class RxThreadFactory extends AtomicLong implements ThreadFactory {

        String prefix;

        final int priority;

        public RxThreadFactory(String prefix) {
            this.prefix = prefix;
            this.priority = Math.max(Thread.MIN_PRIORITY, Math.min(Thread.MAX_PRIORITY, Integer.getInteger("rx2.io-priority", Thread.NORM_PRIORITY)));
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            StringBuilder nameBuilder = new StringBuilder("IO-").append(prefix).append('-').append(incrementAndGet());
            String name = nameBuilder.toString();
            Thread t = new Thread(r, name);
            t.setPriority(priority);
            t.setDaemon(true);
            return t;
        }

        @Override
        public String toString() {
            return "RxThreadFactory[" + prefix + "]";
        }
    }
}
