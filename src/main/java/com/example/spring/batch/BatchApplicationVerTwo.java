package com.example.spring.batch;

public class BatchApplicationVerTwo {
	
	private static int attemptCount = 1;
	
	private static int maxAttemptCount = 4;
		
	public static void main(String...strings) {
		while(attemptCount <= maxAttemptCount) {
			if(attemptCount < maxAttemptCount) {
				Launch launch = new Launch();
				attemptCount = launch.launch(attemptCount, strings);
			} else {
				System.out.println("Reached maximum attempt " + attemptCount + ".");
				break;
			}
		}
		
		if(attemptCount == 5) {
			System.out.println("BatchSystemConstant.ExitCode.SUCCESS");
		} else {
			System.out.println("BatchSystemConstant.ExitCode.FAILED");
		}
	}
	
}

class Launch {

	public Launch() {
		
	}
	
	public int launch(int attemptCount, String...strings) {
		String status = "FAILED";
		try {
			// Test to simulate Exception without unit test
			if(attemptCount < 4) {
				throw new Exception("Deadlock. Retry...");
			} else {
				System.out.println("Run application start.");
				Thread.sleep(1000); // dummy sleep
				System.out.println("Run application end.");
				status = "SUCCESS";
			}
			
//			System.out.println("Run application start.");
//			Thread.sleep(1000); // dummy sleep
//			System.out.println("Run application end.");
//			status = "SUCCESS";
		} catch(Exception ex) {
			++attemptCount;
			String errMsg = ex.getStackTrace().toString();
			System.out.println("Error message: " + errMsg);
		} finally {
			if("SUCCESS".equals(status)) {
				attemptCount = 5;
			}
		}
		
		return attemptCount;
	}
	
}
