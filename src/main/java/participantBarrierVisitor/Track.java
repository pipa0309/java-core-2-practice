package participantBarrierVisitor;

public class Track implements Barrier {
    private final String nameId;
    private final int length;

    public Track(String nameId, int length) {
        this.nameId = nameId;
        this.length = length;
    }

    public String getNameId() {
        return nameId;
    }

    public int getLength() {
        return length;
    }

    @Override
    public void interconnect(Participant participant) {
        participant.run(this);
    }
}
