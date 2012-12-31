package ariath.minecraft.bukkit.KeeperPlugin;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * Encargado de atender cuando un jugador entra y sale del servidor.
 * @author Ariath. 
 */
public class PlayerLoginListener implements Listener{
	/**
	 * Evento: Un jugador entra al servidor.
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player loggedPlayer = event.getPlayer();
		String playerIP = loggedPlayer.getAddress().toString().substring(1);
		String port = playerIP.substring(playerIP.indexOf(":"));
		
		Queries.addLoginEntry(loggedPlayer.getName(), playerIP.substring(0, (playerIP.length() - port.length())), getDate().toString(), getTime().toString());		
	}
	
	
	/**
	 * Evento: Un jugador sale del servidor.
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player loggedPlayer = event.getPlayer();
		String playerIP = loggedPlayer.getAddress().toString().substring(1);  
		String port = playerIP.substring(playerIP.indexOf(":"));
		
		Queries.addLogoffEntry(loggedPlayer.getName(), playerIP.substring(0, (playerIP.length() - port.length())), getDate().toString(), getTime().toString());		
	}
	
	
	/**
	 * Obtiene la fecha actual (Día, Més y Año).
	 */
	private Date getDate(){
	    Calendar calendar = Calendar.getInstance();
	    Date date = new Date(calendar.getTimeInMillis());
	    return date;
	}
	
	
	/**
	 * Obtiene la hora actual (Hora, minutos y segundos)
	 */
	private Time getTime(){
	    Calendar calendar = Calendar.getInstance();
	    Time time = new Time(calendar.getTimeInMillis());
	    return time;
	}
}
