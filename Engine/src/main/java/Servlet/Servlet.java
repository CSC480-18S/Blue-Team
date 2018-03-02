/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import EventHandlers.EventHandler;
import Session.Start;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static Session.Session.LogInfo;
import static Session.Session.LogWarning;


/**
 *
 * @author ulocal
 */
public class Servlet extends HttpServlet {


    public void init(){
        Start.main(new String[0]);
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LogInfo("Servlet: processing request");
//        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (request.getParameter("request").equals("join")) {
                // Join request
                String username = request.getParameter("username");
                String mac = request.getParameter("macAddress");
                out.print(EventHandler.joinHandler(username, mac));
            } else if (request.getParameter("request").equals("play")) {
                // Play Move request
                int startX = Integer.parseInt(request.getParameter("startX"));
                int startY = Integer.parseInt(request.getParameter("startY"));
                boolean horizontal = false;
                if(request.getParameter("horizontal").equals("y")){
                    horizontal = true;
                } else if (request.getParameter("horizontal").equals("n")){
                    horizontal = false;
                }
                String word = request.getParameter("word");
                out.print(EventHandler.playHandler(startX, startY, horizontal,
                        word));
            } else if (request.getParameter("request").equals("leave")) {
                // Leave a Game request
                String username = request.getParameter("username");
                String mac = request.getParameter("macAddress");
                out.print(EventHandler.leaveHandler(username, mac));
            } else if (request.getParameter("request").equals("forfeit")) {
                // Forfeit a Game request
                String username = request.getParameter("username");
                String mac = request.getParameter("macAddress");
                out.print(EventHandler.forfeitHandler(username, mac));
            } else if (request.getParameter("request").equals("login")) {
                // Login request
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                out.print(EventHandler.loginHandler(username, password));
            } else if (request.getParameter("request").equals("exchange")) {
                // Exchange Tiles request
                String tiles = request.getParameter("tiles");
                out.print(EventHandler.exchangeHandler(tiles));
            } else if (request.getParameter("request").equals("pass")) {
                // Pass to Next Player request
                String username = request.getParameter("username");
                out.print(EventHandler.passHandler(username));
            } else if (request.getParameter("request").equals("stats")) {
                // Open Stats request
                out.print(EventHandler.statsHandler());
            } else {
                // Unknown request
                out.print(EventHandler.unknownHandler());
            }
        }
        catch (Exception e) {
            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }
        finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
