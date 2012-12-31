package ariath.minecraft.bukkit.KeeperPlugin;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;


/**
 * Clase para gestionar la configuración del plugin
 * @author Ariath.
 */
public class Config {
    public static String database = "";
    public static String mysqlHost = "";
    public static String mysqlUser = "";
    public static String mysqlPassword = "";
    public static String mysqlPort = "";
    public static boolean showMySQLErrors;
    
    private static Keeper plugin;
    private static FileConfiguration config;
    private static HashMap<String, Object> defaultConfig;

    
    /**
     * Carga la configuración del pluginn.
     * @param pluginInstance Instancia del plugin.
     */
    public static void load(Keeper pluginInstance){
        plugin = pluginInstance;
        config = plugin.getConfig();
        defaultConfig = new HashMap<String, Object>();
        
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }
        
        defaultConfig.put("MySQL Host", "localhost");
        defaultConfig.put("MySQL Port", "3306");
        defaultConfig.put("MySQL User", "root");
        defaultConfig.put("MySQL Password", "password");
        defaultConfig.put("Database", "minecraft");
        defaultConfig.put("Show MySQL errors", false);
        
        for (Entry<String, Object> entry : defaultConfig.entrySet()){
            if (!config.contains(entry.getKey())){
                config.set(entry.getKey(), entry.getValue());
            }
        }
        
        save();
        
        database = config.getString("Database");
        mysqlHost = config.getString("MySQL Host");
        mysqlPort = config.getString("MySQL Port");
        mysqlUser = config.getString("MySQL User");
        mysqlPassword = config.getString("MySQL Password");
        showMySQLErrors = config.getBoolean("Show MySQL errors");
    }
    
    
    /**
     * Recarga la configuración.
     */
    public static void reload(){
        plugin.reloadConfig();
    }
    
    
    /**
     * Actualiza la configuración en disco.
     */
    public static void save(){
        plugin.saveConfig();
    }
    
    
    /**
     * Establece el valor de un campo.
     * Un valor null elimina el campo.
     * 
     * @param field Campo
     * @param value Valor
     */
    public static void set(String field, Object value){
        config.set(field, value);
    }
}
