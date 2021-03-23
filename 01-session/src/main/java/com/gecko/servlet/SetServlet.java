package com.gecko.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SetServlet
 * @Deacription TODO
 * @Author Chen
 * @Date 2021/3/23 15:36
 * @Version 1.0
 */
@WebServlet("/set")
public class SetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("sessionAddr", "welcomeToSpringSession");
        resp.getWriter().write("setSessionSuccess!");

    }
}
