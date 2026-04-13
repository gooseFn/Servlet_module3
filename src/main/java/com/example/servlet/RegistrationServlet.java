package com.example.servlet;

import com.example.servlet.models.User;
import com.example.servlet.models.UserStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        if (UserStorage.getUser(username) == null) {
            User newUser = new User(username, password, email);
            UserStorage.addUser(newUser);
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/files");
        }

        else {
            req.setAttribute("error", "Пользователь с таким именем уже существует!");
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }
}
