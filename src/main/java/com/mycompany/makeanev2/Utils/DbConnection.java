
package com.mycompany.makeanev2.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;


public class DbConnection {
   
    
    public static Connection getConnection () throws SQLException, NamingException {
    return (new MySQLConnectionUtil().getConnection());
    
    }
    
//    public static Connection getConnection2 () throws ClassNotFoundException, SQLException {
//    return MySQLConnectionUtil.getMySQLConnection();
//    }
}
