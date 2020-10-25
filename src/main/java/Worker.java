public class Worker {
    private double x;
    private double y;
    public Worker(double x, double y) {
        this.x = x;
        this.y = y;
        //movement speed
    }
    //Workerul se creeaza de la primarie
    //cand se creeaza o sa ia coordonatele primariei

    public void gather(BuildingResource res){
        //tipul de resursa se da prin click dreapta pe resursa de pe harta
        //adica va fi un moveTo la niste coordonate, si se verifica daca este vreo resursa la coord respective
        return res.quantity;
    }

    public void build(Building b){
        //aici sa aibe muncitorul o lista de cladiri?
        //sa fie o lista globala cu cate 0 cladiri la inceput unde trebuie sa ne facem nr de cladiri
        //parametrul dat aici va fi adaugat in lista noastra globala
    }

    public void moveTo(double newX, double newY){
        this.x = newX;
        this.y = newY;
    }

}
