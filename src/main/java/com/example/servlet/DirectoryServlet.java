package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class DirectoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getParameter("path");
        if (path == null || path.isEmpty())
            path = System.getProperty("user.home");

        File directory = new File(path);
        File[] files = directory.listFiles();

        req.setAttribute("currentPath", path);
        req.setAttribute("files", files);
        req.setAttribute("parentPath", directory.getParent());
        req.setAttribute("currentTime", new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()));
        req.getRequestDispatcher("/explorer.jsp").forward(req, resp);
    }
}
