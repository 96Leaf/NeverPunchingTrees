package com.blackleaf.neverpunchingtrees;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.blackleaf.neverpunchingtrees.Config.*;
import static com.blackleaf.neverpunchingtrees.Tags.Blocks.BREAKABLE;
import static com.blackleaf.neverpunchingtrees.Tags.Items.CANBREAK;


@Mod(NeverPunchingTrees.MODID)
public class NeverPunchingTrees {
    public static final String MODID = "neverpunchingtrees";

    public NeverPunchingTrees(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class PreventPunch {
        @SubscribeEvent
        public static void onTagsUpdated(TagsUpdatedEvent event) {
            if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED ||
                    event.getRegistryAccess() != null) {
                if (requirePreferredTool) {
                    TagCache.refreshCache();
                }
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

            if(!state.is(BREAKABLE) && !stack.is(CANBREAK) || needsCorrectTool) {
                event.setCanceled(true);
            }
        }
    }
}
