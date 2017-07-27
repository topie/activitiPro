package com.topie.campus;

import java.util.ArrayList;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) {
		Object o= new Object ();  
		ArrayList<Object > l=new ArrayList<Object >();  
		 l.add(o);  
		 l.remove(o);
		 //l=null;  
		 o=null;
		System.out.println(l);// "false" will be printed  
	}

}
