import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements Runnable{
    public GameFrame(String frameName)
    {
        // set windows properties
        this.setTitle(frameName);
        //this.setSize(665, 685);
        this.setSize(1320, 785);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when we click X button, application closes
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new GridLayout(0,2));

        // make 2 instances of the board for 2 JPanels; they will have the same resources arrays
        Board board = Board.getInstance("board 1");
        Board board2 = Board.getInstance("board 2");

        board.generatePlayers();
        board.generateWood();
        board.generateStone();
        board.generateGold();

        board2.generatePlayers();
        board2.generateWood();
        board2.generateStone();
        board2.generateGold();

        //this.setContentPane(board);
        this.add(board);
        this.add(board2);

    }
    @Override
    public void run() {
        while(true)
            this.repaint();
    }


}
