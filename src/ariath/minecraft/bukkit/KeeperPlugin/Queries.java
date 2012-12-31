package ariath.minecraft.bukkit.KeeperPlugin;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Consultas SQL.
 */
public class Queries {
    /**
     * Añade una nueva entrada al registro de entrada de jugadores al servidor.
     * 
     * @param playerName Nombre del jugador.
     * @param playerIP IP.
     * @param date Fecha.
     * @param time Hora.
     */
    public static void addLoginEntry(String playerName, String playerIP, String date, String time){
        SQL.executeUpdateQuery("INSERT INTO " + Config.database + "." + Keeper.LOGIN_TABLE + " (Player, IP, Date, Time) " +
                               "VALUES ('" + playerName + "', " +
                               "'" + playerIP + "', " + 
                               "'" + date + "', " + 
                               "'" + time + "')");
    }
    
    
    /**
     * Añade una nueva entrada al registro de salida de jugadores del servidor.
     * 
     * @param playerName Nombre del jugador.
     * @param playerIP IP.
     * @param date Fecha.
     * @param time Hora.
     */
    public static void addLogoffEntry(String playerName, String playerIP, String date, String time){
        SQL.executeUpdateQuery("INSERT INTO " + Config.database + "." + Keeper.LOGOFF_TABLE + " (Player, IP, Date, Time) " +
                               "VALUES ('" + playerName + "', " +
                               "'" + playerIP + "', " + 
                               "'" + date + "', " + 
                               "'" + time + "')");
    }
        
    
    /**
     * Crea las tablas necesarias.
     */
    public static void createNeededTables(){
        SQL.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + Config.database);
        
        if(SQL.executeNormalQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + Config.database + 
                              "' AND table_name LIKE 'keeper_%'").size() < 2){
            
            SQL.executeUpdateQuery("CREATE TABLE " + Config.database + "." + Keeper.LOGIN_TABLE + " (Entry INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                                                                                    "Player VARCHAR(20) NOT NULL, " +
                                                                                                    "IP VARCHAR(17) NOT NULL, " +
                                                                                                    "Date DATE NOT NULL, " +
                                                                                                    "Time TIME NOT NULL);");
            
            SQL.executeUpdateQuery("CREATE TABLE " + Config.database + "." + Keeper.LOGOFF_TABLE + " (Entry INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                                                                                     "Player VARCHAR(20) NOT NULL, " +
                                                                                                     "IP VARCHAR(17) NOT NULL, " +
                                                                                                     "Date DATE NOT NULL, " +
                                                                                                     "Time TIME NOT NULL);");
        }
    }

    
    /**
     * Obtiene las IPs usadas por un jugador.
     * @param player Jugador.
     */
    public static ArrayList<String> getIPSUsedBy(String player){
        ArrayList<HashMap<String, String>> queryResult;
        ArrayList<String> playerIPs = new ArrayList<String>();
        
        if(player.indexOf("*") > -1){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE Player LIKE '" + player.replace("*", "%") + "' GROUP BY IP");
            
        } else {
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE Player = '" + player + "' GROUP BY IP");
        }
        
        if(queryResult.size() > 0){
            for(HashMap<String, String> entry : queryResult){
                playerIPs.add(entry.get("IP"));
            }
        }
        
        return playerIPs;
    }
    
    
    /**
     * Obtiene los usuarios que han usado una ip.
     * @param ip IP.
     */
    public static ArrayList<String> getPlayersUsing(String ip){
        ArrayList<HashMap<String, String>> queryResult;
        ArrayList<String> players = new ArrayList<String>();
        
        if(ip.indexOf("*") > -1){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE IP LIKE '" + ip.replace("*", "%") + "' GROUP BY Player");
            
        } else {
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE IP = '" + ip + "' GROUP BY Player");
        }
        
        if(queryResult.size() > 0){
            for(HashMap<String, String> entry : queryResult){
                players.add(entry.get("Player"));
            }
        }
        
        return players;
    }
    
    
    /**
     * Muestra todas las IPs similares a la dada.
     * Sino se indica ninguna, lista todas las registradas.
     * 
     * @param ip IP a buscar.
     */
    public static ArrayList<String> listIPs(String ip){
        ArrayList<HashMap<String, String>> queryResult;
        ArrayList<String> ips = new ArrayList<String>();
        
        if(ip.equalsIgnoreCase("") || ip == null){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " GROUP BY IP");
            
        } else if(ip.indexOf("*") > -1){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE IP LIKE '" + ip.replace("*", "%") + "' GROUP BY IP");
            
        } else {
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE IP = '" + ip + "' GROUP BY IP");
        }
        
        if(queryResult.size() > 0){
            for(HashMap<String, String> entry : queryResult){
                ips.add(entry.get("IP"));
            }
        }
        
        return ips;
    }
    
    
    /**
     * Muestra todos los usuarios similares al indicado.
     * Sino se indica ninguno, lista todos los registrados.
     * 
     * @param player Jugador a buscar.
     */
    public static ArrayList<String> listPlayers(String player){
        ArrayList<HashMap<String, String>> queryResult;
        ArrayList<String> players = new ArrayList<String>();
        
        if(player.equalsIgnoreCase("") || player == null){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " GROUP BY Player");
            
        } else if(player.indexOf("*") > -1){
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE Player LIKE '" + player.replace("*", "%") + "' GROUP BY Player");
            
        } else {
            queryResult = SQL.executeNormalQuery("SELECT * FROM " + Config.database + "." + Keeper.LOGIN_TABLE + " WHERE Player = '" + player + "' GROUP BY Player");
        }
        
        if(queryResult.size() > 0){
            for(HashMap<String, String> entry : queryResult){
                players.add(entry.get("Player"));
            }
        }
        
        return players;
    }
}
