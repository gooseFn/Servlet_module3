package com.example.servlet.models;

import com.example.servlet.dbService.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsersDAO {

    public static void addUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления пользователя", e);
        }

    }

    public static User getUser(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.byNaturalId(User.class).using("username", username).load();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка поиска пользователя: " + username, e);
        }
    }

    public static boolean validateUser(String username, String password) {
        User user = getUser(username);
        return user != null && user.getPassword().equals(password);
    }
}