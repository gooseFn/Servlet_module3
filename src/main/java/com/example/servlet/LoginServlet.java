package com.example.servlet;

import com.example.servlet.models.User;
import com.example.servlet.models.UserStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/files");
        }

        else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (UserStorage.validateUser(username, password)) {
            HttpSession session = req.getSession();
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/files");
        }

        else {
            req.setAttribute("error", "Неверное имя пользователя или пароль!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
