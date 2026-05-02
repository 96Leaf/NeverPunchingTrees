package com.blackleaf.neverpunchingtrees;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.blackleaf.neverpunchingtrees.NeverPunchingTrees.MODID;

public class Tags {
    public static class Blocks {

        public static final TagKey<Block> BREAKABLE = tag(MODID, "breakable");
        public static final TagKey<Block> MINEABLE_WITH_SHEARS = tag("minecraft", "mineable/shears");
        public static final TagKey<Block> MINEABLE_WITH_SWORD = tag("minecraft", "mineable/sword");

        private static TagKey<Block> tag(String namespace, String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
    }

    public static class Items {

        public static final TagKey<Item> CANBREAK = tag(MODID, "canbreak");
        public static final TagKey<Item> TOOLS_AXES = tag("c", "tools/axes");
        public static final TagKey<Item> TOOLS_HOES = tag("c", "tools/hoes");
        public static final TagKey<Item> TOOLS_PICKAXES = tag("c", "tools/pickaxes");
        public static final TagKey<Item> TOOLS_SHEARS = tag("c", "tools/shears");
        public static final TagKey<Item> TOOLS_SHOVELS = tag("c", "tools/shovels");
        public static final TagKey<Item> TOOLS_SWORDS = tag("c", "tools/swords");

        private static TagKey<Item> tag(String namespace, String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
    }
}