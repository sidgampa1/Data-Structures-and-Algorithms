package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates Clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /** creates a Clorus with energy equal to 1. */
    public Clorus() {
        //int random = HugLifeUtils.randomInt(1, 5);
        this(2);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Clorus. If the Clorus has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        return color(r, g, b);
    }

    /** Do nothing with C, Cloruss are pacifists. */
    public void attack(Creature c) {
        double takenEnergy = c.energy();
        energy += takenEnergy;
    }

    /** Cloruss should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.03;
    }


    /** Cloruss gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy -= 0.01;
    }

    /** Cloruss and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Clorus.
     */
    public Clorus replicate() {
        energy = energy / 2.0;
        double babyEnergy = energy;
        Clorus baby = new Clorus(babyEnergy);
        return baby;
    }

    /** Cloruses take exactly the following actions based on NEIGHBORS:
     - If there are no empty squares, the Clorus will STAY (even if there are Cloruss nearby they could attack).
     - Otherwise, if any Cloruss are seen, the Clorus will ATTACK one of them randomly.
     - Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     - Otherwise, the Clorus will MOVE to a random empty square.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        else if (plips.size() != 0){
            //attack one randomly
            Direction dir = HugLifeUtils.randomEntry(plips);
            //Plip randomPlip = plips[dir];
            return new Action(Action.ActionType.ATTACK, dir);
        }
        else if (energy >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE,moveDir);
        }
        else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
        }

    }
