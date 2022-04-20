package participantBarrierVisitor;

public class App {
    public static void main(String[] args) {
        Participant[] participants = {
                new Human("human1", 36, 41),
                new Robot("robot2", 42, 42),
                new Cat("cat1", 36, 43),
                new Robot("robot1", 38, 50),
                new Human("human2", 35, 35),
                new Cat("cat2", 45, 38)
        };

        Barrier[] barriers = {
                new Track("track1", 40),
                new Wall("wall1", 35)
        };

        for (Barrier barrier : barriers) {
            for (Participant participant : participants) {
                barrier.interconnect(participant);
            }
        }
    }
}
