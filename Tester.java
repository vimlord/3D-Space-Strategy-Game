import entities.ships.Ship;
import main.*;











public class Tester{

    public static void main(String[] args){
        EntityList.addShip(new Ship(0,0,0,100000,50));
        EntityList.getShip(0).setRotationTarget(Math.toRadians(90), Math.toRadians(0));
        while(true){
            Ship s = EntityList.getShip(0);
            System.out.println("Angle: (XZ: " + Math.toDegrees(s.getXZ_ROT()) + ", Y: " + Math.toDegrees(s.getY_ROT()) + ")");
            System.out.println("Angular Speed: (XZ: " + s.getXZ_RotSpeed() + ", Y: " + s.getY_RotSpeed() + ")");
            System.out.println();
            CycleRunner.executeCycle();
        }
    }
    
}
