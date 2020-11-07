package oldCode;
public class Sheep {
    private double x;
    private double y;
    //movement speed?
    public Sheep(double x, double y) {
        this.x = x;
        this.y = y;//o sa fie cu random, verifica daca patratica este sau nu ocupata deja
    }

    public void move(){
        //se misca random intre puncte
        //daca facem oaia cu movement ar fi mai greu la gather() de la worker pt ca acolo verifica daca e ceva pe patrat
        //dar asta e in continua miscare
        //dunno
    }
}
