package com.cpjd.main;

import java.util.Scanner;

public class CmdLine {

	public boolean generate = true;
	public int uc = 100;
	public boolean entropy = true;
	public boolean label = true;
	public String compressionType = "zip";
	public CmdLine() {
		
	}
	
	public void askUser() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter number of unique characters (1-100)");
		String in1 = scan.nextLine();
		if(!in1.equals(""))
			uc = Integer.valueOf(in1);
		
		
		System.out.println("Calculate entropy? (y/n)");
		String in5 = scan.nextLine();
		if(in5.toLowerCase().startsWith("n"))
			entropy = false;
		
		
		
		System.out.println("Label data?");
		String in4 = scan.nextLine();
		if(in4.toLowerCase().startsWith("n"))
			label = false;
		
		System.out.println();
		scan.close();
	}
	
	

}
