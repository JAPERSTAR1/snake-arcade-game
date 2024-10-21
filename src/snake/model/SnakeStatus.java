package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import ui.SnakeFrame;
import ui.SnakePanel;

import java.net.MalformedURLException;
import java.net.URL;
public class SnakeStatus extends JPanel implements ActionListener {
    private Timer timer;
    JSlider volumeSlider;
    FloatControl gainControl;
    ImageIcon soundIcon;
    ImageIcon noSoundIcon;
    ImageIcon networkIcon;
    ImageIcon nonetworkIcon;
    ImageIcon wallIcon;
    ImageIcon noWallIcon;
    // SnakePanel snakePanel;
    ImageIcon informationIcon;

    // public SnakeStatus(SnakePanel snakePanel){
    //     this.snakePanel = snakePanel;
    // }
   
    public SnakeStatus() {
       
            informationIcon = new ImageIcon("assets/icon/info.png");
            soundIcon       = new ImageIcon("assets/icon/sound.png");
            noSoundIcon     = new ImageIcon("assets/icon/muted-Sound.png");
            networkIcon     = new ImageIcon("assets/icon/striped-Background.png");
            nonetworkIcon   = new ImageIcon("assets/icon/unstriped-Background.png");
            wallIcon        = new ImageIcon("assets/icon/collidable.png");
            noWallIcon      = new ImageIcon("assets/icon/uncollidable.png");
       
        this.setLayout(null);
        this.setBounds(0, 0, 200, 1000);
        this.setBackground(new Color(24, 25, 28));
        this.setVisible(true);
        this.setFocusable(true);
        JButton wallButton = new JButton(); 
        wallButton.setIcon(wallIcon);
        wallButton.setBounds(10, 650, 180, 40);
        wallButton.setBackground(Color.WHITE);
        wallButton.setForeground(Color.WHITE);
        wallButton.setFocusPainted(false);
        wallButton.setBorderPainted(false);
        wallButton.setContentAreaFilled(false);
        wallButton.setOpaque(true);
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
        volumeSlider.setBounds(10, 500, 180, 40);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.updateUI();
        volumeSlider.setBackground(new Color(24, 25, 28));
        volumeSlider.setForeground(Color.WHITE);
        JButton spiderButton = new JButton(); 
        spiderButton.setIcon(networkIcon);
        spiderButton.setBounds(10, 600, 180, 40);
        spiderButton.setBackground(Color.WHITE);
        spiderButton.setForeground(Color.WHITE);
        spiderButton.setFocusPainted(false);
        spiderButton.setBorderPainted(false);
        spiderButton.setContentAreaFilled(false);
        spiderButton.setOpaque(true);
        JButton soundButton = new JButton();
        soundButton.setBounds(10, 550, 180, 40);
        soundButton.setBackground(Color.WHITE);
        soundButton.setForeground(Color.WHITE);
        soundButton.setIcon(soundIcon);
        soundButton.setFocusPainted(false);
        soundButton.setBorderPainted(false);
        soundButton.setContentAreaFilled(false);
        soundButton.setOpaque(true);
        JButton informationButton = new JButton();
        informationButton.setBounds(10, 950, 180, 40);
        informationButton.setBackground(new Color(24, 25, 28));
        informationButton.setForeground(Color.WHITE);
        informationButton.setIcon(informationIcon);
        informationButton.setFocusPainted(false);
        informationButton.setBorderPainted(false);
        informationButton.setContentAreaFilled(false);
        informationButton.setOpaque(true);
        volumeSlider.addChangeListener(e -> {
            int value = volumeSlider.getValue();
            if (SnakePanel.sclip != null) {
                gainControl = (FloatControl) SnakePanel.sclip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (20 * Math.log10(value / 100.0));
                gainControl.setValue(dB);
                SnakePanel.paused = true; 
                SnakeFrame.snakePanel.requestFocusInWindow();
                
            }
        });
        soundButton.addActionListener(e -> {
            if (SnakePanel.sclip != null) {
                if (SnakePanel.sclip.isRunning()) {
                    SnakePanel.sclip.stop();
                    soundButton.setIcon(noSoundIcon);
                } else {
                    SnakePanel.sclip.start();
                    soundButton.setIcon(soundIcon);
                    
                }
            }
            SnakePanel.paused = true;
            SnakeFrame.snakePanel.requestFocusInWindow();
        });
        spiderButton.addActionListener(e -> {
            if(SnakePanel.enableNetwording) {
                SnakePanel.enableNetwording = false;
                spiderButton.setIcon(nonetworkIcon);
            } else {
                SnakePanel.enableNetwording = true;
                spiderButton.setIcon(networkIcon);

            }
            SnakePanel.paused = true;
            SnakeFrame.snakePanel.requestFocusInWindow();
        });
        wallButton.addActionListener(e -> {
            if(SnakePanel.WallBlock) {
                SnakePanel.WallBlock = false;
                wallButton.setIcon(noWallIcon);
            } else {
                SnakePanel.WallBlock = true;
                wallButton.setIcon(wallIcon);
            }
            SnakePanel.paused = true;
            SnakeFrame.snakePanel.requestFocusInWindow();
        });
        informationButton.addActionListener(e -> {
            String message = 
                    "1. Use arrow keys to control the snake.\n" +
                    "2. Eat food to grow the snake and increase your score.\n" +
                    "3. Avoid colliding with walls or yourself.\n" +
                    "4. Press 'P' to pause the game.\n" +
                    "5. Press 'Esc' to exit the game.\n" +
                    "6. Enjoy the game!";
             SnakePanel.paused = true;       
             JOptionPane jop = new JOptionPane();
             jop.showMessageDialog(null, message + "\n\n This game was created by:  JAPERSTAR1", "Game Information", JOptionPane.INFORMATION_MESSAGE);
             if(jop.getValue() != null) {
                SnakeFrame.snakePanel.requestFocusInWindow();
             }
             
    });
        this.add(informationButton);
        this.add(wallButton);
        this.add(spiderButton);
        this.add(soundButton);
        this.add(volumeSlider);
        timer = new Timer(100, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Volume Level: ",40,450);
        g.drawString("Snake Game Status :", 5, 30);
        g.drawString("Score: " + SnakePanel.score, 5, 45);
        g.drawString("Body Parts: " + SnakePanel.bodyParts, 5, 75);
        
        g.drawString("Food Eaten: " + SnakePanel.foodEaten, 5, 60);
        g.drawString("Food Pos: (" + SnakePanel.x[0] + ", " + SnakePanel.y[0] + ")", 5, 90);
        g.drawString("High Score: " + SnakePanel.highScore, 5, 105);
        g.drawString("Game Status: " + (SnakePanel.running ? "Running" : "Paused"), 5, 120);
        g.drawString("Paused: " + (SnakePanel.paused ? "Yes" : "No"), 5, 135);
        g.drawString("Snake Direction: " + SnakePanel.direction, 5, 150);
        g.drawString("Snake Length: " + SnakePanel.bodyParts, 5, 165);
        g.setColor(Color.RED);
        g.draw3DRect(21, 780, 140, 30, true);
        g.drawString("Press esc to Close", 25, 800); 
        g.setColor(Color.YELLOW);
        g.draw3DRect(21, 880, 140, 30, true);
        g.drawString("Press P to Pause !", 25, 900);   
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); 
    }
}