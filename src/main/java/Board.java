import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

// singleton class
public class Board extends JPanel {
    public int spacing = 2;
    Color myGreen = new Color(83, 205, 99); // Custom green color
    Color myBrown = new Color(141, 69, 38); // Custom brown color
    Color player1Color = new Color(218, 6, 233); // Custom purple color
    Color player2Color = new Color(31, 44, 218); // Custom blue color

    public int mouseX, mouseY;

    int[][] map = new int[32][32];
    int[][] wood = new int[32][32];
    int[][] stone = new int[32][32];
    int[][] gold = new int[32][32];
    boolean[][] clicked = new boolean[32][32];
    private static int[] woodArray;
    private static int[] stoneArray;
    private static int[] goldArray;

    private String s;

    private Board(String testString) {
        s = testString;
        System.out.println(s);
        this.addMouseMotionListener(new MouseMotionListener() {
           @Override
           public void mouseDragged(MouseEvent e) {

           }
           @Override
           public void mouseMoved(MouseEvent e) {
                mouseX=e.getX();
                mouseY=e.getY();
           }
       });
       this.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {
               if (inBoxX() != -1 && inBoxY() != -1) {
                   for (int i = 0; i < 32; i++) {
                       for (int j = 0; j < 32; j++) {
                           if (i == inBoxX() && j == inBoxY()) {
                               clicked[i][j] = true;
                               System.out.println("Box that was clicked: [" + inBoxX() + "," + inBoxY() + "]");
                           } else clicked[i][j] = false;
                       }
                   }
               }
           }
           @Override
           public void mousePressed(MouseEvent e) {

           }
           @Override
           public void mouseReleased(MouseEvent e) {

           }
           @Override
           public void mouseEntered(MouseEvent e) {

           }
           @Override
           public void mouseExited(MouseEvent e) {

           }
       });
       new Thread(() -> {
           while(true) {
               this.repaint();
           }
       }).start();
    }

    public static Board getInstance(String testString) {

        if(woodArray==null){
            woodArray = generateRandomArray(530);
        }
        if(stoneArray==null){
            stoneArray = generateRandomArray(325);
        }
        if(goldArray==null){
            goldArray = generateRandomArray(325);
        }
        return new Board(testString);
    }

    public void generatePlayers() {
        map[0][31] = 1;
        map[31][0] = 2;
    }

    private static int[] generateRandomArray(int size) {
        int[] randArray = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            randArray[i] = rand.nextInt(100);
        }
        return randArray;
    }

    public void generateWood() {
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                if (woodArray[i*23 + j] < 30) {
                    wood[i+4][j+3] = 1;
                } else wood[i+4][j+3] = 0;
            }
        }
    }

    public void generateStone() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (stoneArray[i*15 + j] < 23) {
                    stone[i+6][j+6] = 1;
                } else stone[i+6][j+6] = 0;
            }
        }
    }

    public void generateGold() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (goldArray[i*10 + j] < 11) {
                    gold[i+7][j+5] = 1;
                } else gold[i+7][j+5] = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        //g.fillRect(0,0,700,700);
        g.fillRect(0, 0, 700, 800);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                g.setColor(myGreen);
                if (map[i][j] == 1 && gold[i][j] == 0 && stone[i][j] == 0 && wood[i][j] == 0)
                    g.setColor(player1Color);
                if (map[i][j] == 2 && gold[i][j] == 0 && stone[i][j] == 0 && wood[i][j] == 0)
                    g.setColor(player2Color);
                if (gold[i][j] == 0 && stone[i][j] == 0 && wood[i][j] == 1)
                    g.setColor(myBrown);
                if (gold[i][j] == 0 && stone[i][j] == 1 && wood[i][j] == 0)
                    g.setColor(Color.gray);
                if (gold[i][j] == 1 && stone[i][j] == 0 && wood[i][j] == 0)
                    g.setColor(Color.yellow);
                if(clicked[i][j])
                    g.setColor(Color.WHITE);
                if((mouseX >= spacing+i*20 ) && (mouseX <= spacing+i*20-2*spacing+16) && (mouseY >= spacing+j*20) && (mouseY <= spacing+j*20+12))
                    g.setColor(Color.red);

                g.fillRect(spacing + i * 20, spacing + j * 20, 22 - 2 * spacing, 22 - 2 * spacing);
            }
        }
    }

//     public class Move implements MouseMotionListener {
//        public void mouseDragged(MouseEvent e) {
//
//        }
//        public void mouseMoved(MouseEvent e) {
//            mouseX = e.getX();
//            mouseY = e.getY();
//        }
//    }
//
//    public class Click implements MouseListener {
//
//        public void mouseClicked(MouseEvent e) {
//            if (inBoxX() != -1 && inBoxY() != -1) {
//                for (int i = 0; i < 32; i++) {
//                    for (int j = 0; j < 32; j++) {
//                        if (i == inBoxX() && j == inBoxY()) {
//                            clicked[i][j] = true;
//                            System.out.println("Box that was clicked: [" + inBoxX() + "," + inBoxY() + "]");
//                        } else clicked[i][j] = false;
//                    }
//                }
//            }
//        }
//        public void mousePressed (MouseEvent e){
//
//        }
//        public void mouseReleased (MouseEvent e){
//
//        }
//        public void mouseEntered (MouseEvent e){
//
//        }
//        public void mouseExited (MouseEvent e){
//
//        }
//    }

    public int inBoxX(){
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                if((mouseX >= spacing+i*20 ) && (mouseX <= spacing+i*20-2*spacing+16) && (mouseY >= spacing+j*20) && (mouseY <= spacing+j*20+12))
                    return i;
            }
        }
        return -1;
    }
    public int inBoxY(){
        for(int i = 0; i < 32; i++) {
            for(int j = 0; j < 32; j++) {
                if((mouseX >= spacing+i*20 ) && (mouseX <= spacing+i*20-2*spacing+16) && (mouseY >= spacing+j*20) && (mouseY <= spacing+j*20+12))
                    return j;
            }
        }
        return -1;
    }
}
