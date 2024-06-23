package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class Game {

    boolean gameStarted = false;

    public static void main(String[] args) {
    }

    public void drawStartingScreen(Graphics g) {

        g.setColor(Color.black);
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        String title = "ICS4U DOOM!";
        g.drawString(title, (int) (1024 / 2 - g.getFontMetrics().stringWidth(title) / 2), 100);

        
        
        g.setColor(Color.WHITE);
        Font textFont = new Font("Arial", Font.PLAIN, 16);
        g.setFont(textFont);
        String text = "Press Enter to Start";
        g.drawString(text, (int) (1024 / 2 - g.getFontMetrics().stringWidth(text) / 2), 200);
            
    }


    public void keyHandler(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.gameStarted = true;
        }

    }

}
