package com.blackleaf.neverpunchingtrees;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import static com.blackleaf.neverpunchingtrees.Config.*;
import static com.blackleaf.neverpunchingtrees.Tags.Blocks.BREAKABLE;
import static com.blackleaf.neverpunchingtrees.Tags.Items.CANBREAK;


@Mod(NeverPunchingTrees.MODID)
public class NeverPunchingTrees {
    public static final String MODID = "neverpunchingtrees";

    public NeverPunchingTrees(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(Config::onLoad);
    }

    @EventBusSubscriber(modid = MODID)
    public static class PreventPunch {
        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            if (requirePreferredTool) {
                TagCache.refreshCache();
            }
        }
        @SubscribeEvent
        public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockPos pos = event.getPos();

            ItemStack stack = player.getMainHandItem();
            BlockState state = level.getBlockState(pos);

            if (allwaysAllowInstaBreak && state.getDestroySpeed(level, pos) == 0.0f) {
                return;
            }

            if (!forceInCreative && player.getAbilities().instabuild) {
                return;
            }

            boolean needsCorrectTool = false;

            if (requirePreferredTool){
                boolean hasMineableTag = false;
                boolean hasCorrectTool = false;

                for (var entry : TagCache.getPreferredMap().entrySet()) {
                    TagKey<Block> mineableTag = entry.getKey();

                    if (state.is(mineableTag)) {
                        hasMineableTag = true;
                        TagKey<Item> requiredToolTag = entry.getValue();

                        if (stack.is(requiredToolTag)) {
                            hasCorrectTool = true;
                            break;
                        }
                    }
                }

                needsCorrectTool = hasMineableTag && !hasCorrectTool;
            }

            if(!state.is(BREAKABLE) && (!stack.is(CANBREAK) || needsCorrectTool)) {
                event.setCanceled(true);
            }
        }
    }
}
