package servlet;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/count")
public class CountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
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

            // 更新访问次数
            pstmt = conn.prepareStatement("UPDATE count SET count = count + 1 WHERE id = 1");
            pstmt.executeUpdate();

            // 获取最新的访问次数
            pstmt = conn.prepareStatement("SELECT count FROM count WHERE id = 1");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");

                // 返回JSON响应
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject json = new JSONObject();
                json.put("count", count);
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