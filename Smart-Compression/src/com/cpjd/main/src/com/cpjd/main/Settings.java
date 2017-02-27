package com.cpjd.main.src.com.cpjd.main;

import java.util.Scanner;

public class Settings {

	public boolean generate = true;
	public int uc = 100;
	public boolean entropy = true;
	public boolean binary = false;
	public boolean random = true;
	public boolean organize = true;
	public boolean label = true;
	public Settings() {
		
	}
	
	public void askUser() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Analyse text already in test.txt? (y/n)");
		
		String in8 = scan.nextLine();
		if(in8.toLowerCase().startsWith("y")){
			generate = false;
			scan.close();
			return;
		}
		System.out.println("Enter number of unique characters (1-100)");
		String in1 = scan.nextLine();
		if(!in1.equals(""))
			uc = Integer.valueOf(in1);
		
		
		System.out.println("Calculate entropy? (y/n)");
		String in5 = scan.nextLine();
		if(in5.toLowerCase().startsWith("n"))
			entropy = false;
		
		System.out.println("Calculate binary? (y/n) (Takes 20X longer)");
		String in3 = scan.nextLine();
		if(in3.toLowerCase().startsWith("y"))
			binary = true;
		
		System.out.println("Calculate randomized? (y/n)");
		String in6 = scan.nextLine();
		if(in6.toLowerCase().startsWith("n"))
			random = false;
		
		System.out.println("Calculate organized? (y/n) ");
		String in7 = scan.nextLine();
		if(in7.toLowerCase().startsWith("n"))
			organize = false;
		
		System.out.println("Label data?");
		String in4 = scan.nextLine();
		if(in4.toLowerCase().startsWith("n"))
			label = false;
		
		System.out.println();
		scan.close();
	}
	
	

}
