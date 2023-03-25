import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "getSavedRecipe", value = "/getSavedRecipe")
public class getSavedRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5000/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "root";
        String email = request.getParameter("email");
        JSONObject object=new JSONObject();
        JSONArray arr=new JSONArray();
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt = conn.createStatement();
            String sql = "select * from savedrecipe where email='" + email + "'";

            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next())
            {

                String str=rs.getString(4);
                JSONObject obj=new JSONObject(str);
                arr.put(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        object.put("ResultSet",arr);
        out.println(object);
    }
}
