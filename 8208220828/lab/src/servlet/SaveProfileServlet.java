package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/saveProfile")
@MultipartConfig // 允许上传文件
public class SaveProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，生产环境中建议指定具体来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // 设置请求和响应的字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 获取表单数据
            String name = request.getParameter("name");
            String ageStr = request.getParameter("age");
            int age = Integer.parseInt(ageStr);
            String job = request.getParameter("job");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            System.out.println(name);
            System.out.println(age);
            System.out.println(job);
            System.out.println(phone);
            System.out.println(email);
            System.out.println(address);

            // 处理文件上传（省略，与之前相同）

            // 更新数据库
            updateDatabase(name, age, job, phone, email, address);

            // 更新成功后，设置会话属性
            request.getSession().setAttribute("successMessage", "个人信息已成功更新！");

            // 重定向到目标页面
            // 更新成功后，构建带有成功消息的重定向URL
            String successMessage = "信息已成功更新！";
            String redirectUrl = "http://localhost:63342/lab/web/html/index.html?successMessage=" + URLEncoder.encode(successMessage, "UTF-8");
            response.sendRedirect(redirectUrl); // 修改为实际的目标URL
        } catch (SQLException e) {
            throw new ServletException("数据库操作失败", e);
        }
    }

    private void updateDatabase(String name, int age, String job, String phone, String email, String address)
            throws SQLException {
        // SQL更新语句
        String sql = "UPDATE user_profile SET name=?, age=?, job=?, phone=?, email=?, address=? WHERE id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        // 建立数据库连接
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_lab", "root", "123456");
            pstmt = conn.prepareStatement(sql);

            // 设置PreparedStatement参数
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, job);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, address);
            pstmt.setInt(7, 1); // 用户ID

            // 执行更新
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("记录已成功更新");
            } else {
                System.out.println("没有找到匹配的记录进行更新");
            }
        } catch (SQLException e) {
            throw new RuntimeException("执行SQL时发生错误", e);
        }  finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { /* ignored */ }
            try { if (conn != null) conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}