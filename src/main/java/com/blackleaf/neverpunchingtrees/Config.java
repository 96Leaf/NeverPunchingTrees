package com.blackleaf.neverpunchingtrees;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue REQUIRE_PREFERRED_TOOL = BUILDER
            .comment("if ture, you have to use preferred tool to harvest blocks. (default: false)")
            .define("require preferred tool", false);

    private static final ModConfigSpec.BooleanValue ALLWAYS_ALLOW_INSTA_BREAK = BUILDER
            .comment("if ture, you can allways break insta-breakable blocks. (default: true)")
            .define("allways allow insta-break", true);

    private static final ModConfigSpec.BooleanValue FORCE_IN_CREATIVE = BUILDER
            .comment("if ture, you have to use tool to break blocks in creative mode. (default: false)")
            .define("force in creative", false);

    static final ModConfigSpec SPEC = BUILDER.build();

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
