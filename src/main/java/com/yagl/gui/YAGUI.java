package com.yagl.gui;

import com.yagl.fragments.Fragment;
import com.yagl.hooks.*;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class YAGUI implements Hookable {

    protected final Player player;
    private final Set<OnOpenHook> onOpenHooks;
    private final Set<OnCloseHook> onCloseHooks;
    private final Set<EffectHook> effectHooks;
    private Inventory inventory;

    public YAGUI(Player player) {
        this.player = player;
        this.onOpenHooks = new HashSet<>();
        this.onCloseHooks = new HashSet<>();
        this.effectHooks = new HashSet<>();
        this.inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
    }

    public void open() {
        inventory.addInventoryCondition((player1, slot, type, res) -> {
            res.setCancel(true);
            for (Fragment<?> fragment : getChildren())
                fragment.onClick(slot, type, res);
        });
        inventory = render(inventory);
        player.openInventory(inventory);
        player.eventNode().addListener(EventListener.builder(InventoryCloseEvent.class)
                .expireCount(1)
                .handler(e -> onCloseHooks.forEach(hook -> hook.getOnClose().run())).build());
        onOpenHooks.forEach(hook -> hook.getOnOpen().run());
    }

    private Inventory render(Inventory inv) {
        for (Fragment<?> fragment : getChildren())
            inv = fragment.renderItemStacks(inv);
        return inv;
    }

    private Set<Fragment<?>> getChildren() {
        Set<Fragment<?>> children = new HashSet<>();
        try {
            for (Field field : this.getClass().getFields()) {
                if (!Fragment.class.isAssignableFrom(field.getType())) continue;
                field.setAccessible(true);
                Fragment<?> fragment = (Fragment<?>) field.get(this);
                children.add(fragment);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return children;
    }

    @Override
    public <T> StateHook<T> useState(Class<T> clazz, T value) {
        StateHook<T> hook = new StateHook<>(this, value);

        return hook;
    }

    @Override
    public void invalidateHook(StateHook<?> hook) {
        for (Fragment<?> fragment : getChildren()) {
            if (fragment.getReRendersOn().contains(hook))
                inventory = fragment.renderItemStacks(inventory);
        }
        effectHooks.forEach(h -> h.updateIfNeeded(hook));
    }

    @Override
    public EffectHook useEffect(Runnable onUpdate, StateHook<?>... hooks) {
        EffectHook effectHook = new EffectHook(this, onUpdate, hooks);
        effectHooks.add(effectHook);
        return effectHook;
    }

    @Override
    public OnOpenHook useOnOpen(Runnable onOpen) {
        OnOpenHook onOpenHook = new OnOpenHook(this, onOpen);
        onOpenHooks.add(onOpenHook);
        return onOpenHook;
    }

    @Override
    public OnCloseHook useOnClose(Runnable onClose) {
        OnCloseHook onOpenHook = new OnCloseHook(this, onClose);
        onCloseHooks.add(onOpenHook);
        return onOpenHook;
    }

    protected void setTitle(Component title) {
        inventory.setTitle(title);
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
