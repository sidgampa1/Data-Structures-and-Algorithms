package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    ArrayRingBuffer arb = new ArrayRingBuffer(10);

    @Test
    public void addTest() {
        for(int i = 0; i < 10; i++){
          arb.enqueue(i);
        }
    }

    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    // @Test
    // public void someTest() {
    //     ArrayRingBuffer arb = new ArrayRingBuffer(10);
    // }

  /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
