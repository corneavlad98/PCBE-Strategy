package oldCode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

// singleton class
public class Board extends JPanel {
    private int spacing = 2;
    private Color myGreen = new Color(83, 205, 99); // Custom green color
    private Color myBrown = new Color(141, 69, 38); // Custom brown color
    private Color player1Color = new Color(218, 6, 233); // Custom purple color
    private Color player2Color = new Color(31, 44, 218); // Custom blue color
    private int selectedPlayer = MyConstants.NO_PLAYER_SELECTED;
    public int mouseX, mouseY;

    static int[][] map = new int[32][32];
    int[][] wood = new int[32][32];
    int[][] stone = new int[32][32];
    int[][] gold = new int[32][32];
    boolean[][] clicked = new boolean[32][32];


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
                               System.out.println("Box that was clicked: [" + inBoxX() + ", " + inBoxY() + "]");
                               if(selectedPlayer != MyConstants.NO_PLAYER_SELECTED) {
                                   movePlayer(i, j);
                                   selectedPlayer = MyConstants.NO_PLAYER_SELECTED;
                                   break;
                               }
                               if(map[i][j] == MyConstants.PLAYER_ONE || map[i][j] == MyConstants.PLAYER_TWO) {
                                   System.out.println("player " + map[i][j]);
                                   selectedPlayer = map[i][j];
                                   map[i][j] = MyConstants.PLAYER_SELECTED;
                                   System.out.println(selectedPlayer);
                                   break;
                               }
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
       /*new Thread(() -> {
           while(true) {
               this.repaint();
           }
       }).start();
    }*/


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
                if(map[i][j] == 7)
                    g.setColor(Color.CYAN);
                g.fillRect(spacing + i * 20, spacing + j * 20, 22 - 2 * spacing, 22 - 2 * spacing);
            }
        }
    }

    public void movePlayer(int x, int y) {
        System.out.println("MOVING to " + x + " " + y);
        int playerCurrentXPos = -1, playerCurrentYPos = -1;
        playerCurrentXPos = getSelectedPlayerXPos();
        playerCurrentYPos = getSelectedPlayerYPos();
        int difX, difY;
        difX = x - playerCurrentXPos;
        difY = y - playerCurrentYPos;
        System.out.println(playerCurrentXPos + " " + playerCurrentYPos);
        System.out.println(selectedPlayer);
        if(selectedPlayer == MyConstants.NO_PLAYER_SELECTED) {
            return;
        }
        if(difX > 0) {
            while(x > playerCurrentXPos) {
                playerCurrentXPos++;
            }
        } else if(difX < 0) {
            while(x < playerCurrentXPos) {
                playerCurrentXPos--;
            }
        }
        if(difY > 0) {
            while (y > playerCurrentYPos) {
                playerCurrentYPos++;
            }
        } else if(difY < 0) {
            while(y < playerCurrentYPos) {
                playerCurrentYPos--;
            }
        }
        map[playerCurrentXPos][playerCurrentYPos] = selectedPlayer;
        System.out.println("Player " + selectedPlayer + " is at [" + playerCurrentXPos + ", " + playerCurrentYPos + "]\n");
        selectedPlayer = MyConstants.NO_PLAYER_SELECTED;
    }

    private int getSelectedPlayerXPos() {
        if(selectedPlayer != MyConstants.NO_PLAYER_SELECTED){
            for(int i = 0; i < 32; i++){
                for(int j = 0; j < 32; j++){
                    if(map[i][j] == MyConstants.PLAYER_SELECTED)
                        return i;
                }
            }
        }
        return -1;
    }

    private int getSelectedPlayerYPos() {
        if (selectedPlayer != MyConstants.NO_PLAYER_SELECTED) {
            int xPos = getSelectedPlayerXPos();
            for(int i = 0; i < 32; i++){
                if(map[xPos][i] == MyConstants.PLAYER_SELECTED){
                    return i;
                }
            }
        }
        return -1;
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
