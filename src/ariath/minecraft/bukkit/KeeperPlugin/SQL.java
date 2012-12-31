package ariath.minecraft.bukkit.KeeperPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Clase para manejar MySQL.
 * @author Ariath
 */
public class SQL {    
    private static String mysqlUser = "";
    private static String mysqlPassword = "";
    
    private static String connectionURL = "";
    
    
    /**
     * Comprueba si se puede conectar con MySQL.
     */
    public static boolean canConnectToMySQL(){
        Connection dbConn = null;
        
        try{
            dbConn = DriverManager.getConnection(connectionURL, 
                                                 mysqlUser, 
                                                 mysqlPassword);
            
            dbConn.close();
            return true;
            
        }catch(SQLException e){
            Log.warning("Error conectar con MySQL");
            Log.warning(e.getMessage());         
            
            if(Config.showMySQLErrors){
                e.printStackTrace();
            }
            
            return false;
        }
    }
    
    
    /**
     * Realiza una consulta normal a la base de datos.
     * @param queryString Consulta a realizar.
     */
    public static ArrayList<HashMap<String, String>> executeNormalQuery(String queryString){
        Connection dbConn = null;
        Statement statement = null;
        ResultSet result = null;
        ResultSetMetaData resultMetadata = null;
        ArrayList<HashMap<String, String>> queryResult = new ArrayList<HashMap<String, String>>();
        
        int columns = 0;
        
        try {
            dbConn = DriverManager.getConnection(connectionURL, 
                                                 Config.mysqlUser, 
                                                 Config.mysqlPassword);
            
            statement = dbConn.createStatement();
            result = statement.executeQuery(queryString);
            resultMetadata = result.getMetaData();
            
            columns = resultMetadata.getColumnCount();
            
            while(result.next()){
                HashMap<String, String> row = new HashMap<String, String>();
                
                for(int c = 1; c <= columns; c ++){
                    row.put(resultMetadata.getColumnName(c), result.getString(c));                    
                }
                
                queryResult.add(row);
            }
            
            dbConn.close();
            
        } catch (SQLException e) {            
            Log.warning("Error al ejecutar la query: " + queryString);
            Log.warning(e.getMessage());
            
            if(Config.showMySQLErrors){
                e.printStackTrace();
            }
        }
        
        return queryResult;
    }
    
    
    /**
     * Realiza una consulta para actualizar la base de datos.
     * @param queryString Consulta a realizar.
     */
    public static void executeUpdateQuery(String queryString){
        Connection dbConn = null;
        PreparedStatement query = null;
        
        try {
            dbConn = DriverManager.getConnection(connectionURL, 
                                                 Config.mysqlUser, 
                                                 Config.mysqlPassword);
            
            query = dbConn.prepareStatement(queryString);
            query.executeUpdate();
            query.close();
            dbConn.close();
            
        } catch (SQLException e) {
            Log.warning("Error al ejecutar la query: " + queryString);
            Log.warning(e.getMessage());
            
            if(Config.showMySQLErrors){
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * Inicializa MySQL.
     * 
     * @param host Host.
     * @param port Puerto.
     * @param user Usuario.
     * @param password Password.
     * @param database Base de datos a usar.
     */
    public static void init(String host, String port, String user, String password, String database){
        mysqlUser = user;
        mysqlPassword = password;
        
        connectionURL = ("jdbc:mysql://" + host + ":" + port + "/" + database);
    }
}
