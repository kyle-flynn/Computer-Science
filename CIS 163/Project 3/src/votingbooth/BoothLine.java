package votingbooth;

import java.util.ArrayList;

/**
 * Created by kylef_000 on 10/19/2016.
 */
public class BoothLine implements ClockListener {

    private ArrayList<Voter> Q;
    private Booth[] booths;
    private int maxQlength;

    public BoothLine(Booth[] booths) {
        this.booths = booths;
        this.Q = new ArrayList<>();
    }

    public void addVoter(Voter person) {
        Q.add(person);
        if (Q.size() > maxQlength) {
            maxQlength = Q.size();
        }
    }

    @Override
    public void event(int tick) {
        if (Q.size() >= 1) {
            for (int i = 0; i < Q.size(); i++) {
                Q.get(i).addTime(1);
                if (Q.get(i).getTimeSpent() >= Q.get(i).getTolerance()) {
                    // fuck this shit im out
                    System.out.println("Voter " + Q.get(i).getVoterID() + ": FUCK THIS");
                    Q.remove(i);
                }
            }

            for (Booth b : booths) {
                if (!b.inUse()) {
                    if (Q.get(0).getStatus() == VoterStatus.WAITING_FOR_BOOTH) {
                        Voter person = Q.remove(0);
                        b.add(person);
                        break;
                    }
                }
            }

        }
    }

    public int getMaxQlength() {
        return maxQlength;
    }

    public int getLeft() {
        return Q.size();
    }

}
