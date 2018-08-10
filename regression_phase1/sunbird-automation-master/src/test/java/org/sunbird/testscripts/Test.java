package org.sunbird.testscripts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.util.SystemOutLogger;
import org.sunbird.generic.GenericFunctions;

public class Test {
	static int i=0;
	public static void main(String[] args) {
		
			  char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			  StringBuilder sb = new StringBuilder();
			  Random random = new Random();
			  for (int i = 0; i < 8; i++) {
			      char c = chars[random.nextInt(chars.length)];
			      sb.append(c);
			  }
			  String output = sb.toString();
			    String date = currentDateAndTime ();
			  String var1= autoIncrementer();
			   
			  System.out.println(output+"_"+date+"_"+var1);
			 }
	
	public static String currentDateAndTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");
		// String newDate = dtf.toString().toCharArray().toString();
	//	System.out.println(now.format(dtf).toCharArray());
		String date = now.format(dtf);
		return date;
	}
	
	public static String autoIncrementer()
	{
		String get = GenericFunctions.readFromNotepad(".\\courseNumbers.txt");
		int var=Integer.parseInt(get);
		System.out.println("Reading "+var);
		var=++var;
		String var1 = Integer.toString(var);
		GenericFunctions.writeNotepad(var1,".\\courseNumbers.txt");
		String set=GenericFunctions.readFromNotepad(".\\courseNumbers.txt");
		System.out.println("writing "+set);
		return set;
	}
	
	
	
	
}


	// return now.format(dtf);
	//}



