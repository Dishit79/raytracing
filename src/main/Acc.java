/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.*;

import java.io.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.sound.sampled.*;
 

class Acc extends JPanel {
   
    Graphics me = null; 	
    private Timer timer = new Timer();
    private Image offScreenImage = null;
    private Graphics offScreenGraphics = null;

    Playfield pf = new Playfield();
  
   
    public class MyKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
            pf.keyHandler(e);         
        }
		public void keyReleased(KeyEvent e) {}
	}
    public class MyMouseListener implements MouseListener {
        	public void mouseClicked (MouseEvent mouseEvent) {} 
            public void mouseEntered (MouseEvent mouseEvent) {} 
            public void mousePressed (MouseEvent mouseEvent) {} 
            public void mouseReleased (MouseEvent mouseEvent) {}  
            public void mouseExited (MouseEvent mouseEvent) {}  
	}

    
    Acc(){
        timer.schedule(new MyTimer(), 0, 1); //1 ms
        KeyListener listener = new MyKeyListener();
        MouseListener mouselistener = new MyMouseListener();
        addKeyListener(listener);
        addMouseListener(mouselistener);
            
        setFocusable(true);
        // initialize variables here
      
    }
    
    /****************************************
    * this function is called from the paint
    * function to make the code neater.  This
    * function will draw everything off screen
    * and the paint function will copy the 
    * offscreen graphics to the on screen graphics
    ********************************************/

 
    /********************************************* 
     This timer class uses the built in function
     to create a delay... it's nicer as a function
     so it runs in the background calling the
     paint function 
     **********************************************/
    private class MyTimer extends java.util.TimerTask { 
        public void run() { 
           // Run thread on event dispatching thread 
           if (!EventQueue.isDispatchThread()) { 
              EventQueue.invokeLater(this); 
           } else { 
              if (Acc.this != null) { 
                  Acc.this.repaint();                         
              } 
           } 
      } // End of Run 
   }         


   

    /**********************************************
     *  the paint function is called based on the
     *  delay used in the constructor instantiation of
     *  Timer class
     ********************************************/
    public void paint(Graphics g) {
        final Dimension d = getSize(); 

        if (offScreenImage == null) {    
             // Double-buffer: clear the offscreen image.                 
            offScreenImage = createImage(d.width, d.height);    
        }         
        offScreenGraphics  = offScreenImage.getGraphics();      
        offScreenGraphics.setColor(Color.gray); 
        offScreenGraphics.fillRect(0, 0, d.width, d.height) ;   

       
        pf.renderOffScreen(offScreenGraphics);


        // Game game = new Game();
        // game.drawStartingScreen(offScreenGraphics);

        Sprite s = new Sprite( 0, (1.5 *65), (5 * 65), 20);
        s.drawSprite(offScreenGraphics, pf.px, pf.py, pf.pangle);





        g.drawImage(offScreenImage, 0, 0, null);
        }
    }