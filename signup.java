import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "signup", value = "/signup")
public class signup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST");
        response.setHeader("Access-Control-Allow-Headers","Content-Type,Origin,Authorization");
        PrintWriter out=response.getWriter();
       // out.println("hello");
        String dbName = "jdbc:postgresql://localhost:5000/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "root";
        JSONObject obj = new JSONObject();
        String user=request.getParameter("username");
        String email=request.getParameter("email");
        String pass=request.getParameter("password");


        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt=conn.createStatement();
            String sql = "select * from login where username='"+user+"'or email='"+email+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifalreadyexist=rs.next();
            if(!ifalreadyexist)
            {

                sql="insert into login(username,email,password,created_at) values('"+user+"','"+email+"','"+pass+"','"+"now()')";
                Statement stmt1=conn.createStatement();
                stmt1.executeUpdate(sql);
                obj.put("success","successfully registered");


            }
            else{
                obj.put("success","user already exist");
            }


        } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
        }
        out.print(obj);

    }


}
