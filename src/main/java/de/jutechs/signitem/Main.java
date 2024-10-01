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
import java.util.Objects;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sign")
                    .executes(context -> signItem(context))
            );
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("unsign")
                    .executes(context -> unSignItem(context))
            );
        });
    }

    private int signItem(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        var player = source.getPlayer();
        assert player != null;
        ItemStack itemStack = player.getMainHandStack();

        // Ensure the player is holding an item
        if (itemStack.isEmpty()) {
            player.sendMessage(Text.literal("You must hold an item to sign it!").formatted(Formatting.RED), false);
            return SINGLE_SUCCESS;
        }

        // Check if the item is already signed
        if (Objects.requireNonNull(itemStack.get(DataComponentTypes.LORE)).toString().contains("Signed") && !itemStack.get(DataComponentTypes.LORE).toString().contains(player.getName().toString())) {
            player.sendMessage(Text.literal("This item is already signed. You can't sign it again!").formatted(Formatting.RED), false);
            return SINGLE_SUCCESS;
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

            return SINGLE_SUCCESS;
        }
    }
    private int unSignItem(CommandContext<ServerCommandSource> context){
        ServerCommandSource source = context.getSource();
        var player = source.getPlayer();
        assert player != null;
        ItemStack itemStack = player.getMainHandStack();

        // Ensure the player is holding an item
        if (itemStack.isEmpty()) {
            player.sendMessage(Text.literal("You must hold an item to unsign it!").formatted(Formatting.RED), false);
            return SINGLE_SUCCESS;
        }

        // Check if the item is already signed
        if (!Objects.requireNonNull(itemStack.get(DataComponentTypes.LORE)).toString().contains("Signed")) {
            player.sendMessage(Text.literal("This item is not signed!").formatted(Formatting.RED), false);
            return SINGLE_SUCCESS;
        } else if (Objects.requireNonNull(itemStack.get(DataComponentTypes.LORE)).toString().contains(player.getName().toString())) {
            itemStack.set(DataComponentTypes.LORE, new LoreComponent(List.of(
                    Text.literal("")
            )));

            // Send confirmation to the player
            player.sendMessage(Text.literal("Item unsigned successfully!").formatted(Formatting.GREEN), false);
            return SINGLE_SUCCESS;
        }
        player.sendMessage(Text.literal("This item is signed by another player!").formatted(Formatting.RED), false);


        return SINGLE_SUCCESS;
    }
}
