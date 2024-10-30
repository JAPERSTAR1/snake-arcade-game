package ui;
import java.io.PrintWriter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JPanel;
public class SnakePanel extends JPanel implements ActionListener{
    public static final int SCREEN_WIDTH = 1500;
    public static final int SCREEN_HEIGHT = 1000;
    public static final int UNIT_SIZE = 50;
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE);
    public static final int DELAY = 75;
    public static final int[] x = new int[GAME_UNITS];
    public static final int[] y = new int[GAME_UNITS];
    public static Image FOOD_IMAGE;
    public static Image backgroundImage;
    public static Image buttonsImage;
    public static Image spaceButtonImage;
    public static Image enterButtonImage;
    public static Image pauseButtonImage;
    public static int bodyParts = 4;
    public static int foodEaten = 0;
    public static int foodX;
    public static int foodY;
    public static int score = 0;
    public static int highScore = 0;
    public static int savedHighScore = 0;
    public static char direction = 'R';
    public static boolean running = true;
    public static Timer timer;
    public static Random random;
    public static boolean paused = false;
    public static boolean WallBlock = true; 
    public static boolean gameStarted  = false;    
    public static File saveFile;  
    public static Scanner scanner;  
    public static Scanner fileScanner;
    public static PrintWriter writer;
    public static Clip sclip;
    public static boolean enableNetwording = true;
    public static boolean startGame = false;
    public static Image moveButtonsImage;
    public SnakePanel() {
        

        saveFile = new File("assets/data/saveFile.txt");
        scanner = new Scanner(System.in);
        if (saveFile.exists()) {
            try {
                fileScanner = new Scanner(saveFile);
                if (fileScanner.hasNextInt()) {
                    savedHighScore = fileScanner.nextInt();
                    if (savedHighScore > highScore) {
                        highScore = savedHighScore;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                saveFile.createNewFile();
                writer = new PrintWriter(saveFile);
                writer.println(highScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.requestFocusInWindow();
        this.setFocusable(true);
       
     
            moveButtonsImage = new ImageIcon("assets/icon/wasd&arrows-Buttons.png").getImage();
            backgroundImage= new ImageIcon("assets/icon/background.jpeg").getImage();
            FOOD_IMAGE = new ImageIcon("assets/icon/apple.png").getImage();
            spaceButtonImage = new ImageIcon("assets/icon/space-Button.png").getImage();
            enterButtonImage = new ImageIcon("assets/icon/enter-Button.png").getImage();
            buttonsImage = new ImageIcon("assets/icon/buttons.png").getImage();
    
        if (backgroundImage == null || FOOD_IMAGE == null || spaceButtonImage == null || enterButtonImage == null) {
            System.err.println("Error loading images. Please check the URLs.");
            System.exit(0);
        }

        try {
        File songFile = new File("assets/audio/Vivace Momentum.wav");
        AudioInputStream songStream = AudioSystem.getAudioInputStream(songFile);
        sclip = AudioSystem.getClip();
        sclip.open(songStream);
        sclip.start();
        sclip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
        e.printStackTrace();
    }


        this.random = new Random(); 

 
        this.initialize();

        this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (paused) {
                paused = false;
                if (timer != null) timer.start();
                repaint();
            }
        }
    });
        
        startGame();
    }

    public void initialize() {
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        this.setLayout(null);
        this.setBounds(200, 0, 1500, 1000);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    public void startGame() {
        if (timer != null) {
        timer.stop(); 
    }
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        this.requestFocusInWindow();
        repaint();   
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(startGame && !paused && running){
        System.out.println(x[0] + " " + y[0]);}
        draw(g);

        
    }

    public void draw(Graphics g) {
        if(!startGame) {
            g.setColor(Color.WHITE);
            g.drawImage(backgroundImage, 0, 0, null);
            g.setFont(new Font("Consolas", Font.BOLD, 50));
            g.drawString("Press move buttons to Start", 400, 300);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Image scaledImage = moveButtonsImage.getScaledInstance(400, 130, Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 500, 390, null);
            g.drawImage(moveButtonsImage, 500, 390, 500, 170, null);
            
            return;
        }
        if (paused) {
            g.drawImage(backgroundImage, 0, 0, null);
            g.setColor(Color.BLACK);
            g.drawImage(FOOD_IMAGE, foodX, foodY, UNIT_SIZE, UNIT_SIZE, null);
            
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Consolas", Font.BOLD, 50));
            g.drawString("Paused", 600, 300);
            g.setFont(new Font("Consolas", Font.BOLD, 30));
            g.drawString("Press P to Resume", 550 ,400);
            g.drawImage(pauseButtonImage, 600, 390, 100, 170, null);
            return;
        }
        if(!running) {
            g.drawImage(backgroundImage, 0, 0, null);
            g.setColor(Color.BLACK);
            g.drawImage(FOOD_IMAGE, foodX, foodY, UNIT_SIZE, UNIT_SIZE, null);
            g.setColor(Color.RED);
            g.setFont(new Font("Consolas", Font.BOLD, 50));
            g.drawString("Game Over", 500, 300);
            g.setFont(new Font("Consolas", Font.BOLD, 30));
            g.drawString("Press Space or Enter to Restart", 380 ,400);
            g.drawImage(spaceButtonImage, 500, 390, 100, 170, null);
            g.drawImage(enterButtonImage, 650, 450, 100, 50, null);
            g.setColor(Color.GREEN);
            g.drawString("Best Score: " + this.highScore, 480, 200);
            return;
        }
        g.drawImage(backgroundImage, 0, 0, null);
        if(enableNetwording){
        g.setColor(Color.BLACK);
       for(int i = 0;i< SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }

        g.drawLine(1499, 0, 1499, SCREEN_HEIGHT);
        
        for(int i = 0;i<SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH , i * UNIT_SIZE);
        }}
        
        g.drawImage(FOOD_IMAGE, foodX, foodY, UNIT_SIZE, UNIT_SIZE, null);
        
        for(int i = 0; i < bodyParts; i++) {
             if(i == 0) {
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    
    }




    public void move() {
        if (!startGame || paused) {
        return; 
    }
        if(!paused){
        for(int i = bodyParts; i>0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
        if(!WallBlock){
        if (x[0] < 0) {
        x[0] = SCREEN_WIDTH - UNIT_SIZE;
    } else if (x[0] >= SCREEN_WIDTH) {
        x[0] = 0;
    }

    if (y[0] < 0) {
        y[0] = SCREEN_HEIGHT - UNIT_SIZE;
    } else if (y[0] >= SCREEN_HEIGHT) {
        y[0] = 0;
    }}}}
    
    public void newFood() {
        boolean foodOnSnake = true;
        while (foodOnSnake) {
            foodOnSnake = false;
            foodX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            foodY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == foodX && y[i] == foodY) {
                    foodOnSnake = true;
                    break;
                }
            }
        }
    }

    public void checkCollision() {
        for(int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
            if(WallBlock){
            if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
                running = false;
            }
                }
              if(!running) {
                    gameOver();
                }  
            }}

        
    

    public void checkFood() {
        if((x[0] == foodX) && (y[0] == foodY)) {
            playEatingSound();
            bodyParts++;
            foodEaten++;
            score += 150;
            if(score > highScore) {
                highScore = score;
            }
            newFood();
        }
    }
    public void gameOver() {
        running = false;
        timer.stop();
        System.out.println("Game Over! You ate " + foodEaten + " food(s).");
        System.out.println("Your final score is: " + (foodEaten * 150));
        this.requestFocusInWindow();
        saveHighScore();

    }

    public void saveHighScore() {
        try {
            writer = new PrintWriter(saveFile);
            if(savedHighScore < highScore) {
            writer.println(highScore);
            writer.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void resetGame() {
    bodyParts = 2;
    foodEaten = 0;
    score = 0;
    direction = 'R';
    paused = false;
    running = true;

    x[0] = 200;
    y[0] = 200;
    for (int i = 1; i < x.length; i++) {
        x[i] = 0;
        y[i] = 0;
    }

    startGame();
}
    public void update() {
        
    }
    public void drawScore(Graphics g) {
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running && startGame){
          
            move();
            checkFood();
            checkCollision();
            update();
            
        } 
            

        if(!running) {
            timer.stop();
            saveHighScore();
        }
          
          repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key Pressed");
            
         switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                        startGame = true;
                        
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                        startGame = true;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                        startGame = true;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                        startGame = true;
                    }
                    break;
                // Additional controls for W, A, S, D keys
                case KeyEvent.VK_W:
                   if(direction != 'D') {
                        direction = 'U';
                        startGame = true;
                    }
                break;

                    case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                        startGame = true;
                    } break;
                    case KeyEvent.VK_A:
                    if(direction != 'R') {  
                        direction = 'L';
                        startGame = true;
                    }   break;
                    case KeyEvent.VK_D:
                    if(direction != 'L') {          
                        direction = 'R';
                        startGame = true;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                   if (!running) {
               paused = false; 
              resetGame();    
              repaint();     
    }
    break;

      
                case KeyEvent.VK_ENTER:
               if (!running) {
               paused = false; 
              resetGame();    
              repaint();     
    }
    break;

                case KeyEvent.VK_P:
    if (running) {
        paused = !paused;
        if (paused) {
            timer.stop(); 
        } else {
            timer.start(); 
        }
        repaint();
    }
    break;
                case KeyEvent.VK_ESCAPE:
                    saveHighScore();
                    System.out.println("Game exited. High score saved.");
                    System.exit(0);
                    break;
                default:
                    break;

            }
        }

        public void keyReleased(KeyEvent e) {
           
        }   
    }

public void playEatingSound() {
    try {
        File soundFile = new File("assets/audio/eating-sound.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    
}