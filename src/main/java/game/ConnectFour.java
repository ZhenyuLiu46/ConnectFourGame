package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * backend model for the connect Four game. (4 rows or 4 cols counts as win)
 * This game support two players playing or play with a computer.
 * Used Observer, Builder, static factory pattern.
 * Unit test was done for the non GUI-code.
 */
public class ConnectFour {
    private int[][] board;
    private int player;
    private int totalMoves;
    private int m = 6; // default to 6
    private int n = 7; // default to 7
    private int mode = 1; // default 1, play with human(mode : 1); play with Computer(mode : 2).

    private List<GameListener> listeners = new ArrayList<>();

    /**
     * for gameView to addGameListener
     * @param listener GameListener
     */
    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * default constructor will initialize the board 7 * 6 rows.
     */
    public ConnectFour(){
        resetGame();
    }

    /**
     * Constructor with Parameters
     * @param m number of rows
     * @param n number of cols
     */
    public ConnectFour(int m, int n){
        this.m = m;
        this.n = n;
        resetGame();
    }

    /**
     * Static factory method provides game.ConnectFour with parameters
     * @param m number of rows
     * @param n number of cols
     * @return game.ConnectFour
     */
    public static ConnectFour createWithBoardOf(int m, int n) {
        return new ConnectFour(m, n);
    }

    /**
     * Builder class for builder pattern
     */
    public static class Builder{
        private int[][] board;
        private int player;
        private int totalMoves;
        private int mode;
        //optional parameters
        private int m = 6;
        private int n = 7;

        /**
         * constructor for Builder class
         */
        public Builder(){
            board = new int[m][n];
            totalMoves = m*n;
            player = 1;
            mode = 1;
        }

        /**
         * To initialize value of m
         * @param m (number of rows)
         * @return Builder
         */
        public Builder setM(int m){
            this.m = m;
            board = new int[m][n];
            totalMoves = m*n;
            return this;
        }

        /**
         * To initialize value of n
         * @param n (number of cols)
         * @return Builder
         */
        public Builder setN(int n){
            this.n = n;
            board = new int[m][n];
            totalMoves = m*n;
            return this;
        }

        /**
         * To build with customized params
         * @return ConnectFour
         */
        public ConnectFour build(){
            return new ConnectFour(this);
        }
    }

    private ConnectFour(Builder builder){
        this.m = builder.m;
        this.n = builder.n;
        this.board = builder.board;
        this.player = builder.player;
        this.totalMoves = builder.totalMoves;
        this.mode = builder.mode;
        fireGameStartEvent();
    }

    /**
     * resetGame status
     */
    public void resetGame() {
        board = new int[m][n];
        totalMoves = m*n;
        player = 1;
        fireGameStartEvent();
    }

    /**
     * set game mode. mode 1: vs player; mode 2: vs computer.
     * @param mode 1 or 2
     */
    public void setMode(int mode) {
        this.mode = mode;
    }


    /**
     * board[row][col] move
     * @param row int row
     * @param col int col
     * @return next player
     */
    public int move(int row, int col){

        board[row][col] = player;

        if(winCondition(row,col)){
            totalMoves = 42;
            fireGameWonEvent(player);
        }

        totalMoves--;
        if(totalMoves == 0){
            fireGameFinishEvent();
        }

        player = -player; // pass in next player
        if(player == 1 || mode == 1 ){
            return player;
        } else {
            ComputerMove();
            return -player;
        }

    }

    /**
     * get m
     * @return m
     */
    public int getM() {
        return m;
    }

    /**
     * get n
     * @return n
     */
    public int getN() {
        return n;
    }

    /**
     * get player
     * @return player
     */
    public int getPlayer() {
        return player;
    }

    /**
     * get mode
     * @return mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * get totalMoves
     * @return totalMoves (for calculating when the game finish)
     */
    public int getTotalMoves() {
        return totalMoves;
    }


    private void fireGameWonEvent (int player) {
        for (GameListener listener: listeners) {
            listener.gameWin(player);
        }
    }

    private void fireGameStartEvent() {
        for (GameListener listener: listeners) {
            listener.gameStart();
        }
    }
    private void fireGameFinishEvent () {
        for (GameListener listener: listeners) {
            listener.gameFinish();
        }
    }

    private void fireGameDisplayComputerEvent (int player, int i, int j) {
        for (GameListener listener: listeners) {
            listener.gameDisplayComputerPlay(player, i, j);
        }
    }

    /**
     * helper function to help to simulate computer move,
     * looking one step ahead for potential success
     */
    private void ComputerMove(){
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(board[i][j] == 0){
                    board[i][j] = player;
                    if(winCondition(i,j)){
                        //computer move to here;
                        player = -player;
                        fireGameDisplayComputerEvent(player,i,j);
                        fireGameWonEvent(-player);
                        totalMoves = 42; //reset
                        return ;
                    }
                    board[i][j] = 0; //reset
                }
            }

        }
        // otherwise random pick
        int i , j;
        do{
            Random randomI = new Random();
            Random randomJ = new Random();
            i = randomI.nextInt(m);
            j = randomJ.nextInt(n);

        } while(board[i][j] != 0);
        System.out.println(i);
        System.out.println(j);
        board[i][j] = player;

        player = -player;
        fireGameDisplayComputerEvent(player,i,j);

    }

    /**
     * helper function to check if can win the game
     * @param row int row
     * @param col int col
     * @return if can win the game
     */
    private boolean winCondition(int row, int col){

        int[] rows = board[row];
        for(int i=0; i < board[0].length-3; i++ ){
            if(rows[i] == player && rows[i+1] == player && rows[i+2] == player  && rows[i+3] == player){
                return true;
            }
        }
        for(int i=0; i < board.length-3; i++ ){
            if(board[i][col] == player && board[i+1][col] == player && board[i+2][col] == player  && board[i+3][col] == player){
                return true;
            }
        }
        return false;
    }



}
