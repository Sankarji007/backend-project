import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

@WebServlet(name = "SaveRecipe", value = "/SaveRecipe")
public class SaveRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5000/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "root";

        JSONObject obj = new JSONObject();
        String email = request.getParameter("email");
        String recipe_id = request.getParameter("recipe_id");
        String str=request.getParameter("carddata");
        JSONObject card=new JSONObject(str);

        try {


            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt = conn.createStatement();
            String sql = "select * from savedrecipe where email='"+email+"'and recipe_id='"+recipe_id+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifalreadyexist=rs.next();
            if(!ifalreadyexist)
            {
                sql = "INSERT INTO savedrecipe(email,recipe_id,card_data) values('"+email+"','"+recipe_id+"','"+card+"')";
                Statement stmt1=conn.createStatement();
                stmt1.executeUpdate(sql);
                obj.put("success","successfully registered");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.print(obj);

    }
}
