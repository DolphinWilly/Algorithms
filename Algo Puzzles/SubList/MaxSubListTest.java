import static org.junit.Assert.*;

import org.junit.Test;

public class MaxSubListTest {

	@Test
	public void test() {
		int[] l1 = {1,3,-2,5};
		int[] l2 = {1,2,-6,4};
		int[] l3 = {-1,-2,4,-5,9};
		MaxSubList test1 = new MaxSubList();
		MaxSubList test2 = new MaxSubList();
		MaxSubList test3 = new MaxSubList();
		assertEquals(test1.maxSub(l1),7);
		assertEquals(test2.maxSub(l2),4);
		assertEquals(test3.maxSub(l3),9);
	}

}
