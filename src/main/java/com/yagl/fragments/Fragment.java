package com.yagl.fragments;

import com.yagl.gui.YAGUI;
import com.yagl.hooks.StateHook;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;

public abstract class Fragment<T extends Fragment<?>> {

    protected final YAGUI parent;
    private final BooleanSupplier visible;
    protected final Set<StateHook<?>> reRendersOn;

    public Fragment(YAGUI parent, BooleanSupplier visible, Set<StateHook<?>> reRendersOn) {
        this.parent = parent;
        this.visible = visible;
        this.reRendersOn = reRendersOn;
    }

    public boolean isVisible() {
        if (visible == null) return true;
        return visible.getAsBoolean();
    }

    public void onClick(int slot, ClickType type, InventoryConditionResult res) {
    }

    public Inventory renderItemStacks(Inventory inventory) {
        return inventory;
    }

    public Set<StateHook<?>> getReRendersOn() {
        return reRendersOn;
    }
}
