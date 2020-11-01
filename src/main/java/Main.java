public class Main implements Runnable{

    UserInterface ui = new UserInterface();
    public static void main(String[] args){
        new Thread(new Main()).start();
    }
        @Override
        public void run() {
            while(true) {
                ui.repaint();
            }
        }

}
