package votingbooth;

/**
 * @author Roger Ferguson
 *
 */
public class Sim {

    public static void main (String[] args) {

        Clock clk = new Clock();
        Booth booth = new Booth(60);

        // 		int numOfTicksNextPerson = 20
        //      int averageBoothTime = 20

//        VoterProducer produce = new VoterProducer(booth, 20, 18);
//        clk.add(produce);
        clk.add(booth);

        clk.run(10000);

//        System.out.println("Through put is: " + booth.getThroughPut() + " people.");
//        System.out.println("People that are still in the Q:b" + booth.getLeft() + " people.");
//        System.out.println ("Max Q length: " + booth.getMaxQlength() + " people.");
    }
}
