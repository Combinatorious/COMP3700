import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FinderTest {
    @Test
    /*
        Tests findMax using a valid input array
     */
    public void findMaxValidTest() {
        Integer[] validArray = {0, -5, 12, 3, 9, 11};
        Integer expected = 12;
        assertEquals(expected, Finder.findMax(validArray));
    }

    @Test
    /*
        Test findMin using a valid input array
     */
    public void findMinValidTest() {
        Integer[] validArray = {9, 5, 3, -2, 14, -2, 4, 18, 13};
        Integer expected = -2;
        assertEquals(expected, Finder.findMin(validArray));
    }

    @Test
    /*
        Tests findMax using a null input array
     */
    public void findMaxNullTest() {
        assertNull(Finder.findMax(null));
    }

    @Test
    /*
        Tests findMax using an empty input array
     */
    public void findMaxEmptyTest() {
        assertNull(Finder.findMax(new Integer[0]));
    }

    @Test
    /*
        Tests findMin using a null input array
     */
    public void findMinNullTest() {
        assertNull(Finder.findMin(null));
    }

    @Test
    /*
        Tests findMin using an empty input array
     */
    public void findMinEmptyTest() {
        assertNull(Finder.findMin(new Integer[0]));
    }

}
