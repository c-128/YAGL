package com.yagl.interfaces;

import com.yagl.fragments.Fragment;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;

public interface ToggleClickListener {

    /**
     * @param fragment The fragment, if you if it is enabled, it will return what it is BEFORE the click
     * @return Return true if you want the event to be cancelled
     */
    boolean onClick(Fragment<?> fragment, ClickType type, InventoryConditionResult res);
}
