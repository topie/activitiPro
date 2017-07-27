package com.topie.campus;

public class ThreadRn extends Thread{

	private int num;
	public ThreadRn(int num)
	{
		this.num=num;
	}
	
	public void run()
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println(Thread.currentThread().getName()+"--"+num);
			/*Thread.sleep(3000L);*/
	}

}
