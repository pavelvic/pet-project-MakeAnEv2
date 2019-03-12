package com.mycompany.makeanev2;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class MySQLConnectionUtil {
    
    	private InitialContext ic;
	private DataSource ds;
        
	
	public Connection getConnection() throws SQLException, NamingException	{
		ic = new InitialContext();
		ds = (DataSource) ic.lookup("java:/comp/env/jdbc/makeanev"); 
		return ds.getConnection();
	}
        
//        public static Connection getMySQLConnection () throws ClassNotFoundException, SQLException {
//        
//        String dbDrv = "com.mysql.jdbc.Driver"; //название драйвера
//        String dbUrl = "jdbc:mysql://localhost/makeanevents?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //урл к базе (у меня развернут MySQL на локальной машине и база назвывается makeanevent)
//        String user = "root"; //имя пользователя
//        String pass = "1234"; //пароль
//            
//            
//            
//        Class.forName(dbDrv); //загрузка драйвера, ссылаемся на внешний дарйвер, который реализует работу с СУБД
//        Connection con = DriverManager.getConnection(dbUrl, user, pass); //объект подключения
//        return con;
//        }
        
        
    
}
