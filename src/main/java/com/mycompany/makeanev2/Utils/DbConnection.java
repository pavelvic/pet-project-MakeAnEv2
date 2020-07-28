
package com.mycompany.makeanev2.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

/*инкапсулируем соединение с БД. Меняя этот класс можно настраивать соединения с разными БД
это позволяет дальше в программе обращаться только к этому классу, не задумываясь какая СУБД выбрана и какой драйвер использован
код становится более универсальным*/
public class DbConnection {
   
    
    public static Connection getConnection () throws SQLException, NamingException {
    return (new MySQLConnectionUtil().getConnection());
    
    }  
}
