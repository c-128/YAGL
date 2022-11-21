package com.yagl.fragments;

import com.yagl.gui.YAGUI;
import com.yagl.hooks.StateHook;
import com.yagl.interfaces.ClickListener;
import com.yagl.interfaces.ItemStackSupplier;
import com.yagl.interfaces.ToggleClickListener;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;
import net.minestom.server.item.ItemStack;

import java.util.Set;
import java.util.function.BooleanSupplier;

public class ToggleButtonFragment extends Fragment<ToggleButtonFragment> {

    public static Builder builder(YAGUI parent, int x, int y) {
        return new Builder(parent, x, y);
    }

    private final int slot;
    private final ItemStackSupplier itemEnabled, itemDisabled;
    private final ToggleClickListener onClick;
    private boolean enabled;

    private ToggleButtonFragment(YAGUI parent, BooleanSupplier visible, Set<StateHook<?>> reRendersOn, int slot, ItemStackSupplier itemEnabled, ItemStackSupplier itemDisabled, boolean enabled, ToggleClickListener onClick) {
        super(parent, visible, reRendersOn);
        this.slot = slot;
        this.itemEnabled = itemEnabled;
        this.itemDisabled = itemDisabled;
        this.onClick = onClick;
        this.enabled = enabled;
    }

    @Override
    public void onClick(int slot, ClickType type, InventoryConditionResult res) {
        if (slot == this.slot && isVisible()) {
            if (onClick != null && onClick.onClick(this, type, res)) return;
            enabled = !enabled;
            renderItemStacks(parent.getInventory());
        }
    }

    @Override
    public Inventory renderItemStacks(Inventory inventory) {
        if (isVisible()) {
            if (enabled) inventory.setItemStack(this.slot, itemEnabled.get());
            else inventory.setItemStack(this.slot, itemDisabled.get());
        } else inventory.setItemStack(this.slot, ItemStack.AIR);
        return inventory;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static class Builder extends FragmentBuilder<ToggleButtonFragment, Builder> {

        private final int slot;
        private ItemStackSupplier itemEnabled, itemDisabled;
        private ToggleClickListener onClick;
        private boolean enabled;

        private Builder(YAGUI parent, int x, int y) {
            super(parent);
            this.slot = y * 9 + x;
            this.enabled = true;
        }

        public Builder item(ItemStackSupplier itemEnabled, ItemStackSupplier itemDisabled) {
            this.itemEnabled = itemEnabled;
            this.itemDisabled = itemDisabled;
            return this;
        }

        public Builder itemEnabled(ItemStackSupplier itemEnabled) {
            this.itemEnabled = itemEnabled;
            return this;
        }

        public Builder itemDisabled(ItemStackSupplier itemDisabled) {
            this.itemDisabled = itemDisabled;
            return this;
        }

        public Builder onClick(ToggleClickListener onClick) {
            this.onClick = onClick;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public ToggleButtonFragment build() {
            return new ToggleButtonFragment(parent, visible, reRendersOn, slot, itemEnabled, itemDisabled, enabled, onClick);
        }
    }
}
