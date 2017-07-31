package com.topie.campus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topie.campus.activiti.model.BpmNode;

public class TestDate {

	public static void main(String[] args) {
		Object o= new Object ();  
		ArrayList<Object > l=new ArrayList<Object >();  
		 l.add(o);  
		 l.remove(o);
		 //l=null;  
		 o=null;
		System.out.println(l);// "false" will be printed  
		
		List<String> ids = new ArrayList<String>();
		for(int i=0;i<10;i++)
		{
			ids.add("a"+i);
		}
		System.out.println(ids);
		ids.remove("a"+5);
		System.out.println(ids);
		BpmNode node = new BpmNode();
		System.out.println(node.getClass().getTypeName());
	}
	
}
