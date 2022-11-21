package com.yagl.test;

import com.yagl.fragments.ButtonFragment;
import com.yagl.fragments.ToggleButtonFragment;
import com.yagl.gui.YAGUI;
import com.yagl.hooks.EffectHook;
import com.yagl.hooks.OnCloseHook;
import com.yagl.hooks.OnOpenHook;
import com.yagl.hooks.StateHook;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class ExampleGUI extends YAGUI {

    public StateHook<Integer> example = useState(Integer.class, 0);
    public StateHook<Boolean> visible = useState(Boolean.class, true);

    public ButtonFragment button = ButtonFragment.builder(this, 0, 0)
            .item(() -> ItemStack.builder(Material.GOLD_BLOCK)
                    .displayName(Component.text(example.get()))
                    .build())
            .onClick((fragment, type, res) -> example.set(example.get() + 1))
            .visible(() -> visible.get())
            .reRendersOn(example, visible)
            .build();

    public ButtonFragment toggleVisible = ButtonFragment.builder(this, 1, 0)
            .item(() -> ItemStack.of(Material.BARRIER))
            .onClick((fragment, type, res) -> visible.set(!visible.get()))
            .build();

    public ToggleButtonFragment toggleButton = ToggleButtonFragment.builder(this, 2, 0)
            .item(() -> ItemStack.of(Material.GREEN_WOOL), () -> ItemStack.of(Material.RED_WOOL))
            .onClick((fragment, type, res) -> {
                ToggleButtonFragment button = (ToggleButtonFragment) fragment;
                player.sendMessage("Toggled: " + button.isEnabled());
                return false;
            }).build();

    public EffectHook onExample = useEffect(() -> {
        player.sendMessage("New example value: " + example.get());
    }, example);

    public OnOpenHook onOpen = useOnOpen(() -> {
        player.sendMessage("Opened GUI");
    });

    public OnCloseHook onClose = useOnClose(() -> {
        player.sendMessage("Closed GUI.");
    });


    public ExampleGUI(Player player) {
        super(player);
    }
}