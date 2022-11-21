package com.yagl.interfaces;

import com.yagl.fragments.Fragment;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;

public interface ClickListener {

    void onClick(Fragment<?> fragment, ClickType type, InventoryConditionResult res);
}
