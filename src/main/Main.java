/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import java.net.URL;


public class Main{

	public static void main(String[] a) {
            JFrame window = new JFrame();
            window.setSize(1024,512);
            // window.setBackground(Color.YELLOW); useless
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.getContentPane().add(new Acc());
            window.setVisible(true);
            
        }
}
