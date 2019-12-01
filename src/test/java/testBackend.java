import game.ConnectFour;
import org.junit.Assert;
import org.junit.Test;

public class testBackend {

    @Test
    public void testForConstructor(){
        ConnectFour game = new ConnectFour();
        Assert.assertEquals(6, game.getM());
        Assert.assertEquals(7, game.getN());
        Assert.assertEquals(1, game.getPlayer());
        Assert.assertEquals(42, game.getTotalMoves());
    }

    @Test
    public void testForStaticFactory(){
        ConnectFour game = ConnectFour.createWithBoardOf(5,4);
        Assert.assertEquals(5, game.getM());
        Assert.assertEquals(4, game.getN());
        Assert.assertEquals(1, game.getPlayer());
        Assert.assertEquals(20, game.getTotalMoves());
    }

    @Test
    public void testForBuilderPattern(){
        ConnectFour game = new ConnectFour.Builder().setM(5).setN(6).build();
        Assert.assertEquals(5, game.getM());
        Assert.assertEquals(6, game.getN());
        Assert.assertEquals(1, game.getPlayer());
        Assert.assertEquals(1, game.getMode());
        game.setMode(2);
        Assert.assertEquals(2, game.getMode());
        Assert.assertEquals(30, game.getTotalMoves());
    }

    @Test
    public void testForMove(){
        ConnectFour game = new ConnectFour();
        game.move(1,1);
        Assert.assertEquals(-1, game.getPlayer());
    }

    @Test
    public void testForComputerMove(){
        ConnectFour game = new ConnectFour();
        game.setMode(2);
        game.move(1,1);
        Assert.assertEquals(1, game.getPlayer());
    }


}
