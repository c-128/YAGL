package com.yagl.fragments;

import com.yagl.gui.YAGUI;
import com.yagl.hooks.StateHook;
import com.yagl.interfaces.ClickListener;
import com.yagl.interfaces.ItemStackSupplier;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonFragment extends Fragment<ButtonFragment> {

    public static Builder builder(YAGUI parent, int x, int y) {
        return new Builder(parent, x, y);
    }

    private final int slot;
    private final ItemStackSupplier item;
    private final ClickListener onClick;

    private ButtonFragment(YAGUI parent, BooleanSupplier visible, Set<StateHook<?>> reRendersOn, int slot, ItemStackSupplier item, ClickListener onClick) {
        super(parent, visible, reRendersOn);
        this.slot = slot;
        this.item = item;
        this.onClick = onClick;
    }

    @Override
    public void onClick(int slot, ClickType type, InventoryConditionResult res) {
        if (slot == this.slot && isVisible()) onClick.onClick(this, type, res);
    }

    @Override
    public Inventory renderItemStacks(Inventory inventory) {
        if (isVisible()) inventory.setItemStack(this.slot, item.get());
        else inventory.setItemStack(this.slot, ItemStack.AIR);
        return inventory;
    }

    public static class Builder extends FragmentBuilder<ButtonFragment, Builder> {

        private final int slot;
        private ItemStackSupplier item;
        private ClickListener onClick;

        private Builder(YAGUI parent, int x, int y) {
            super(parent);
            this.slot = y * 9 + x;
        }

        public Builder item(ItemStackSupplier item) {
            this.item = item;
            return this;
        }

        public Builder onClick(ClickListener onClick) {
            this.onClick = onClick;
            return this;
        }

        public ButtonFragment build() {
            return new ButtonFragment(parent, visible, reRendersOn, slot, item, onClick);
        }
    }
}
