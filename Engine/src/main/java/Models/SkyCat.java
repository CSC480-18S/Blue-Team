/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Session.Session;

/**
 *
 * @author wcook
 */
public class SkyCat extends User {
    
    private static int aiCount = 0;

    public SkyCat(Session session) {
        super("SkyCat" + ++aiCount);
    }

    @Override
    public int updateScore(Move move) {
        // Calculate Score
        int points = Session.getSession().calculateMovePoints(move);
        score += points;
        
        // Update score via SQL?
        
        return score;
    }

    @Override
    public String getType() {
        return "AI";
    }
    
}
