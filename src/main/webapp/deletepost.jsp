<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.example.dao.BoardDAO, com.example.bean.BoardVO, com.example.util.JDBCUtil.FileUpload"%>
<%
	String seq = request.getParameter("id");
	if (seq != ""){
		int id = Integer.parseInt(seq);
		BoardVO u = new BoardVO();
		u.setSeq(id);
		BoardDAO boardDAO = new BoardDAO();
		String filename = BoardDAO.getPhotoFilename(id);
		if(filename !=null) {
			FileUpload.deleteFile(request, filename);
		}
		boardDAO.deleteBoard(u);
	}
	response.sendRedirect("posts.jsp");
%>