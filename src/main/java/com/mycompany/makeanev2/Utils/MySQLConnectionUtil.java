package com.mycompany.makeanev2.Utils;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

/*предметная реализация соединения с MySQL, эта СУБД выбрана в данном проекте*/
public class MySQLConnectionUtil {

    private InitialContext ic;
    private DataSource ds;

    public Connection getConnection() throws SQLException, NamingException {
        ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/makeanev");
        return ds.getConnection();
    }
}
