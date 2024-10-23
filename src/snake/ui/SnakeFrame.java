package ui;
import ui.SnakePanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.SnakeStatus;

import java.awt.event.MouseEvent;

public class SnakeFrame extends JFrame {
private Point initialClick;
public static SnakePanel snakePanel;

    public SnakeFrame() {
        this.initialize();
    }

    public void initialize() {
        SnakeStatus statusPanel = new SnakeStatus();
        snakePanel = new SnakePanel();
        this.add(statusPanel);
        this.add(snakePanel);
        this.setUndecorated(true);
        this.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            snakePanel.requestFocusInWindow();
        }
    });
        this.setTitle("Snake Game");
    
        this.setIconImage(new ImageIcon("assets/icon/snake-Icon.png").getImage());
     
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1690, 990);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
            this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
            snakePanel.requestFocusInWindow();
        }

        //this.pack();
    });
    enableDragToMove();
    
}
private void enableDragToMove() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
    }


}
