package org.unibl.etf.virtual_bank_jsp_m2.controllers;

import com.google.gson.Gson;
import org.unibl.etf.virtual_bank_jsp_m2.dao.BankAccountDAO;
import org.unibl.etf.virtual_bank_jsp_m2.exceptions.NotFoundException;
import org.unibl.etf.virtual_bank_jsp_m2.models.BankAccount;
import org.unibl.etf.virtual_bank_jsp_m2.models.Transaction;
import org.unibl.etf.virtual_bank_jsp_m2.models.enums.CardType;
import org.unibl.etf.virtual_bank_jsp_m2.models.requests.BankAccountRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/api/bank/accounts")
public class BankServletController extends HttpServlet {

    public static final String LOGIN_ACTION = "login";
    public static final String LOGOUT_ACTION = "logout";
    public static final String HOME = "home";
    public static final String TRANSACTIONS = "transactions";
    public static final String CHANGE_ACCOUNT_STATUS = "change_account_status";
    public static final String RELOAD_TRANSACTIONS = "reload_transactions";
    public static final String IS_BLOCKED_ACCOUNT = "is_blocked_account";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/pages/login.jsp";
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        session.setAttribute("notification", "");
        try {
            if (action == null || action.isEmpty()) {
                address = "/WEB-INF/pages/login.jsp";
            } else if (action.equals(LOGOUT_ACTION)) {
                session.invalidate();
                address = "/WEB-INF/pages/login.jsp";
            } else if (action.equals(LOGIN_ACTION)) {
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String cardNumber = request.getParameter("cardNumber");
                CardType cardType = CardType.valueOf(request.getParameter("cardType"));
                Date expirationDate = Date.valueOf(request.getParameter("expirationDate"));
                String pin = request.getParameter("pin");

                try {
                    BankAccount bankAccount = BankAccountDAO.selectAccountByProvidedData(new BankAccountRequest(name, surname, cardNumber, cardType, expirationDate, pin));
                    List<Transaction> transactions = BankAccountDAO.getAllTransactions(bankAccount);
                    session.setAttribute("account", bankAccount);
                    session.setAttribute("transactions", transactions);
                    address = "/WEB-INF/pages/home.jsp";
                } catch (NotFoundException | NullPointerException exception) {
                    System.out.println(exception.getMessage());

                    if (exception instanceof NotFoundException)
                        session.setAttribute("notification", "Account not found!");

                    address = "/WEB-INF/pages/login.jsp";
                }
            } else {
                BankAccount bankAccount = (BankAccount) session.getAttribute("account");

                if (bankAccount == null) {
                    address = "/WEB-INF/pages/login.jsp";
                } else {
                    if (action.equals(TRANSACTIONS)) {
                        address = "/WEB-INF/pages/transactions.jsp";
                    } else if (action.equals(HOME)) {
                        address = "/WEB-INF/pages/home.jsp";
                    } else if (action.equals(RELOAD_TRANSACTIONS)) {
                        List<Transaction> transactions = BankAccountDAO.getAllTransactions(bankAccount);
                        session.setAttribute("transactions", transactions);
                        response.getWriter().write(new Gson().toJson(transactions));
                        return;
                    } else if (action.equals(CHANGE_ACCOUNT_STATUS)) {
                        bankAccount.setBlocked(!bankAccount.getBlocked());
                        if (!BankAccountDAO.changeAccountStatus(bankAccount)) {
                            throw new Exception("Changing status failed!");
                        }

                    } else if (action.equals(IS_BLOCKED_ACCOUNT)) {
                        if (bankAccount.getBlocked())
                            response.getWriter().write("true");
                        else
                            response.getWriter().write("false");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
