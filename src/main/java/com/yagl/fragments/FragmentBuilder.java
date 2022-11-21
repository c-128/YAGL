package com.yagl.fragments;

import com.yagl.gui.YAGUI;
import com.yagl.hooks.StateHook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.logging.Handler;

public abstract class FragmentBuilder<T extends Fragment<?>, E extends FragmentBuilder<?, ?>> {

    protected final YAGUI parent;
    protected BooleanSupplier visible;
    protected Set<StateHook<?>> reRendersOn;

    public FragmentBuilder(YAGUI parent) {
        this.parent = parent;
        this.reRendersOn = new HashSet<>();
    }

    public E visible(BooleanSupplier visible) {
        this.visible = visible;
        return (E) this;
    }

    public E reRendersOn(StateHook<?>... hooks) {
        reRendersOn.addAll(Arrays.asList(hooks));
        return (E) this;
    }

    public abstract T build();
}
