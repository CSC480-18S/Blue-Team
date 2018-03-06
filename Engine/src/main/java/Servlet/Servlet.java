/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import EventHandlers.EventHandler;
import Session.Start;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Session.Session;


/**
 *
 * @author ulocal
 */
@WebServlet(urlPatterns = "/Servlet", loadOnStartup=1)
public class Servlet extends HttpServlet {

    @Override
    public void init(){
        try{
            Start.main(null);

        }
        catch (Exception e)
        {

        }
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

        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        PrintWriter out = response.getWriter();
        try {
            if (request.getParameter("request").equals("join")) {
                // Join request
                String username = request.getParameter("username");
                String mac = getMACAddress(request.getRemoteAddr());

                out.write(EventHandler.joinHandler(username, mac));       // Write response body.

            } else if (request.getParameter("request").equals("play")) {
                String word = request.getParameter("word");
                // Play Move request

                String coords = request.getParameter("coords");

                boolean horizontal = false;
                if(request.getParameter("direction").equals("h")){
                    horizontal = true;
                } else if (request.getParameter("direction").equals("v")){
                    horizontal = false;
                }
                
                String[] splitCoords = coords.split(",");
                int startX = Integer.parseInt(splitCoords[0]);
                int startY = Integer.parseInt(splitCoords[1]);
                String macAddress = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.playHandler(startX, startY, horizontal,
                        word, macAddress));
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
            //Session.LogWarning(e.getMessage() + "\n" + e.getStackTrace());
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
        String text = "Success message";

        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(text);       // Write response body.
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


    public String getMACAddress(String ip){
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("arp -a" );
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i <100; i++) {
                str = input.readLine();
                if (str != null) {
                    if(str.contains(ip)){
                        macAddress += str.substring(str.indexOf("at ") + 3, str.indexOf("at ") + 20);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }

}
