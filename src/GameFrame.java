import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(String difficulty, String mode, String color) {
        this.add(new GamePanel(difficulty, mode, color));
        this.setTitle("Snake Game");
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
