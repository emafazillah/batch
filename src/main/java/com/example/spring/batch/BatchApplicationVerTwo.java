package com.example.spring.batch;

public class BatchApplicationVerTwo {
	
	private static int attemptCount = 1;
	
	private static int maxAttemptCount = 4;
	
	public static void main(String...strings) {
		while(attemptCount <= maxAttemptCount) {
			try {
//				if(attemptCount == 4) {
//					new Exception("More than 3 attempts");
//				} else {
//					System.out.println("Run application");
//				}
				if(attemptCount < 4) {
					throw new Exception("Deadlock. Retry...");
				} else {
					System.out.println("Run application");
					break;
				}
			} catch(Exception ex) {
				++attemptCount;
				String errMsg = ex.getStackTrace().toString();
				System.out.println("Error message: " + errMsg);
			}
			
		}
	}

}
