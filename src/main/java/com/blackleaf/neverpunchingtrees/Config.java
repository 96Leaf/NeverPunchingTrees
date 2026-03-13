package com.blackleaf.neverpunchingtrees;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = NeverPunchingTrees.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue REQUIRE_PREFERRED_TOOL = BUILDER
            .comment("if ture, you have to use preferred tool to harvest blocks. (default: false)")
            .define("require preferred tool", false);

    private static final ForgeConfigSpec.BooleanValue ALLWAYS_ALLOW_INSTA_BREAK = BUILDER
            .comment("if ture, you can allways break insta-breakable blocks. (default: true)")
            .define("allways allow insta-break", true);

    private static final ForgeConfigSpec.BooleanValue FORCE_IN_CREATIVE = BUILDER
            .comment("if ture, you have to use tool to break blocks in creative mode. (default: false)")
            .define("force in creative", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean requirePreferredTool;
    public static boolean allwaysAllowInstaBreak;
    public static boolean forceInCreative;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        requirePreferredTool = REQUIRE_PREFERRED_TOOL.get();
        allwaysAllowInstaBreak = ALLWAYS_ALLOW_INSTA_BREAK.get();
        forceInCreative = FORCE_IN_CREATIVE.get();
    }
}
