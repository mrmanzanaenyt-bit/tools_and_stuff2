package com.toolsandstuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ToolsAndStuff implements ModInitializer {
    public static final String MOD_ID = "tools_and_stuff";
    /** Bloques que se pueden escalar antes de que se rompa. */
    public static final int MAX_BLOCKS = 900;

    public static Item PIOLET;

    @Override
    public void onInitialize() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(MOD_ID, "piolet"));
        PIOLET = Registry.register(BuiltInRegistries.ITEM, key,
                new Item(new Item.Properties().setId(key).durability(MAX_BLOCKS)));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> output.accept(PIOLET));

        ServerTickEvents.END_SERVER_TICK.register(server ->
                server.getPlayerList().getPlayers().forEach(ClimbDurability::tick));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                ClimbDurability.forget(handler.getPlayer().getUUID()));
    }
}
