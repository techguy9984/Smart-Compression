
package com.cpjd.main;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;



/**
 * Daniel and Will's Science Fair Experiment.
 * 
 * This software generates strings at increasing levels of entropy and
 * allows the portions of text to be measured for how random (entropy) they are.
 * 
 * The experiment is to test the compression size of files depending on their 
 * entropy. This software will likely be expanded to automatically check factors of
 * text such as entropy, size, format, etc. and automatically perform the best 
 * compression method for the measured attributes.
 * @author Will Davies and Daniel Peterson
 *
 */
public class Main {

	final int BUFFER = 2048;
	final String fn = System.getProperty("user.dir")+"//test.txt";
	final String zfn = System.getProperty("user.dir")+"//test.zip";
	private CmdLine cmd  = new CmdLine();
	private String[] data;
	
	/**
	 * The constructor. Here we check the entropy of the text file and then procede to generate strings with increasing entropy
	 */

	public Main() {
		cmd.start();
		if (cmd.generate){
			InitiateData();
			AnalyzeData();	
		}else{
			AnalyzeText();
			
		}
	}

	
	
	private void AnalyzeText() {
		File original =new File(fn);
		File compressed =new File(zfn);
		double entropy = 0;
		int uniqueCharacters = 0;
		int length = 0;
		int compLength = 0;
		
		String data = "";
		try {
			data = loadFile("test.txt", 6000000);
		} catch (Exception e) {
			System.err.println("file loading error");
		}
		zip(fn);
		entropy = getEntropy(data);
		uniqueCharacters = uniqueChars(data);
		length = (int)original.length();
		compLength = (int)compressed.length();
		
		System.out.print("     Entropy: ");
		System.out.println(entropy);
		System.out.print("Unique Chars: ");
		System.out.println(uniqueCharacters);
		System.out.print("    Original: ");
		System.out.println(length);
		System.out.print("  Compressed: ");
		System.out.println(compLength);
		
	}

	private void AnalyzeData() {
		for (int i = 0; i < data.length;i++){
			if (cmd.entropy){
				if (cmd.label)
					System.out.print("     Entropy: ");
				System.out.println(getEntropy(data[i]));
			}
			
			File file =new File(zfn);
			
			
				//this lines takes the 2nd longest
			writeToFile(shuffle(data[i]));
			if(cmd.compressionType.equals("zip")){
				long t = System.nanoTime();
				zip(fn);
				System.out.println(System.nanoTime()-t);
			}
			
			
			if (cmd.label)
				System.out.println("  Compressed: " +file.length() +" bytes");
			else 
				System.out.println(file.length());
			if (cmd.label)
				System.out.println();	
		}
		System.out.println("Uncompressed: "+ cmd.uc*cmd.uc + " bytes");	
	}
	private void InitiateData() {
		data = generateText2(cmd.uc);
	}
	
	/**
	 * The main method, program starts here.
	 * @param args Command-line arguments. Not applicable for this program.
	 */
	public static void main(String[] args) {
		new Main();
	}
	
	/**
	 * Converts a text file into an array of strings
	 * @param path The text file's location within the working directory
	 * @param max The character limit. When it's reached, the text file will stop being read
	 * @return An arraylist of type string
	 * @throws Exception If the file couldn't be accessed
	 */
	private String loadFile(String path, int characters) throws Exception {
		InputStream in = getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
		String lines = "";
		
		String line = br.readLine();

		int progress = -1;
		while(line != null) {
			lines += line;
			if(lines.length()>=characters)
				break;
			line = br.readLine();
			if((int)((double)lines.length()/characters*100)!=progress){
				progress = (int)((double)lines.length()/characters*100);
				System.out.println("loading "+progress+"%");
			}
		}
		
		
		//cuts off the  file to the desired length
		if (lines.length() >= characters){
			lines = lines.substring(0, characters);	
		}
		
		return lines;
	}
	private void writeToFile(String str) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	
	private void zip (String fileName) {
	      try {
	         BufferedInputStream origin = null;
	         //must be .zip?
	         FileOutputStream dest = new  FileOutputStream("test.zip");
	         ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
	         //out.setMethod(ZipOutputStream.DEFLATED);
	         byte data[] = new byte[BUFFER];

	            FileInputStream fi = new FileInputStream(fileName);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry("test");
	            out.putNextEntry(entry);
	            int count;
	            while((count = origin.read(data, 0, 
	              BUFFER)) != -1) {
	               out.write(data, 0, count);
	            }
	            origin.close();
	         
	         out.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }
	
	
    
    
    //same # of characters and size, varrying entropy
    private String[] generateText2(int characters){
        String[] texts = new String[characters];
        
        for(int i = 0 ; i < characters; i ++){
        	String text = "";
        	
        	//(characters-(i+1)*(characters-1)) of first character
        	for(int c = 0; c<characters*characters-(i+1)*(characters-1);c++){
        		text+=(char)33;
        	}
        	//(i+1) of folowing characters
        	for(int c = 1; c<characters;c++){
        		for(int j = 0; j < (i+1); j++){
        			text+=(char)(33+c);
        		}
        	}
        	texts[i] = text;
        }

        return texts;
    }
    
	/**
	 * Gets the entropy of text. Entropy of text is essentially measured by taking 
	 * the summation of the log2 of each character's occurrence over the number of 
	 * characters in the text
	 * ranges from zero to log2 of X, with X unique characters.
	 * @param lines The text to check for entropy
	 * @return The entropy value
	 */
	private double getEntropy(String lines) {
		ArrayList<Character> letters = new ArrayList<Character>();
		ArrayList<Integer> occurrence = new ArrayList<Integer>();
		
		double totalCharacters = 0;
		for(int i = 0; i < lines.length(); i++) {
				char currentChar = lines.charAt(i);
				
				int index = letters.indexOf(currentChar);
				if(index < 0) {
					letters.add(currentChar);
					occurrence.add(1);
				} else {
					occurrence.set(index, occurrence.get(index) + 1);
				}
				
				totalCharacters++;
		}
		
		double entropy = 0;
		for(int i = 0; i < occurrence.size(); i++) {
			double p = occurrence.get(i) / totalCharacters;
			entropy += (p * log2(p));
		}
		entropy = -entropy;
		
		return entropy;
	}
	
	/**
	 * Converts a String into a binary representation of that String
	 * @param str String to convert to binary
	 * @return String representation of the binary
	 */
	private String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
	}

	
	/**
	 * Performs the log base 2 operation since Java doesn't have a native log2 function
	 * @param value The value to be operated on
	 * @return The log base 2 of the value
	 */
	private double log2(double value) {
		return Math.log(value) / Math.log(2);
	}

	private int uniqueChars(String data){
		ArrayList<Character> uniqueCharacters = new ArrayList<Character>();
		
		for (int i = 0; i < data.length();i++){
			
			if(!uniqueCharacters.contains(data.charAt(i))){
				uniqueCharacters.add(data.charAt(i));
			}
				
			
		}
		return uniqueCharacters.size();
	}


	
}
