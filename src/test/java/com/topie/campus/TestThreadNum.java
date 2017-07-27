package com.topie.campus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadNum {

	public static void main(String[] args) {
		
		ExecutorService eService = Executors.newFixedThreadPool(100); 
		
		for(int i=0;i<50;i++)
		{
			eService.execute(new ThreadRn(i));
		}
		eService.shutdown();
		System.out.println(eService.isTerminated());
	}

}
