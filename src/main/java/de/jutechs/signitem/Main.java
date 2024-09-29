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

import java.time.LocalDate;
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

        // Check if the item is already signed
        if (itemStack.get(DataComponentTypes.LORE).toString().contains("Signed")) {
            player.sendMessage(Text.literal("This item is already signed. You can't sign it again!").formatted(Formatting.RED), false);
            return Command.SINGLE_SUCCESS;
        } else {
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            String dateString = currentDate.toString(); // Format as "YYYY-MM-DD"

            // Add the signature and date to the lore
            itemStack.set(DataComponentTypes.LORE, new LoreComponent(List.of(
                    Text.literal("Signed by ")
                            .formatted(Formatting.GOLD)
                            .append(Text.literal(player.getName().getString()).formatted(Formatting.YELLOW))
                            .append(Text.literal(" on ").formatted(Formatting.GOLD))
                            .append(Text.literal(dateString).formatted(Formatting.YELLOW))
            )));

            // Send confirmation to the player
            player.sendMessage(Text.literal("Item signed successfully!").formatted(Formatting.GREEN), false);

            return Command.SINGLE_SUCCESS;
        }
    }
}
