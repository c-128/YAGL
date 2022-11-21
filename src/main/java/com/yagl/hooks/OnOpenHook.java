package com.yagl.hooks;

import com.yagl.gui.YAGUI;

public class OnOpenHook extends Hook {

    private final Runnable onOpen;

    public OnOpenHook(YAGUI gui, Runnable onOpen) {
        super(gui);
        this.onOpen = onOpen;
    }

    public Runnable getOnOpen() {
        return onOpen;
    }
}
