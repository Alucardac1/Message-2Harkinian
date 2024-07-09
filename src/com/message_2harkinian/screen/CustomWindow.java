package com.message_2harkinian.screen;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class CustomWindow implements Runnable{

	public CustomWindow() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
    private void createAndShowGUI(){
        JFrame window = new JFrame();
        window.setUndecorated(true);
        
        // Instantiates the CustomPanel class, passing the window Jframe as param.
        CustomPanel panel = new CustomPanel(window);
        
        
        // panel and window definitions
        panel.setLayout(null); // Define the layout as null for absolute positions of elements.
        panel.revalidate();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(panel);
        window.setBackground(new Color(0, 0, 0, 0)); // makes the background translucid.
        window.setLayout(new FlowLayout());
        window.setAlwaysOnTop(false);
        window.pack();
        window.setLocationRelativeTo(null); // center window on screen.
        window.addWindowListener(panel); // adds the customPanel as listener for allow clickable elements.
        window.setVisible(true);
    }

	@Override
	public void run() {
		createAndShowGUI();
	}
}
