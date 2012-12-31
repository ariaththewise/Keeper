package ariath.minecraft.bukkit.KeeperPlugin;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class Log {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private static PluginDescriptionFile pdFile;
    
    /**
     * Registra mensajes de información en el log.
     * @param message Mensaje.
     */
    public static void info(String message){
        logger.info("[" + pdFile.getName() + "] " + message);
    }
    
    
    /**
     * Inicializa el log.
     * 
     * @param plugin El plugin del que se obtiene el
     * fichero de descripción.
     */
    public static void init(JavaPlugin plugin){
        pdFile = plugin.getDescription();
    }
    
    
    /**
     * Registra mensajes graves en el log.
     * @param message Mensaje.
     */
    public static void severe(String message){
        logger.severe("[" + pdFile.getName() + "] " + message);
    }
    
    
    /**
     * Registra mensajes de aviso en el log.
     * @param message Mensaje.
     */
    public static void warning(String message){
        logger.warning("[" + pdFile.getName() + "] " + message);
    }
}
