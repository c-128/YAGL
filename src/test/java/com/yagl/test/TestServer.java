package com.yagl.test;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

public class TestServer {

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        MinecraftServer.getCommandManager().register(new Command("gui") {{
            setDefaultExecutor((sender, context) -> {
                new ExampleGUI(sender.asPlayer()).open();
            });
        }});

        minecraftServer.start("0.0.0.0", 25565);
    }
}
