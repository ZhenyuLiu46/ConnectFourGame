package game;

/**
 * game.GameApp default as a 7 * 6 board, but supports different m/n parameters.
 * Try with the builder pattern with optional parameters.
 */

public class GameApp {
    public static void main(String[] args) {

        ConnectFour game = new ConnectFour();
//        game.ConnectFour game2 = game.ConnectFour.createWithBoardOf(5,4);
//        game.ConnectFour game = new game.ConnectFour.Builder().build();
//        game.ConnectFour game = new game.ConnectFour.Builder().setM(5).setN(6).build();
//        game.ConnectFour game2 = new game.ConnectFour.Builder().setM(3).setN(4).build();
        new GameView(game);
//        new game.GameView(game2);

    }
}
