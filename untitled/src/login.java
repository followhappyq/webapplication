

import dao.Dao;
import dao.MyDao;
import data.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@WebServlet("/login")
public class login extends HttpServlet {

    private static final long serialVersionUID = 2L;

    public login() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String target;
        Dao dao = MyDao.getDao();
        HttpSession session = request.getSession();
        if (request.getParameter("email") != "") {

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Users user = dao.getUser(email);
            if (user != null && (user.getPassword().equals(password) && user.getEmail().equals(email))) {
                session.setAttribute("login", email);
                session.setAttribute("loginStatus", "ON");
                if (user.getRole() > 0){
                    target = "adminController";
                } else
                    target = "PageControlPanel";

            } else target = "incorrectInfo.html";

        } else target = "incorrectInfo.html";

        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        try {
            dispatcher.include(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

}