package gay.plat.victeemtweaks.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class VicteemTweaksConfig extends MidnightConfig {
    public static final String COOLDOWN_TRICK_NOTIFIER = "cooldown_trick_notifier";

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Mod Enabled")
    public static boolean ctn_isEnabled = true;

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Volume", isSlider = true, min = 0F, max = 1F, precision = 20)
    public static float ctn_volume = 1F;
}
