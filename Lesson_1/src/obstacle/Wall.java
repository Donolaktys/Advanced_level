package obstacle;

import participant.Participants;

public class Wall extends Obstacle{
    public Wall(int height) {
        super(height);
    }

    @Override
    public void doIt(Participants participants) {
        participants.jump(getDist());
    }
}