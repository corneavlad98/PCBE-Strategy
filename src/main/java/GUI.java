import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class GUI extends JFrame {
    int spacing = 2;
    Color myGreen = new Color(83, 205, 99); // Custom green color
    Color myPurple = new Color(186, 12, 249); // Custom purple color
    Color myBrown = new Color(141, 69, 38); // Custom brown color

    // initialise required arrays
    int[][] wood = new int [32][32];
    int[][] stone = new int [32][32];
    int[][] gold = new int [32][32];

    public int mx, my;

    // o sa trebuiasca sa folosim getter & setters cand lucram la partea de concurenta
   /* public int getMx() {
        return mx;
    }
    public void setMx(int mx) {
        this.mx = mx;
    }
    public int getMy() {
        return my;
    }
    public void setMy(int my) {
        this.my = my;
    }*/

    public void generateWood()
    {
        Random rand = new Random();
        for(int i = 4; i < 28; i++)
        {
            for(int j = 4; j < 28; j++)
            {
                if(rand.nextInt(100) < 22)
                {
                    wood[i][j] = 1;
                }
                else wood[i][j] = 0;
            }
        }
    }
    public void generateStone()
    {
        Random rand = new Random();
        for(int i = 7; i < 24; i++)
        {
            for(int j = 6; j < 24; j++)
            {
                if(rand.nextInt(100) < 20)
                {
                    stone[i][j] = 1;
                }
                else stone[i][j] = 0;
            }
        }
    }
    public void generateGold()
    {
        Random rand = new Random();
        for(int i = 10; i < 22; i++)
        {
            for(int j = 8; j < 22; j++)
            {
                if(rand.nextInt(100) < 12)
                {
                    gold[i][j] = 1;
                }
                else gold[i][j] = 0;
            }
        }
    }

    public GUI(){
        this.setTitle("GAME MAP");
        this.setSize(665, 685);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when we click X button, application closes
        this.setVisible(true);
        this.setResizable(false);

        generateWood();
        generateStone();
        generateGold();

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public class Board extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,700,700);

            for(int i = 0; i < 32; i++)
            {
                for(int j = 0; j < 32; j++)
                {
                    g.setColor(myGreen);
                    if((i == 31 && j ==  0) ||( i == 0 && j == 31))  g.setColor(myPurple);
                    if(gold[i][j] == 0 && stone[i][j] == 0 && wood[i][j] == 1)
                        g.setColor(myBrown);
                    if(gold[i][j] == 0 && stone[i][j] == 1 && wood[i][j] == 0)
                        g.setColor(Color.gray);
                    if(gold[i][j] == 1 && stone[i][j] == 0 && wood[i][j] == 0)
                        g.setColor(Color.yellow);
                    if((mx >= spacing+i*20 + 2) && (mx <= spacing+i*20-2*spacing+22) && (my >= spacing+j*20+20) && (my <= spacing+j*20+38))
                        g.setColor(Color.red);
                    g.fillRect(spacing+i*20, spacing+j*20 ,22-2*spacing, 22-2*spacing);
                }
            }
        }
    }

    public class Move implements MouseMotionListener{
        public void mouseDragged(MouseEvent e) {

        }
        public void mouseMoved(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
        }
    }
    public class Click implements MouseListener{
        public void mouseClicked(MouseEvent e) {
            if(inBoxX() != -1 && inBoxY() != -1){
                System.out.println("Box that was clicked: ["+ inBoxX()+ "," + inBoxY() + "]");
            }
        }
        public void mousePressed(MouseEvent e) {

        }
        public void mouseReleased(MouseEvent e) {

        }
        public void mouseEntered(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {

        }
    }
    public int inBoxX(){
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                if((mx >= spacing+i*20 + 2) && (mx <= spacing+i*20-2*spacing+22) && (my >= spacing+j*20+20) && (my <= spacing+j*20+38))
                    return i;
            }
        }
        return -1;
    }
    public int inBoxY(){
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                if((mx >= spacing+i*20 + 2) && (mx <= spacing+i*20-2*spacing+24) && (my >= spacing+j*20+20) && (my <= spacing+j*20+38))
                    return j;
            }
        }
        return -1;
    }
}
