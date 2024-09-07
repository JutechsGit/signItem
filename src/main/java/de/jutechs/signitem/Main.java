package de.jutechs.signitem;

import net.fabricmc.api.ModInitializer;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sign")
                    .executes(context -> signItem(context))
            );
        });
    }
    private int signItem(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        var player = source.getPlayer();
        ItemStack itemStack = player.getMainHandStack();

        // Ensure the player is holding an item
        if (itemStack.isEmpty()) {
            player.sendMessage(Text.literal("You must hold an item to sign it!").formatted(Formatting.RED), false);
            return Command.SINGLE_SUCCESS;
        }

        // Access the custom lore component

        // LoreComponent lore = itemStack.get(DataComponentTypes.LORE);
        // lore.with(Text.literal("Signed by " + source.getPlayer().getName().getString()));

        //Insert lore into item
        if (itemStack.get(DataComponentTypes.LORE).toString().contains("Signed")) {
            player.sendMessage(Text.literal("This Item is already signed. You can't sign it again!").formatted(Formatting.RED), false);
            return Command.SINGLE_SUCCESS;
        } else {
            itemStack.set(DataComponentTypes.LORE, new LoreComponent(List.of(Text.literal("Signed by " + source.getPlayer().getName().getString()).formatted(Formatting.GOLD))));

            // Send confirmation to the player
            player.sendMessage(Text.literal("Item signed successfully!").formatted(Formatting.GREEN), false);

            return Command.SINGLE_SUCCESS;
        }
    }

}
