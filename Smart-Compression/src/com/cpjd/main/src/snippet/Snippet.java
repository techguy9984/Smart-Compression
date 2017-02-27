package com.cpjd.main.src.snippet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Snippet {
	private static final int BUFFER = 2048;

	/**
		 * Same size, varrying number of characters and entropy
		 * flawed, to many variables
		 * Generates text at increasing levels of entropy.
		 * @param length The amount of characters to generate.
		 * There are 95 human readable characters specified in the ASCII table, including the letters A through Z both in upper and lower case, the numbers 0 through 9, a handful of punctuation marks and characters like the dollar symbol, the ampersand and a few others. It also includes 33 values for things like space, line feed, tab, backspace 
		 * @return A String[] of each generated string and it's corresponding entropy
		 */
	    private String[] generateText(int length, int repetitions){
	    	//length max of one hundred
	        String[] texts = new String[383];
	        
	        //starts off string[] with all "!"
	        texts[0] = new String(new char[length]).replace("\0", "!");
	        
	        //number of divisions
	        int count = 1;
	        for (int i = 1; i <= length;i++){
	            //progress in current division
	            for (int j = 1; j <= (length/(i+1));j++){
	                String temp = "";
	                //adding current char
	                for (int c = 0;c<j;c++){
	                	//+33
	                    temp+= (char)(i+33);
	                }
	                //filling remainding
	                for (int c = 0; c < i; c++){
	                    for (Byte b = 0; b < (length-j)/(i);b++)
	                    	//+33
	                        temp += (char)(c+33);
	                }
	                for (int c = 0; c < (length-j)%(i);c++){
	                	//+33
	                    temp += (char)(c+33);
	                }
	                
	                texts[count] = temp;
	                count++;
	               
	            }
	            
						//texts[i-1] = new String(new char[repetitions]).replace("\0", texts[i-1]);
						//System.out.println("here" + count);
				}
	        
	        String[] ret = new String[count];
	        for (int i = 0; i <ret.length;i++){
	        	if(repetitions > 1 && texts[i] != null){				
	    			//fix what causes ths problem
	            		String temp = "";
	            		for (int j = 0;j<repetitions;j++){
	            			temp += texts[i];
	            		}
	            		texts[i] = temp;
	            }
	        	ret[i] = texts[i];
	        	
	        }
	        
	        return ret;
	    }

	//i dont know if we need this method or not
		private void unZip(String fileName){
			try {
		         BufferedOutputStream dest = null;
		         FileInputStream fis = new FileInputStream(fileName);
		         ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		         ZipEntry entry;
		         while((entry = zis.getNextEntry()) != null) {
		            System.out.println("Extracting: " +entry);
		            int count;
		            byte data[] = new byte[BUFFER];
		            // write the files to the disk
		            // make more simple
		            FileOutputStream fos = new FileOutputStream(entry.getName().substring(0,entry.getName().length()-4));
		            dest = new BufferedOutputStream(fos, BUFFER);
		            while ((count = zis.read(data, 0, BUFFER)) 
		              != -1) {
		               dest.write(data, 0, count);
		            }
		            dest.flush();
		            dest.close();
		         }
		         zis.close();
		      } catch(Exception e) {
		         e.printStackTrace();
		      }
			
			
		}
}

