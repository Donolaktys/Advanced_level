package obstacle;

import participant.Participants;

public class Treadmill extends Obstacle {
    public Treadmill(int dist) {
        super(dist);
    }

    @Override
    public void doIt(Participants participants) {
        participants.run(getDist());
    }
}
