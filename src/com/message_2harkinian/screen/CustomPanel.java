package com.message_2harkinian.screen;
import java.awt.AlphaComposite;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import com.message_2harkinian.main.Interpreter;



public class CustomPanel extends JPanel implements MouseListener, MouseMotionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;
    private BufferedImage exitButtonBufferedImage, minimizeButtonBufferedImage, logoAreaBufferedImage, configsButtonBufferedImage,
    					selectFileButtonBufferedImage, startButtonBufferedImage, versionLogoBufferedImage, githubLogoBufferedImage,
    					bloggerLogoBufferedImage;
    private TexturePaint exitButtonTexture, minimizeButtonTexture, logoAreaTexture, configsButtonTexture, selectFileButtonTexture, startButtonTexture,
    					versionLogoTexture, githubLogoTexture, bloggerLogoTexture;
    private Polygon exitButton, minimizeButton, logoArea, configsButton, selectFileButton, startButton, versionLogo, githubLogo, bloggerLogo;
    private JFrame window;
    private int initialClickX, initialClickY;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JFileChooser fileChooser;
	private Interpreter interpreter;
	private boolean isWorking = false; // Verify if the Interpreter is working.
	
    public CustomPanel (JFrame window) {
        super();
        this.window = window;
        addMouseListener(this);
        addMouseMotionListener(this);

		String backgroundImageFile = "/assets/background.png";
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(backgroundImageFile);
			backgroundImage = ImageIO.read(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        
        // creation of interactive elements
        // points reference:
        // top right corner; top-left corner; bottom left corner; bottom right corner
        
        int[] exitButtonXPoints = { 538, 599, 599, 538 };
        int[] exitButtonYPoints = { 75, 65, 136, 145 };
        exitButton = new Polygon(exitButtonXPoints, exitButtonYPoints, exitButtonXPoints.length);
        exitButtonClicked(false);

        int[] minimizeButtonXPoints = { 533, 472, 472, 533 };
        int[] minimizeButtonYPoints = { 75, 84, 155, 146 };
		minimizeButton = new Polygon(minimizeButtonXPoints, minimizeButtonYPoints, minimizeButtonXPoints.length);
		minimizeButtonClicked(false);

        int[] logoAreaXPoints = { 595, 23, 27, 596 };
        int[] logoAreaYPoints = { 64, 0, 151, 65 };
        logoArea = new Polygon(logoAreaXPoints, logoAreaYPoints, logoAreaXPoints.length);
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream("/assets/blank.png");
			logoAreaBufferedImage = ImageIO.read(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		logoAreaTexture = new TexturePaint(logoAreaBufferedImage, new Rectangle2D.Double(0, 0,
				logoAreaBufferedImage.getWidth(), logoAreaBufferedImage.getHeight()));
		
		// points reference:
		// top right corner; top left corner; bottom left corner; bottom right corner

		int[] configsButtonXPoints = { 232, 80, 80, 232 };
        int[] configsButtonYPoints = { 260, 282, 351, 331 };
        configsButton = new Polygon(configsButtonXPoints, configsButtonYPoints, configsButtonXPoints.length);
        configsButtonClicked(false);
		
		int[] selectFileButtonXPoints = { 232 + 160, 80 + 160, 80 + 160, 232 + 160 };
        int[] selectFileButtonYPoints = { 260 - 22, 282 - 22, 351 - 21, 331 - 22};
        selectFileButton = new Polygon(selectFileButtonXPoints, selectFileButtonYPoints, selectFileButtonXPoints.length);
        selectFileButtonClicked(false);

		int[] startButtonXPoints = { 392 + 160, 240 + 160, 240 + 160, 392 + 160};
        int[] startButtonYPoints = { 238 - 22, 260 - 22, 330 - 21, 309 - 22};
        startButton = new Polygon(startButtonXPoints, startButtonYPoints, startButtonXPoints.length);
        startButtonClicked(false);
        
        int[] versionLogoXPoints = {602, 523, 523, 602};
        int[] versionLogoYPoints = {583, 583, 640, 640};
        versionLogo = new Polygon(versionLogoXPoints, versionLogoYPoints, versionLogoXPoints.length);
        versionLogoClicked(false);
        
        // hyperlinks buttons
        
        int[] githubLogoXPoints = {392 + 24, 391, 396, 421};
        int[] githubLogoYPoints = {65 + 3, 75 - 4, 95, 92};
        githubLogo = new Polygon(githubLogoXPoints, githubLogoYPoints, githubLogoXPoints.length);
        try {
        	InputStream inputStream = CustomPanel.class.getResourceAsStream("/assets/githubLogo.png");
        	githubLogoBufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        githubLogoTexture = new TexturePaint(githubLogoBufferedImage, new Rectangle2D.Double(41, 68,
        		githubLogoBufferedImage.getWidth(), githubLogoBufferedImage.getHeight()));
        
        int[] bloggerLogoXPoints = {449, 424, 428, 453};// top right corner; top left corner; bottom left corner; bottom right corner
        int[] bloggerLogoYPoints = {68 - 6, 68 - 3, 92 - 1, 92 - 4}; //{68, 71, 95, 92};
        bloggerLogo = new Polygon(bloggerLogoXPoints, bloggerLogoYPoints, bloggerLogoXPoints.length);
        try {
        	InputStream inputStream = CustomPanel.class.getResourceAsStream("/assets/bloggerLogo.png");
        	bloggerLogoBufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        bloggerLogoTexture = new TexturePaint(bloggerLogoBufferedImage, new Rectangle2D.Double(46, 63,
        		bloggerLogoBufferedImage.getWidth(), bloggerLogoBufferedImage.getHeight()));
        
        // textArea
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(175 + 18, 390, 252, 175);
        scrollPane.setVisible(false);
        scrollPane.setEnabled(false);
        
        this.add(scrollPane);
        
        // FileChooser
        
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                // show only directories
                if (f.isDirectory()) {
                    return true;
                }
                // permits only the file "message_data.h"
                return f.getName().equalsIgnoreCase("message_data.h");
            }

            @Override
            public String getDescription() {
                return "Dialog file \"message_data.h\"";
            }
        });
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        
   
     					
     		
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (logoArea.contains(e.getPoint())) {
            initialClickX = e.getXOnScreen();
            initialClickY = e.getYOnScreen();
        }
        
        if (minimizeButton.contains(e.getPoint())) {
        	minimizeButtonClicked(true);
        }
        
		if (!isWorking) {

			if (exitButton.contains(e.getPoint())) {
				exitButtonClicked(true);
			}

			if (configsButton.contains(e.getPoint()) && !isWorking) {
				configsButtonClicked(true);
			}
			if (selectFileButton.contains(e.getPoint()) && !isWorking) {
				selectFileButtonClicked(true);
			}

			if (startButton.contains(e.getPoint()) && !isWorking) {
				startButtonClicked(true);
			}
		}
        
        if (versionLogo.contains(e.getPoint())) {
        	versionLogoClicked(true);
        }
        
        if (githubLogo.contains(e.getPoint())) {
        	try {
				Desktop.getDesktop().browse(new URI("https://github.com/Srchronotrigger/message-2harkinian"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
        }
        
        if (bloggerLogo.contains(e.getPoint())) {
        	try {
				Desktop.getDesktop().browse(new URI("http://blogdochrono.blogspot.com/"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
        }
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!isWorking) {
		if (!exitButton.contains(e.getPoint())) {
			exitButtonClicked(false);
		} else {
			exitButtonClicked(true);
			Timer timer = new Timer(45, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					window.dispose();
					System.exit(0);
					((Timer) ae.getSource()).stop();
				}
			});
			timer.setRepeats(false);
			timer.start();
		}

		// configs Button functions
		if (!configsButton.contains(e.getPoint())) {
			configsButtonClicked(false);
		} else {
			configsButtonClicked(true);
			JOptionPane.showMessageDialog(window, "function planned for the next version");
			configsButtonClicked(false);
		}
		// select file Button functions
		if (!selectFileButton.contains(e.getPoint())) {
			selectFileButtonClicked(false);
		} else {
			selectFileButtonClicked(true);
			if (fileChooser.showOpenDialog(fileChooser) == 0) {
				scrollPane.setVisible(true);
				scrollPane.setEnabled(true);
				textArea.setText(null); // Clean the textArea
				textArea.append("message_data.h selected!\nPress start to initialize the conversion.");
				selectFileButtonClicked(false);
			} else {
				selectFileButtonClicked(false);
			}
		}
		// start Button functions
		if (!startButton.contains(e.getPoint())) {
			startButtonClicked(false);
        } else {
        	if (startButton.contains(e.getPoint())) {
        		startButtonClicked(true);
        		textArea.setText(null);
				// the SwingWorker is necessary to update the textArea in realtime.
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						// Functions on second plan.
						isWorking = true;
						interpreter = new Interpreter(fileChooser.getSelectedFile(), textArea);
						interpreter.Start();
						return null;
					}

					@Override
					protected void done() {
						isWorking = false;
					}
				};

				worker.execute(); // Start the execution of the SwingWorker.
            }
        	startButtonClicked(false);
		}
	} else {
		JOptionPane.showMessageDialog(window, "Wait the conclusion of the process.");
	}
		
		if (!minimizeButton.contains(e.getPoint())) {
			minimizeButtonClicked(false);

		} else {

			Timer timer = new Timer(45, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					window.setExtendedState(JFrame.ICONIFIED);
					((Timer) ae.getSource()).stop();
				}
			});
			timer.setRepeats(false);
			timer.start();

		}
		
		if (!versionLogo.contains(e.getPoint())) {
			versionLogoClicked(false);
		} else {
			versionLogoClicked(true);
			JOptionPane.showMessageDialog(window, "Message 2Harkinian\n" + "Beta v0.1 build july-08-2024\n"
			+ "by: Srchronotrigger\n\n" + "Special thanks:\nCabeça de piu-piu: for the help\nwith blender.\n\n"
			+ "\"Commit your work to the Lord,\n" + "and your plans will be established.\"\n" + "Proverbs 16:3.");
			versionLogoClicked(false);
		}
		
	}


	

	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(backgroundImage, 0, 0, this);
        g2d.scale(0.65f, 0.65f);
        
        g2d.scale(1/0.65f, 1/0.65f);
        g2d.setPaint(exitButtonTexture);
        g2d.fill(exitButton);
        
        g2d.setPaint(minimizeButtonTexture);
        g2d.fill(minimizeButton);
        
        g2d.setPaint(logoAreaTexture);
        g2d.fill(logoArea);
        
        g2d.setPaint(configsButtonTexture);
        g2d.fill(configsButton);
        
        g2d.setPaint(selectFileButtonTexture);
        g2d.fill(selectFileButton);
        
        g2d.setPaint(startButtonTexture);
        g2d.fill(startButton);
        
        g2d.setPaint(versionLogoTexture);
        g2d.fill(versionLogo);
        
        g2d.setPaint(githubLogoTexture);
        g2d.fill(githubLogo);
        
        g2d.setPaint(bloggerLogoTexture);
        g2d.fill(bloggerLogo);
        
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (logoArea.contains(e.getPoint())) {
            // it's gets the actual coords of the mouse on screen
            int currentX = e.getXOnScreen();
            int currentY = e.getYOnScreen();

            // calculates the difference between the actual position and initial position
            int deltaX = currentX - initialClickX;
            int deltaY = currentY - initialClickY;

            // move the window through the calculated difference
            window.setLocation(window.getX() + deltaX, window.getY() + deltaY);

            // update the initial coords
            initialClickX = currentX;
            initialClickY = currentY;
        }
    }
    
	@Override
	public void windowDeiconified(WindowEvent e) {
		// restore the minimize button
		minimizeButtonClicked(false);
		
	}
	
	// Animations logic
	private void exitButtonClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/exitButtonClicked.png";
		} else {
			state = "/assets/exitButtonNonClicked.png";
		}
		
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
			exitButtonBufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
        exitButtonTexture = new TexturePaint(exitButtonBufferedImage, new Rectangle2D.Double(49, 68,
                exitButtonBufferedImage.getWidth(), exitButtonBufferedImage.getHeight()));
        repaint();
	}
	
	private void minimizeButtonClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/minimizeButtonClicked.png";
		} else {
			state = "/assets/minimizeButtonNonClicked.png";
		}
		
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
            minimizeButtonBufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        minimizeButtonTexture = new TexturePaint(minimizeButtonBufferedImage, new Rectangle2D.Double(45, 77,
                minimizeButtonBufferedImage.getWidth(), minimizeButtonBufferedImage.getHeight()));
        repaint();
	}
	
	private void configsButtonClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/configsButtonClicked.png";
		} else {
			state = "/assets/configsButtonNonClicked.png";
		}
		
    	try {
    		InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
    		configsButtonBufferedImage = ImageIO.read(inputStream);
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	configsButtonTexture = new TexturePaint(configsButtonBufferedImage, new Rectangle2D.Double(80, 80,
    			configsButtonBufferedImage.getWidth(), configsButtonBufferedImage.getHeight()));
    	repaint();
	}
	
	private void selectFileButtonClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/selectFileButtonClicked.png";
		} else {
			state = "/assets/selectFileButtonNonClicked.png";
		}
		
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
    		selectFileButtonBufferedImage = ImageIO.read(inputStream);
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	selectFileButtonTexture = new TexturePaint(selectFileButtonBufferedImage, new Rectangle2D.Double(88, 148,
    			selectFileButtonBufferedImage.getWidth(), selectFileButtonBufferedImage.getHeight()));
    	repaint();
	}
	
	public void startButtonClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/startButtonClicked.png";
		} else {
			state = "/assets/startButtonNonClicked.png";
		}
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
        	startButtonBufferedImage = ImageIO.read(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        startButtonTexture = new TexturePaint(startButtonBufferedImage, new Rectangle2D.Double(94, 126,
        		startButtonBufferedImage.getWidth(), startButtonBufferedImage.getHeight()));
    	repaint();
	}
	
	public void versionLogoClicked(boolean isClicked) {
		String state = "";
		if (isClicked) {
			state = "/assets/versionLogoClicked.png";
		} else {
			state = "/assets/versionLogoNonClicked.png";
		}
		
		try {
			InputStream inputStream = CustomPanel.class.getResourceAsStream(state);
			versionLogoBufferedImage = ImageIO.read(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		versionLogoTexture = new TexturePaint(versionLogoBufferedImage, new Rectangle2D.Double(37, 12.5,
				versionLogoBufferedImage.getWidth(), versionLogoBufferedImage.getHeight()));
		repaint();
	}
	
	
	// inherit methods
	
	@Override
    public void mouseClicked(MouseEvent e) {
        
    }
	
	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	

}
