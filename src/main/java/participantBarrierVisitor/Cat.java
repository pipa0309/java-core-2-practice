package participantBarrierVisitor;

public class Cat implements Participant {
    private final String name;
    private final int heightJump;
    private final int lengthRun;

    public Cat(String name, int heightJump, int lengthRun) {
        this.name = name;
        this.heightJump = heightJump;
        this.lengthRun = lengthRun;
    }

    public int getHeightJump() {
        return heightJump;
    }

    public int getLengthRun() {
        return lengthRun;
    }

    @Override
    public void jump(Wall wall) {
        if (this.getHeightJump() >= wall.getHeight()) {
            System.out.printf("%s jump %s\n", this.name, wall.getNameId());
        }
    }

    @Override
    public void run(Track track) {
        if (this.getLengthRun() >= track.getLength()) {
            System.out.printf("%s run %s\n", this.name, track.getNameId());
        }
    }
}
