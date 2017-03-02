package com.cpjd.compression;

public class COMPRESSOR {

	public int compressRLE(String string) {
		return getStringSize(RLE.encode(string));
	}
	
	private int getStringSize(String string) {
		byte[] b = string.getBytes();
		return b.length;
	}
	
}
