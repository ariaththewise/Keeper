package ariath.minecraft.bukkit.KeeperPlugin.Commands;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ariath.minecraft.bukkit.KeeperPlugin.Keeper;
import ariath.minecraft.bukkit.KeeperPlugin.Queries;


public class KeeperCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player && !Keeper.playerHasPermission((Player)sender, "keeper.command")){
            sender.sendMessage(ChatColor.RED + "No tienes permisos para usar este comando.");
            return true;
        }

        
        /*************************/
        /* Sin parámetros: Ayuda */
        /*************************/
        if (args.length == 0){
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GREEN + "Keeper -- Ayuda");
            sender.sendMessage(ChatColor.GREEN + "----------------------------------------------------");
            sender.sendMessage(ChatColor.GREEN + "/keeper <player>");
            sender.sendMessage(ChatColor.GREEN + "Muestra las ips usadas por este jugador, y si alguien mas las ha usado.");
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GREEN + "/keeper listips [ip]");
            sender.sendMessage(ChatColor.GREEN + "Lista todas las ips registradas similares a la dada.");
            sender.sendMessage(ChatColor.GREEN + "Sino se indica ninguna, las lista todas.");
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GREEN + "/keeper listplayers [player]");
            sender.sendMessage(ChatColor.GREEN + "Lista todos los jugadores registrados similares al indicado.");
            sender.sendMessage(ChatColor.GREEN + "Sino se indica ninguno, los lista todos.");
            sender.sendMessage(" ");            
            return true;
        }
        
        
        /********************************/
        /* Parámetro: Nombre de jugador */
        /********************************/
        if(args.length == 1 && (!args[0].equalsIgnoreCase("listips") && !args[0].equalsIgnoreCase("listplayers"))){
            ArrayList<String> playerIPs = new ArrayList<String>();
            ArrayList<ArrayList<String>> playersSharingSameIPs = new ArrayList<ArrayList<String>>();            
            
            playerIPs = Queries.getIPSUsedBy(args[0]);
            
            if(playerIPs.size() > 0){
                for(int ip = 0; ip < playerIPs.size(); ip ++){
                    ArrayList<String> playersSharingThisIP = Queries.getPlayersUsing(playerIPs.get(ip));
                    playersSharingSameIPs.add(ip, playersSharingThisIP);                    
                }
                
                
                sender.sendMessage(ChatColor.GREEN + "----------------------------------------------------");
                sender.sendMessage(ChatColor.GREEN + "IPs registradas al jugador y jugadores que las han usado");
                sender.sendMessage(ChatColor.GREEN + "----------------------------------------------------");
                
                for(int ip = 0; ip < playerIPs.size(); ip ++){
                    ArrayList<String> playersSharingThisIP = playersSharingSameIPs.get(ip);                                        
                                        
                    sender.sendMessage(playerIPs.get(ip));
                    
                    if(playersSharingSameIPs.get(ip).size() > 0){
                        StringBuilder message = new StringBuilder();
                        message.append("- ");
                        
                        for(int player = 0; player < playersSharingThisIP.size(); player++){
                            String playerName = playersSharingThisIP.get(player);
                            
                            message.append(ChatColor.GOLD + playerName + ChatColor.WHITE);                            
                            
                            if(player != (playersSharingThisIP.size() -1)){
                                message.append(", ");
                            }
                        }
                        
                        sender.sendMessage(message.toString());
                        sender.sendMessage(" ");
                    }                    
                }
                
                return true;
            }
            
            sender.sendMessage(ChatColor.RED + "[Keeper] No hay nadie registrado con ese nombre.");
            return true;
            
        } else if (args.length >= 1){
            /****************************************/
            /* Parámetros "listips" y "listplayers" */
            /****************************************/
            
            ArrayList<String> queryResult = new ArrayList<String>();
            String searchPattern;
            
            if(args[0].equalsIgnoreCase("listips")){
                if(args.length == 2){
                    searchPattern = args[1];
                    queryResult = Queries.listIPs(args[1]);
                    
                } else {
                    searchPattern = "Todas las IPs";
                    queryResult = Queries.listIPs("");
                }
                                
            } else if(args[0].equalsIgnoreCase("listplayers")){
                if(args.length == 2){
                    searchPattern = args[1];
                    queryResult = Queries.listPlayers(args[1]);
                    
                } else {
                    searchPattern = "Todos los jugadores";
                    queryResult = Queries.listPlayers("");
                }                
                
            } else {
                return false;
            }
            
            if(queryResult.size() > 0){
                sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
                sender.sendMessage(ChatColor.GREEN + "Resultado de la busqueda por el patron '" + searchPattern + "'");
                sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            
                for(String entry : queryResult){
                    sender.sendMessage(ChatColor.GOLD + entry);
                }
            
                sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
                return true;
                
            } else {
                sender.sendMessage(ChatColor.GREEN + "[Keeper] La busqueda no devolvio ningun resultado.");
                return true;
            }
        }
        
        return false;
    }
}
