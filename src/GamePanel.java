import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGHT = 600;
    final int UNIT_SIZE = 25;
    final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;

    int DELAY = 75;

    final int[] x = new int[GAME_UNIT];
    final int[] y = new int[GAME_UNIT];

    boolean running;
    char pre_direction = 'R';
    char direction = 'R';
    int bodyParts = 6;
    int score;
    int appleX;
    int appleY;

    Timer timer;
    Random rd;

    String DIFFICULTY, MODE, COLOR_CHOOSE;

    GamePanel(String difficulty, String mode, String color) {
        rd = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        switch (difficulty) {
            case "Hard" -> DELAY = 50;
            case "Normal" -> DELAY = 75;
            case "Easy" -> DELAY = 100;
        }

        this.DIFFICULTY = difficulty;
        this.MODE = mode;
        this.COLOR_CHOOSE = color;

        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            //display the current score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score,(SCREEN_WIDTH - metrics.stringWidth("Score: "))/2,g.getFont().getSize());

            //display the apple
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            //display the snake
            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[0],y[0],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    if(COLOR_CHOOSE.equals("Default"))
                        g.setColor(new Color(45,180,0));
                    else if(COLOR_CHOOSE.equals("Mixed"))
                        g.setColor(new Color(rd.nextInt(255),rd.nextInt(255),rd.nextInt(255)));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }

            //display wall in border mode
            if(MODE.equals("Border")) {
                g.setColor(Color.red);
                g.drawLine(0,0,0,SCREEN_HEIGHT);                                                //left
                g.drawLine(SCREEN_WIDTH - 1,0,SCREEN_WIDTH - 1,SCREEN_HEIGHT - 1);          //right
                g.drawLine(0,1,SCREEN_WIDTH - 1,1);                                         //top
                g.drawLine(0,SCREEN_HEIGHT - 1,SCREEN_WIDTH - 1,SCREEN_HEIGHT - 1);         //bottom
            }
        }
        else {
            gameOver(g);
        }
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'L' -> {x[0] = x[0] - UNIT_SIZE;pre_direction=direction;}
            case 'R' -> {x[0] = x[0] + UNIT_SIZE;pre_direction=direction;}
            case 'U' -> {y[0] = y[0] - UNIT_SIZE;pre_direction=direction;}
            case 'D' -> {y[0] = y[0] + UNIT_SIZE;pre_direction=direction;}
        }
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            newApple();
            bodyParts++;
            score++;
        }
    }

    public void newApple() {

        int rx =rd.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        int ry =rd.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

        boolean flag;
        flag = false;

        for (int i = bodyParts; i > 0; i--) {
            if (x[i] == rx && y[i] == ry) {
                flag = true;
                break;
            }
        }
        if (flag)
            //if( rx != UNIT_SIZE || ry != UNIT_SIZE)
            newApple();
        else {
            appleX = rx;
            appleY = ry;
        }

    }

    public void checkCollisions() {
        //check if the snake's head meets the body parts
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        //check if the snake running out of the game's frame
        if(MODE.equals("Border")) {
            if(x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT)
                running = false;
        }

        if(!running) timer.stop();
    }

    //in mode that the snake doesn't die when it touches 4 border of the frame
    public void checkTouchBorder() {
        if(x[0] < 0) x[0] = SCREEN_WIDTH-UNIT_SIZE;
        if(x[0] > SCREEN_WIDTH-UNIT_SIZE) x[0] = 0;
        if(y[0] < 0) y[0] = SCREEN_HEIGHT-UNIT_SIZE;
        if(y[0] > SCREEN_HEIGHT-UNIT_SIZE) y[0] = 0;
    }

    public void gameOver(Graphics g) {
        //show the total score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + score,(SCREEN_WIDTH - metrics.stringWidth("Score: "))/2,SCREEN_HEIGHT/2);

        //show game over notification
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/3);

        //check if the player want to play the new game or not
        this.add(createButtonPlayAgain());
        this.add(createButtonBackToMenu());
    }

    public JButton createButtonPlayAgain() {
        JButton btn = new JButton("Play again?");
        btn.setBackground(Color.green);
        btn.setEnabled(true);
        btn.getFocusListeners();
        btn.setVisible(true);
        btn.setSize(new Dimension(200,50));
        btn.setLocation(50, SCREEN_HEIGHT*2/3);
        btn.addActionListener(e -> {
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
            new GameFrame(DIFFICULTY, MODE, COLOR_CHOOSE);
        });
        return btn;
    }

    public JButton createButtonBackToMenu() {
        JButton btn = new JButton("Back to Menu");
        btn.setBackground(Color.red);
        btn.setForeground(Color.white);
        btn.setEnabled(true);
        btn.getFocusListeners();
        btn.setVisible(true);
        btn.setSize(new Dimension(200,50));
        btn.setLocation(350, SCREEN_HEIGHT*2/3);
        btn.addActionListener(e -> {
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
            new MenuScreen();
        });
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();

            if(MODE.equals("Borderless"))
                checkTouchBorder();

            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(pre_direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(pre_direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(pre_direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(pre_direction != 'U') direction = 'D';
                    break;
            }
        }
    }
}
