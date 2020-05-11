package com.wecent.common.widget.floating;

import java.util.List;

/**
 * @desc: 悬浮窗管理类
 * @author wecent
 * @since 202/4/21
 */
public class FloatingManager {

    private FloatingManager() {

    }

    private static final FloatingManager instance = new FloatingManager();

    public static FloatingManager getInstance() {
        return instance;
    }

    public void showFloatingView(List<FloatingData> data) {
        FloatingView.get().create(data);
        FloatingView.get().listener(new MagnetViewListener() {
            @Override
            public void onClose(MagnetFloatingView magnetView) {
                FloatingView.get().close();
            }

            @Override
            public void onCancel(MagnetFloatingView magnetView) {
                FloatingView.get().close();
            }

            @Override
            public void onConfirm(MagnetFloatingView magnetView) {
                FloatingView.get().close();
            }
        });

    }

    public void showFloatingView(FloatingData data) {
        if (FloatingView.get().isShow()) {
            FloatingView.get().refresh(data);
        } else {
            FloatingView.get().attach();
            FloatingView.get().create(data);
            FloatingView.get().listener(new MagnetViewListener() {
                @Override
                public void onClose(MagnetFloatingView magnetView) {
                    FloatingView.get().close();
                }

                @Override
                public void onCancel(MagnetFloatingView magnetView) {
                    FloatingView.get().close();
                }

                @Override
                public void onConfirm(MagnetFloatingView magnetView) {
                    FloatingView.get().close();
                }
            });
        }
    }
}
