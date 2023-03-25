import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "SignIn", value = "/SignIn")
public class SignIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out=response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5000/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "root";
        String email=request.getParameter("email");
        String pass=request.getParameter("password");
        System.out.println(email+" "+pass);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM login where email='"+email+"'and "+"password='"+pass+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("success","login successfully");
            }
            else{
                obj.put("success","login failed incorrect username or password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.println(obj);
        out.println("hello");
    }

}
