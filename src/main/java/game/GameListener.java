package game;

/**
 * public interface for the game listener
 */
public interface GameListener {
    void gameStart();
    void gameFinish();
    void gameWin(int player);
    void gameDisplayComputerPlay(int player, int i, int j);
}

