package ariath.minecraft.bukkit.KeeperPlugin;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import ariath.minecraft.bukkit.KeeperPlugin.Commands.*;



/**
 * Plugin Keeper
 * Lleva un registro de entradas y salidas de usuarios al servidor.
 * 
 * @author Ariath. 
 */
public class Keeper extends JavaPlugin{    
    public static final Logger logger = Logger.getLogger("Minecraft");
    public static PluginDescriptionFile pdFile;
    
	private PlayerLoginListener playerLoginListener;
	
    public File pluginDataFolder;
    
    public final static String LOGIN_TABLE = "keeper_login";
    public final static String LOGOFF_TABLE = "keeper_logoff";
	   
    
	
	@Override
	public void onDisable() {
	    Log.info("Desactivado.");
	}
	
	
	@Override
	public void onEnable() {
	    pdFile = this.getDescription();
	    
	    Log.init(this);
        Config.load(this);
        
        SQL.init(Config.mysqlHost, Config.mysqlPort, Config.mysqlUser, Config.mysqlPassword, Config.database); 
        
        if(SQL.canConnectToMySQL()){
            Queries.createNeededTables();
    
    		playerLoginListener = new PlayerLoginListener();
    		
    		Bukkit.getServer().getPluginManager().registerEvents(playerLoginListener, this);
    		
    		this.getCommand("keeper").setExecutor(new KeeperCommand());

    		Log.info("Activado.");
    		
        }else{
            Log.warning("Hubo problemas con MySQL. No se registraran los eventos de jugadores.");            
        }
	}
    
		
    /**
     * Comprueba si un jugador tiene el permiso indicado.
     * 
     * Si el plugin PermissionsEx no está habilitado,
     * se comprueba el permiso con Bukkit.
     * 
     * @param player Jugador.
     * @param permission Permiso.
     */
    public static boolean playerHasPermission(Player player, String permission){
        if(player.isOp()){
            return true;
        }
        
        if(PermissionsEx.getPermissionManager().has(player, permission)){
            return true;
        }
        
        if(player.hasPermission(permission)){
            return true;
        }
        
        return false;
    }
}
