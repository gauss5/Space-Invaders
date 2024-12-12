import javax.swing.*;

public class Main {
    public static void main(String[] args) {
       
        JFrame frame = new JFrame("Space Invaders");
        Game gamePanel = new Game();

        frame.add(gamePanel); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); 
        frame.setResizable(false); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
    }
}
