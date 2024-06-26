package main;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Playfield {

    int xc=0;
    int yc=0;

    double px = 300, py = 100;
    double pdeltax = 0, pdeltay = 0;
    double pangle = 0;


    int mapX = 8, mapY = 8, mapSize = 64;
    int map[]= {
        1, 1, 1, 1, 1, 1, 1, 1,
        1, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 1, 1, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1,
        1, 1, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 1, 0, 1,
        1, 1, 1, 1, 1, 1, 1, 1
    };

    public void drawMap(Graphics g){
        
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {

                if (map[y * mapX + x] == 1) {
                    g.setColor(Color.white);

                    g.fill3DRect(x*mapSize -1, y*mapSize -1, mapSize , mapSize , true);
                   
                } else {
                    g.setColor(Color.black);

                    g.fill3DRect(x*mapSize -1, y*mapSize -1, mapSize , mapSize , true);
                   
                }
            }
        }

    }
   
    public void renderOffScreen( Graphics g) { 
        // me = g;
        drawMap(g);
        drawPlayer(g);
        drawRays(g);
    } 
         
    public void drawPlayer(Graphics g){

        g.setColor(Color.yellow);	
        
        // g.fillRect(px, py, 10, 10);

        Rectangle2D rect = new Rectangle2D.Double(px, py, 1, 1);
        Graphics2D g2d = (Graphics2D) g; // cast to Graphics2D

        g2d.drawLine((int)px, (int)py, (int)(px+pdeltax*10), (int)(py+pdeltay*10));

        g2d.draw(rect);
        
    }

    public void drawRays(Graphics g) {

        Graphics2D g2d = (Graphics2D) g; // cast to Graphics2D
        g2d.setColor(Color.green);

        double rayAngle = pangle - Math.toRadians(1) * 30;
        double rayX =0, rayY =0;

        double rayXOffset =0, rayYOffset =0;

        double finalDis = 0;

        // controls the FOV
        if (rayAngle < 0) {
            rayAngle += 2*Math.PI;
        }
        if (rayAngle > 2*Math.PI) {
            rayAngle -= 2*Math.PI;
        }

        // map vars 
        int mx, my, mp;
        
                
        for (int r = 0; r < 60; r++) {
            
            // deafualt distnace
            double disH = 10000000, hx = px, hy = py;

           // looking down
            int dof = 0;
            // inverse of tan
            double aTan = -1 / Math.tan(rayAngle);
            if (rayAngle > Math.PI) {

               
                rayY = Math.floor(py / 64.0) * 64.0 - 0.0001;

                rayX = (py - rayY) * aTan + px;
                rayYOffset = -64;
                rayXOffset = -rayYOffset * aTan;
                 

            } if (rayAngle < Math.PI) {

                rayY = Math.floor(py / 64.0) * 64.0 + 64.0;

                rayX = (py - rayY) * aTan + px;
                rayYOffset = 64;
                rayXOffset = -rayYOffset * aTan;

            } if (rayAngle == 0 || rayAngle == Math.PI) {
                System.out.println("ray angle is 0 or pi");
                rayX = px;
                rayY = py;
                dof = 8;               
            } 

            while (dof < 8) {

                mx = (int) (rayX / 64.0);
                my = (int) (rayY / 64.0);

                mp = my * mapX + mx;

                if ( mp >= 0 && mp < mapX * mapY && map[mp] == 1) {
                    
                    hx = rayX;
                    hy = rayY;
                    disH = getDistance(hx, hy, px, py, rayAngle);
                    dof = 8;
                } else {
                    rayX += rayXOffset;
                    rayY += rayYOffset;
                    dof++;
                }
               
            }

            // deafualt distnace
            double disV = 1000000, vx = px, vy = py;
            // vertical 
            dof = 0;
            // neg of tan
            double nTan = -Math.tan(rayAngle);
            // check left
            if (rayAngle > Math.PI/2 && rayAngle < Math.PI*3/2) {

               
                rayX = Math.floor(px / 64.0) * 64.0 - 0.0001;

                rayY = (px - rayX) * nTan + py;
                rayXOffset = -64;
                rayYOffset = -rayXOffset * nTan;
                 

            } if (rayAngle < Math.PI/2 || rayAngle > Math.PI*3/2) {

                rayX = Math.floor(px / 64.0) * 64.0 + 64.0;

                rayY = (px - rayX) * nTan + py;
                rayXOffset = 64;
                rayYOffset = -rayXOffset * nTan;

            } if (rayAngle == 0 || rayAngle == Math.PI) // up or down
            {
                System.out.println("ray angle is 0 or pi");
                rayX = px;
                rayY = py;
                dof = 8;               
            } 

            while (dof < 8) {

                mx = (int) (rayX / 64.0);
                my = (int) (rayY / 64.0);

                mp = my * mapX + mx;

                if (mp > 0 && mp < mapX * mapY && map[mp] == 1) {

                    vx = rayX;
                    vy = rayY;
                    disV = getDistance(vx, vy, px, py, rayAngle);

                    dof = 8;
                } else {
                    rayX += rayXOffset;
                    rayY += rayYOffset;
                    dof++;
                }
               
            }

            // System.out.println("disH: " + disH + " disV: " + disV);

            if (disH < disV) {
                rayX = hx;
                rayY = hy;
                finalDis = disH;

                g2d.setColor(Color.red);

            } if (disH > disV) {
                rayX = vx;
                rayY = vy;
                finalDis = disV;

                g2d.setColor( new Color(153, 0, 0));
            }

           
            // g2d.drawLine((int) px, (int) py, (int) rayX, (int) rayY);


            // DRAW 3d PARTS

            double lineHeight = (mapSize *320) / finalDis;
            if (lineHeight > 320) {
                lineHeight = 320;
            }

            double lineOffest =  160 - (lineHeight / 2);  // line offest

            double ca = pangle - rayAngle;  
            if (ca < 0) {
                ca += Math.PI*2;
            } if (ca > Math.PI*2) {
                ca -= Math.PI*2;
            }
            finalDis = finalDis * Math.cos(ca);

            
            // g2d.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(8));
            g2d.drawLine(r * 8 +530, (int)lineOffest, r*8 + 530, (int)lineHeight + (int)lineOffest);



            rayAngle += Math.toRadians(1);
            if (rayAngle > Math.PI*2) {
                rayAngle -= Math.PI*2;
            }
            if (rayAngle < 0) {
                rayAngle += Math.PI*2;
            }
        }
        
    }

    double getDistance(double ax, double ay, double bx, double by, double angle) {
        return Math.sqrt((bx-ax)*(bx-ax) + (by-ay)*(by-ay));        
    }

    public void keyHandler(KeyEvent e) {
        // System.out.println("key pressed: " + e.getKeyChar());
        if (e.getKeyChar() == 'w') {

            px+=pdeltax;
            py+=pdeltay;
        }
        if (e.getKeyChar() == 's') {

            px-=pdeltax;
            py-=pdeltay;
        }
        if (e.getKeyChar() == 'a') {
           pangle -= 0.1;
           if (pangle < 0) {
               pangle += 2*Math.PI;
           } 
           pdeltax = Math.cos(pangle) *5;
           pdeltay = Math.sin(pangle) *5;
        }
        if (e.getKeyChar() == 'd') {
           pangle += 0.1;
           if (pangle > 2*Math.PI) {
               pangle -= 2*Math.PI;
           } 
           pdeltax = Math.cos(pangle) *5;
           pdeltay = Math.sin(pangle) *5;
        }
    }



}
