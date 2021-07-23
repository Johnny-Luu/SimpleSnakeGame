import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuScreen {
    private JLabel difficulty_forward;
    private JLabel difficulty_back;
    private JLabel mode_back;
    private JLabel mode_forward;
    private JPanel container;
    private JLabel lblDifficulty;
    private JLabel lblMode;
    private JLabel lblColor;
    private JButton btnPlay;
    private JLabel color_back;
    private JLabel color_forward;

    private final String[] arrDifficulty = { "Hard", "Normal", "Easy" };
    private final String[] arrMode = { "Border", "Borderless" };
    private final String[] arrColor = { "Default", "Mixed" };

    int difficulty = 0;
    int mode = 0;
    int color = 0;

    MenuScreen() {
        init();
        clickEventDifficulty();
        clickEventMode();
        clickEventColor();
        clickEventBtnPlay();
    }

    private void init() {
        JFrame frame = new JFrame("Snake Game");
        frame.add(container);
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        lblDifficulty.setText(arrDifficulty[difficulty]);
        lblMode.setText(arrMode[mode]);
        lblColor.setText(arrColor[color]);

        frame.setVisible(true);
    }

    private void clickEventDifficulty() {
        difficulty_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                difficulty--;
                if(difficulty == -1) difficulty = arrDifficulty.length - 1;
                lblDifficulty.setText(arrDifficulty[difficulty]);
            }
        });
        difficulty_forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                difficulty++;
                if(difficulty == arrDifficulty.length) difficulty = 0;
                lblDifficulty.setText(arrDifficulty[difficulty]);
            }
        });
    }

    private void clickEventMode() {
        mode_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mode--;
                if(mode == -1) mode = arrMode.length - 1;
                lblMode.setText(arrMode[mode]);
            }
        });
        mode_forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mode++;
                if(mode == arrMode.length) mode = 0;
                lblMode.setText(arrMode[mode]);
            }
        });
    }

    private void clickEventColor() {
        color_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                color--;
                if(color == -1) color = arrColor.length - 1;
                lblColor.setText(arrColor[color]);
            }
        });
        color_forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                color++;
                if(color == arrColor.length) color = 0;
                lblColor.setText(arrColor[color]);
            }
        });
    }

    private void clickEventBtnPlay() {
        btnPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new GameFrame(arrDifficulty[difficulty], arrMode[mode], arrColor[color]);
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
            }
        });
    }
}
