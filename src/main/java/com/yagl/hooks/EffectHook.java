package com.yagl.hooks;

import com.yagl.gui.YAGUI;

public class EffectHook extends Hook {

    private final Runnable onUpdate;
    private final StateHook<?>[] states;

    public EffectHook(YAGUI gui, Runnable onUpdate, StateHook<?>... states) {
        super(gui);
        this.onUpdate = onUpdate;
        this.states = states;
    }

    public void updateIfNeeded(StateHook<?> hook) {
        boolean contains = false;
        for (StateHook<?> hookToCompare : states)
            if (hookToCompare == hook) {
                contains = true;
                break;
            }
        if (contains) onUpdate.run();
    }

    public StateHook<?>[] getStates() {
        return states;
    }

    public Runnable getOnUpdate() {
        return onUpdate;
    }
}
