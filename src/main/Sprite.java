package main;

import java.awt.Color;
import java.awt.Graphics;

public class Sprite {

    int state;
    double x;
    double y;
    double z;
    
    public Sprite(int state, double x, double y, double z) {
        this.state = state;
        this.x = x;
        this.y = y;
        this.z = z;
    }







    public void drawSprite(Graphics g, double playerX, double playerY, double playerAngle) {

        double sx = x-playerX;
        double sy = y-playerY;
        double sz = z;

        // rotate to face player
        double rx = sy * Math.cos(playerAngle) +  sx * Math.sin(playerAngle) ;
        double ry = sx * Math.sin(playerAngle) -  sy * Math.cos(playerAngle) ;

        sx = rx;
        sy = ry;

        // convert to X and Y on the screen
        sx = (sx * 108/sy) + (120/2);
        sy = (sx * 108/sy) + (80/2);


        g.setColor(Color.green);
        
        
        int w = 10;
        int h = 10;
        int dx = (int)(w/2*Math.cos(Math.PI/2-Math.atan(sz/Math.sqrt(sx*sx+sy*sy))));
        int dy = (int)(w/2*Math.sin(Math.PI/2-Math.atan(sz/Math.sqrt(sx*sx+sy*sy))));
        g.fillRect((int)sx-dx, (int)sy-dy, w, h);
        
    }
 
}



