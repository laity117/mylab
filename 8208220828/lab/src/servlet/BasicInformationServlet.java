package servlet;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/basicInformation")
public class BasicInformationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，生产环境中建议指定具体来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_lab", "root", "123456");

            // 获取最新的访问次数
            pstmt = conn.prepareStatement("SELECT name, age, job, phone, email, address FROM user_profile");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // 返回JSON响应
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject json = new JSONObject();
                json.put("name", rs.getString("name"));
                json.put("age", rs.getInt("age"));
                json.put("job", rs.getString("job"));
                json.put("phone", rs.getString("phone"));
                json.put("email", rs.getString("email"));
                json.put("address", rs.getString("address"));
                response.getWriter().write(json.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { /* ignored */ }
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { /* ignored */ }
            try { if (conn != null) conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}
