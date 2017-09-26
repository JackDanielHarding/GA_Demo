package Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import genetics.chromesomes.OrderChromosome;

public class OrderChromesomeTest {

	@Test
	public void createOrderChromosomeTest() {
		String name = "Test Chromesome";
		OrderChromosome<TestEnum> oChrom = new OrderChromosome<>(name, TestEnum.class);

		assertEquals(oChrom.getName(), name);
	}

	public void createCloneOrderChromesomeTest() {

	}

	private enum TestEnum {
		STATE1, STATE2, STATE3
	}
}
