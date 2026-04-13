package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        else {
            String username = (String) session.getAttribute("username");
            String homeDir = System.getProperty("user.home") + "\\" + "filemanager" + "\\" + username + "\\";

            String fileDownloadPath = req.getParameter("fileDownloadPath");
            if (fileDownloadPath == null || !fileDownloadPath.startsWith(homeDir)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён!!!");
            }

            else {
                File file = new File(fileDownloadPath);
                resp.setContentType("application/octet-stream");
                String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
                resp.setContentLength((int) file.length());

                try (FileInputStream fileInputStream = new FileInputStream(file);
                     OutputStream outputStream = resp.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
}