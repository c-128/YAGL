package com.yagl.hooks;

import com.yagl.gui.YAGUI;

public class OnCloseHook extends Hook {

    private final Runnable onClose;

    public OnCloseHook(YAGUI gui, Runnable onOpen) {
        super(gui);
        this.onClose = onOpen;
    }

    public Runnable getOnClose() {
        return onClose;
    }
}
