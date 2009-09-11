import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import sun.misc.*;
import java.util.*;
import com.javaexchange.dbConnectionBroker.*;

/**
 * Basic example of the DbConnectionBroker.
 * Servlet simply queries a timestamp from an Oracle database and
 * displays it with the ID of the current connection from the pool.
 * It is invoked by the URL http://whatever-domain/servlet/Example1
 */
public class Example1 extends HttpServlet {
    DbConnectionBroker myBroker;   

    public void init (ServletConfig config) throws ServletException {
        super.init(config); 
        // The below statement sets up a Broker with a minimun pool size of 2 connections
        // and a maximum of 5.  The log file will be created in 
        // D:\JavaWebServer1.1\DCB_Example.log and the pool connections will be
        // restarted once a day.
        try {myBroker = new DbConnectionBroker("oracle.jdbc.driver.OracleDriver",
                                         "jdbc:oracle:thin:@209.94.3.212:1526:orcl",
                                         "scott","tiger",2,6,
                                         "D:\\JavaWebServer1.1\\DCB_Example1.log",0.01);
	} catch (IOException e5)  { 
	}
    }

    public void doGet (
	HttpServletRequest request,
	HttpServletResponse response
    ) throws ServletException, IOException {
        PrintStream out = new PrintStream (response.getOutputStream());
        Connection conn = null;
        Statement stmt = null;
        int thisConnection;
        response.setContentType ("text/html");

        try {
            // Get a DB connection from the Broker
            conn = myBroker.getConnection();
            
            thisConnection = myBroker.idOfConnection(conn);
            
            out.println("<h3>DbConnectionBroker Example 1</h3>" +
                        "Using connection " + thisConnection + 
                        " from connection pool<p>");
            
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select sysdate from dual");
            
            while (rset.next()) {
                out.println("Time queried from the Database is " + rset.getString(1));
            }
        } catch (SQLException e1) {
	        out.println("<i><b>Error code:</b> " + e1 + "</i>");
	} finally {
            try{if(stmt != null) {stmt.close();}} catch(SQLException e1){};
            
            // The connection is returned to the Broker
            myBroker.freeConnection(conn);
        }
        
        out.close();
        response.getOutputStream().close();
    }
}
