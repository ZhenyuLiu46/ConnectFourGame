package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Swing GUI for the game
 */
public class GameView implements GameListener {

    private ConnectFour game;
    private JTextArea textArea;
    private JButton[] buttons;
    private int m;
    private int n;
    private int mode;


    public GameView(ConnectFour game) {
        this.m = game.getM();
        this.n = game.getN();
        textArea = new JTextArea();
        buttons = new JButton[m*n];
        JFrame frame = new JFrame();
        this.game = game;
        gameStart();

        game.addGameListener(this);
        frame.setSize(1000, 1200);
        JPanel panel = new JPanel();

        JPanel mainPanel = new JPanel(new GridLayout(m, n));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JButton restartButton = new JButton("Restart");
        bottomPanel.add(restartButton, BorderLayout.EAST);
        JButton robotButton = new JButton("vs Computer");
        bottomPanel.add(robotButton, BorderLayout.SOUTH);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                restartButtonPressed();
            }
        });

        robotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                RobotButtonPressed();
            }
        });


        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("");
            buttons[i].setPreferredSize(new Dimension(100, 100));
            buttons[i].setForeground(Color.white);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 60));
            final int idx = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonPressed(idx);
                }
            });

            mainPanel.add(buttons[i]);
        }
        panel.add(mainPanel);
        panel.add(bottomPanel);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void restartButtonPressed() {
        game.resetGame();
        game.setMode(1);
        this.mode = 1;

        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
            button.setForeground(Color.white);
        }
    }

    private void RobotButtonPressed() {
        game.resetGame();
        game.setMode(2);
        this.mode = 2;
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
            button.setForeground(Color.white);
        }
    }

    private void buttonPressed(int idx) {
        int i = idx / n;
        int j = idx % n;
        int player = game.move(i,j);
        if(player == -1){ //if next player == -1;
            buttons[idx].setText("X");
            player = 2; // display as 2;
        }
        else{
            buttons[idx].setText("O");
        }
        buttons[idx].setForeground(Color.black);
        buttons[idx].setEnabled(false);
        if(mode != 2 || player != 2){
            textArea.append("Turn: Player " + player + "\n");
        }

    }


    @Override
    public void gameStart() {
        textArea.setText("Game started!\n");
        textArea.append("Player 1 Symbol : X\n");
        textArea.append("Player 2 Symbol : O\n");
        textArea.append("Turn: Player 1 \n");
    }

    @Override
    public void gameFinish() {
        textArea.append("Game is Finished. Nobody Won, Tie !!!!!\n");

    }

    @Override
    public void gameWin(int player) {
        if(player == -1){
            player = 2;
        }
        for(JButton button : buttons){
            button.setEnabled(false);
        }

        textArea.append("Player" + " " + player + " Won!!!!!\n");
        textArea.setEnabled(false);
        textArea.setEditable( false );
    }

    @Override
    public void gameDisplayComputerPlay(int player, int i, int j){
        int idx = n * i + j;
        if(player == -1){
            buttons[idx].setText("X");
            player = 2;
        }
        else{
            buttons[idx].setText("O");
        }
        buttons[idx].setForeground(Color.black);
        buttons[idx].setEnabled(false);
        textArea.append("Turn: Player " + player + "\n");
    }

}
