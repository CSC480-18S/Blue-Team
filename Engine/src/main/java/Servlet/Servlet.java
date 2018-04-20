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
            e.printStackTrace();
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
            String req = request.getParameter("request");
            req = req.toLowerCase();
            if (req.equals("join")) {
                // Join request
                String username = request.getParameter("username");
                String team = request.getParameter("team");
                String mac = getMACAddress(request.getRemoteAddr());

                out.write(EventHandler.joinHandler(username, mac, team));       // Write response body.

            } else if (req.equals("play")) {
                String word = request.getParameter("word");
                word = word.toUpperCase();
                // Play Move request

                String coords = request.getParameter("coords");

                boolean horizontal = false;
                if(request.getParameter("direction").equals("h")){
                    horizontal = true;
                } else if (request.getParameter("direction").equals("v")){
                    horizontal = false;
                }
                
                String[] splitCoords = coords.split("_");
                int startX = Integer.parseInt(splitCoords[0]);
                int startY = Integer.parseInt(splitCoords[1]);
                String macAddress = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.playHandler(startX, startY, horizontal,
                        word, macAddress));
            } else if(req.equals("gethand")) {
                String macAddress = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.getHandHandler(macAddress));
            }else if (req.equals("leave")) {
                // Leave a Game request
                String mac = getMACAddress(request.getRemoteAddr());
                System.out.println("leave");
                out.write(EventHandler.leaveHandler(mac));
            } else if (req.equals("forfeit")) {
                // Forfeit a Game request
                String username = request.getParameter("username");
                String mac = request.getParameter("macAddress");
                out.write(EventHandler.forfeitHandler(username, mac));
            } else if (req.equals("login")) {
                // Login request
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                out.write(EventHandler.loginHandler(username, password));
            } else if (req.equals("exchange")) {
                // Exchange Tiles request
                String tiles = request.getParameter("tiles");
                String mac = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.exchangeHandler(mac, tiles));
            } else if (req.equals("pass")) {
                // Pass to Next Player request
                String username = request.getParameter("username");
                out.write(EventHandler.passHandler(username));
            } else if (req.equals("stats")) {
                // Open Stats request
                out.write(EventHandler.statsHandler());
            } else if (req.equals("getboard")) {
                out.write(Session.getSession().getBoardJSON());

            } else if (req.equals("getscore")) {
                String macAddress = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.scoreHandler(macAddress));
            } else if (req.equals("getupdates")) {
                String macAddress = getMACAddress(request.getRemoteAddr());
                String score = EventHandler.scoreHandler(macAddress);
                String turn = EventHandler.turnHandler(macAddress);
                out.write(score + "_" + turn);
            }else if(req.equals("amiregistered")) {
                String mac = getMACAddress(request.getRemoteAddr());
                out.write(EventHandler.amIregisteredHandler(mac));
            }else {
                // Unknown request
                out.write(EventHandler.unknownHandler());
            }
        }
        catch (Exception e) {
            if (out != null)
            {
                out.write("Error in servlet: \n" + e.getMessage());
            }
            e.printStackTrace();
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
