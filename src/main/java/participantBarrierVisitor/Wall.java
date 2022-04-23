package participantBarrierVisitor;

public class Wall implements Barrier {
    private final String nameId;
    private final int height;

    public Wall(String nameId, int height) {
        this.nameId = nameId;
        this.height = height;
    }

    public String getNameId() {
        return nameId;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void interconnect(Participant participant) {
        participant.jump(this);
    }
}
