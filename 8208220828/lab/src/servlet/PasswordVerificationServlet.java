package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/verifyPassword")
public class PasswordVerificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("aaaaaaaaaaaa");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，生产环境中建议指定具体来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // 设置请求的字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            String password = request.getParameter("password");
            String from = request.getParameter("from");

            String correctPassword = "";

            // 加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_lab", "root", "123456");

            pstmt = conn.prepareStatement("SELECT password FROM password WHERE id = 1");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                correctPassword = rs.getString("password");
            }

            // 模拟密码验证逻辑
            boolean isPasswordCorrect = correctPassword.equals(password);

            String successMessage = "";

            String redirectUrl = "";

            if (isPasswordCorrect) {
                successMessage = "true";
            } else {
                successMessage = "false";
            }
            redirectUrl = "http://localhost:63342/lab/web/html/passwordVerification.html?successMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&from=" + URLEncoder.encode(from, "UTF-8");
            response.sendRedirect(redirectUrl); // 修改为实际的目标URL
        } catch (Exception e) {
            throw new ServletException("密码验证失败", e);
        }
    }
}