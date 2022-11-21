package com.yagl.hooks;

import com.yagl.fragments.Fragment;
import com.yagl.gui.YAGUI;

public class StateHook<T> extends Hook {

    private T value;

    public StateHook(YAGUI gui, T value) {
        super(gui);
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        gui.invalidateHook(this);
    }
}
