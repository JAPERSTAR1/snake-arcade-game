import ui.SnakeFrame;
import javax.swing.SwingUtilities;

public class SnakeGame {
   public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> { 
    SnakeFrame frame = new SnakeFrame();
    frame.setVisible(true);
});
    
     



    
   }
}
