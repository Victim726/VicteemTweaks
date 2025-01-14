package gay.plat.victeemtweaks.util;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FileUtil {
    public static File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toString()+"\\victeemtweaks");
}
