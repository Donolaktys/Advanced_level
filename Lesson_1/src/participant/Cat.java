package participant;

public class Cat implements Participants{
    String type;
    String name;

    int maxRunDistance;
    int maxJumpHeight;
    boolean overcameAnObstacle;

    public Cat (String name, int maxRunDistance, int maxJumpHeight){
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.type = "Кот";
        this.overcameAnObstacle = true;
    }

    @Override
    public void run(int dist) {
        if(dist <= maxRunDistance) {
            System.out.println(type + " " + name + " успешно пробежал(а) дистанцию");
        } else {
            System.out.println(type + " " + name + " не смог(ла) завершить дистанцию");
            overcameAnObstacle = false;
        }
    }

    @Override
    public void jump(int height) {
        if(height <= maxJumpHeight) {
            System.out.println(type + " " + name + " преодолел(а) стену");
        } else {
            System.out.println(type + " " + name + " не смог(ла) преодолеть стену");
            overcameAnObstacle = false;
        }
    }

    @Override
    public void info() {
        System.out.println(name + " " + overcameAnObstacle);
    }

    @Override
    public boolean isDistance() {
        return overcameAnObstacle;
    }
}
