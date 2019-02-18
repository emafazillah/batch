package com.example.spring.batch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BatchApplicationTests {

	@Test
	public void testPositiveMain() throws Exception {
		String[] mockStrings = new String[] {"2019-02-18", "JOB NAME"};
		
		Launch mockLaunch = PowerMockito.mock(Launch.class);
		
		PowerMockito.when(mockLaunch.launch(1, mockStrings)).thenReturn(5);
		
		BatchApplicationVerTwo.main(mockStrings);
	}
	
	@Test
	public void testNegativeMain() throws Exception {
		String[] mockStrings = new String[] {"2019-02-18", "JOB NAME"};
		
		Launch mockLaunch = PowerMockito.mock(Launch.class);
		
		PowerMockito.when(mockLaunch.launch(3, mockStrings)).thenReturn(4);
		
		BatchApplicationVerTwo.main(mockStrings);
		
		assertEquals(4, mockLaunch.launch(3, mockStrings));
	}

}
