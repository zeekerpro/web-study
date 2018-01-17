/**
 * @fileName :     KeychainApi
 * @author :       zeeker
 * @date :         17/01/2018 14:23
 * @description :
 */

package com.zeeker.keychain.api;

import com.zeeker.keychain.model.Keychain;
import com.zeeker.keychain.service.KeychainService;
import com.zeeker.keychain.service.impl.KeychainServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/list")
public class KeychainApi extends HttpServlet {

    private KeychainService keychainService;

    {
        keychainService = new KeychainServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Keychain> keychains = keychainService.findAll();
        req.setAttribute("keychains", keychains);
        req.getRequestDispatcher("/WEB-INF/views/listKeychains.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
