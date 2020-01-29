import obstacle.Obstacle;
import obstacle.Treadmill;
import obstacle.Wall;
import participant.Cat;
import participant.Human;
import participant.Participants;
import participant.Robot;

public class Main {
    public static void main(String[] args) {
        Participants[] participants = {
                new Human("Oleg", 1500, 10),
                new Robot("Wall-E", 3000, 20),
                new Cat("Barsik", 800, 15),
                new Human("Olga", 1300, 12)
        };
        Obstacle[] obstacles = {
                new Wall(11),
                new Treadmill(1000),
                new Wall(12)
        };

        for (Participants p: 
                participants) {
            for (Obstacle o :
                    obstacles) {
                o.doIt(p);
                if(!p.isDistance()) {
                    break;
                }
            }
        }
        for (Participants p :
                participants) {
                    p.info();
        }
    }
}
