import org.junit.*;

import static org.junit.Assert.assertEquals;
public class JUnitSimpleDemonstration {
    @Test
    public void test_addIntegers() {
        SimpleOperations operation = new SimpleOperations();
        int int1 = 2;
        int int2 = 3;
        int expectedResult = 5;
        int actualResult = operation.addIntegers(int1,int2);
        // test
        assertEquals(actualResult,expectedResult);
    }

    @Test
    public void test_sumArrayOfIntegers() {
        SimpleOperations operation = new SimpleOperations();
        int[] intArray = new int[]{1,2,3,4,5,6};
        int expectedResult = 21;
        int actualResult = operation.sumArrayOfIntegers(intArray);
        // test
        assertEquals(actualResult,expectedResult);
    }

    @Test
    public void test_simpleDotProduct() {
        SimpleOperations operation = new SimpleOperations();
        int[] sequence1 = new int[]{1,3,-5};
        int[] sequence2 = new int[]{4,-2,-1};
        int expectedResult = 3;
        int actualResult = operation.simpleDotProduct(sequence1,sequence2);
        // test
        assertEquals(actualResult,expectedResult);
    }
}
