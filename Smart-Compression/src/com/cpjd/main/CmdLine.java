package com.cpjd.main;

import java.util.Scanner;

public class CmdLine {

	public boolean generate = true;
	public int uc = 100;
	public boolean size = true;
	public boolean entropy = false;
	public boolean label = false;
	public boolean time = false;
	public int compressionType = 1;
	
	public CmdLine() {
		
	}
	
	public void askUser() {
		Scanner scan = new Scanner(System.in);
		/*
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
		*/
		
		
		System.out.println("Which compression type should be used?");
		System.out.println("1-GZIP");
		System.out.println("2-BZIP2");
		System.out.println("3-LZMA");
		System.out.println("4-QUICKLZ");
		System.out.println("5-LZO");
		System.out.println("6-Snappy");
		compressionType = scan.nextInt();
		

		
		
		System.out.println();
		scan.close();
	}


	public void start() {
		askUser();
	}
	
	

}
