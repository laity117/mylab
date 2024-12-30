package servlet;

import POJO.EducationEntry;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.google.gson.Gson;

@WebServlet("/loadEducation")
public class LoadEducationServlet extends HttpServlet {
    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/web_lab";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，生产环境中建议指定具体来源
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        try {
            loadEducation(request, response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEducation(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        List<EducationEntry> entries = new ArrayList<>();

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM education_background order by date_range ")) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EducationEntry entry = new EducationEntry(
                            rs.getInt("id"),
                            rs.getString("date_range"),
                            rs.getString("school_name"),
                            rs.getString("degree_major")
                    );
                    entries.add(entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert list to JSON and send it back to client
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String json = JSON.toJSONString(entries); // 使用 FastJSON 序列化
        out.print(json);
        out.flush();
    }


}