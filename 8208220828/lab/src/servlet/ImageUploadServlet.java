package servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/uploadImage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ImageUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，生产环境中建议指定具体来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        System.out.println("imgupload");

        try {
            Part filePart = request.getPart("imageFile");
            String contentType = filePart.getContentType();
            if (contentType.equals("application/octet-stream")) {
                return;
            }
            InputStream inputStream = filePart.getInputStream();
            byte[] imageData = inputStream.readAllBytes();

            // 将图片数据存入数据库
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_lab", "root", "123456");
                String sql = "update user_profile set profile_photo = ?, content_type = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setBytes(1, imageData);
                pstmt.setString(2, contentType);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("changgong");
                    out.println("{\"success\": true, \"message\": \"图片上传成功！\"}");
                } else {
                    out.println("{\"success\": false, \"message\": \"图片上传失败。\"}");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("{\"success\": false, \"message\": \"数据库错误。\"}");
            } finally {
                closeResources(pstmt, conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"success\": false, \"message\": \"发生异常。\"}");
        }
    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}