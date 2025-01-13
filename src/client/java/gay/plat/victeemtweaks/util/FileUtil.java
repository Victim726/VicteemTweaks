package gay.plat.victeemtweaks.util;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toString()+"\\victeemtweaks");
}
