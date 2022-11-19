package com.example.util;

import com.example.bean.BoardVO;
import com.example.dao.BoardDAO;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://walab.handong.edu:3306/p1_22100729", "p1_22100729", "Eifaih9sau");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
//    public static void main(String ars[]) {
//		Connection conn = getConnection();
//		if(conn != null)
//			System.out.println("DB 연결됨!");
//		else
//			System.out.println("DB 연결중 오류 !");
//	}

    public static class FileUpload {
        public BoardVO uploadPhoto(HttpServletRequest request) {
            String filename = "";
            int sizeLimit = 15 * 1024 * 1024;

            String realPath = request.getServletContext().getRealPath("upload");

            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();

            BoardVO one = null;
            MultipartRequest multipartRequest = null;
            try {
                multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());

                filename = multipartRequest.getFilesystemName("photo");
                one = new BoardVO();
                String sid = multipartRequest.getParameter("seq");
                if (sid != null && !sid.equals("")) one.setSid(Integer.parseInt(seq));
                one.setCategory(multipartRequest.getParameter("category"));

                if (sid != null && !sid.equals("")) {
                    BoardDAO dao = new BoardDAO();
                    String oldfilename = dao.getPhotoFilename(Integer.parseInt(sid));
                    if (filename != null && oldfilename != null)
                        FileUpload.deleteFile(request, oldfilename);
                    else if (filename == null && oldfilename != null)
                        filename = oldfilename;
                }
                one.setPhoto(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return one;
        }

        public static void deleteFile(HttpServletRequest request, String filename) {
            String filePath = request.getServletContext().getRealPath("upload");

            File f = new File(filePath + "/" + filename);
            if( f.exists()) f.delete();
        }
    }
}
