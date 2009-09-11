import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import sun.misc.*;
import java.util.*;
import com.javaexchange.dbConnectionBroker.*;

/**
 *  Example2:  This servlet demonstrates that multiple concurrent hits are processed
 *  correctly by the DbConnectionBroker.  
 *  Each hit inserts a new record into a log table with a unique
 *  sequence number and timestamp.  A simple test could be done by hitting the refresh
 *  button on the browser in rapid sucession while keeping a mental count.  
 *  You should then see a single HTML page returned to the browser (the latest one) 
 *  but with the data for each of the refresh button hits included.
 *  This example also displays the current connection from the connection pool.
 *  Marc A. Mnich  12/11/97
 */
public class Example2 extends HttpServlet {
    DbConnectionBroker myBroker;   

    public void init (ServletConfig config) throws ServletException {
        super.init(config); 
        // The below statement sets up a Broker with a minimun pool size of 2 connections
        // and a maximum of 5.  The log file will be created in 
        // D:\JavaWebServer1.1\DCB_Example2.log and the pool connections will be
        // restarted once a day.
        try {myBroker = new DbConnectionBroker("oracle.jdbc.driver.OracleDriver",
                                         "jdbc:oracle:thin:@209.194.12.223:1526:orcl",
                                         "scott","tiger",2,7,
                                         "D:\\JavaWebServer1.1\\DCB_Example2.log",1.0);
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
        PreparedStatement pstmt = null;
        response.setContentType ("text/html");

        try {
            // Get a DB connection from the Broker
            conn = myBroker.getConnection();
            
            thisConnection = myBroker.idOfConnection(conn);
            
            
            out.println("<h3>DbConnectionBroker Example 2</h3>" +
                        "Your log history queried using connection id = <b>" +
                        thisConnection + "</b>:<p>ID URL Timestamp<br>");
            
            
            // Load some data into the DCB log file
            pstmt = conn.prepareStatement("insert into dcb_logs " +
                                          "(sno,timestamp,url) values " +
                                          "(sno_seq.nextval,sysdate,?)");
            pstmt.setString(1,request.getRemoteAddr());
            pstmt.executeUpdate();
            
            
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select sno," +
                             "to_char(timestamp,'MM/DD/YY HH24:MI:SS')," +
                             "url from dcb_logs " +
                             "where url='" + request.getRemoteAddr() + 
                             "' and timestamp > sysdate-.0003 order by timestamp desc");
            
            
            while (rset.next()) {
                out.println(rset.getString(1) + " " + rset.getString(3) + 
                            " " + rset.getString(2) + "<BR>");
            }
        } catch (SQLException e1) {
	    out.println("<i><b>Error code:</b> " + e1 + "</i>");
	} finally {
            try{if(stmt != null) {stmt.close();}} catch(SQLException e1){};
            try{if(pstmt != null) {pstmt.close();}} catch(SQLException e1){};
            
            // The connection is returned to the Broker
            myBroker.freeConnection(conn);
        }
        
        out.close();
        response.getOutputStream().close();
    }
}
