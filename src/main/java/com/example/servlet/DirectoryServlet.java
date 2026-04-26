package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class DirectoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        else {
            String username = (String) session.getAttribute("username");
            String homeDir = System.getProperty("user.home") + "\\" + "filemanager" + "\\" + username + "\\";
            File homeDirFile = new File(homeDir);
            String canonicalHomeDir = homeDirFile.getCanonicalPath() + "\\";
            String path = req.getParameter("path");
            File requestedFile;
            if (path == null || path.isEmpty())
                requestedFile = homeDirFile;
            else
                requestedFile = new File(path);
            String canonicalRequested = requestedFile.getCanonicalPath();
            if (!canonicalRequested.startsWith(canonicalHomeDir)) {
                requestedFile = homeDirFile;
                path = requestedFile.getAbsolutePath();
            }
            else {
                path = canonicalRequested;
            }
            File directory = requestedFile;
            if (!directory.exists()) {
                directory.mkdir();
            }

            File[] files = directory.listFiles();

            req.setAttribute("currentPath", path);
            req.setAttribute("files", files);
            req.setAttribute("parentPath", directory.getParent());
            req.setAttribute("currentTime", new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()));
            req.getRequestDispatcher("/explorer.jsp").forward(req, resp);
        }
    }
}