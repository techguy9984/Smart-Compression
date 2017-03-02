package com.cpjd.compression.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.cpjd.compression.methods.BZip2Driver;
import com.cpjd.compression.methods.JDKGzipDriver;
import com.cpjd.compression.methods.LzmaJavaDriver;

public class Compressor {

	private int size;
	private byte[] uncompressed;

	public static void main(String[] args) throws Exception {
		Result r = new Compressor(new File("C:\\Users\\Will Davies\\Desktop\\tester.txt")).compressBZIP2();
		System.out.println(r.getSize());
	}

	/**
	 * Intializes the compresser class, just call a method to compress that file
	 * 
	 * @param file
	 *            The file to be compressed
	 */
	public Compressor(File file) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream((int) file.length());
			byte[] buffer = new byte[4000];
			int count;
			FileInputStream in = new FileInputStream(file);

			while ((count = in.read(buffer)) > 0) {
				bytes.write(buffer, 0, count);
			}
			in.close();
			uncompressed = bytes.toByteArray();
		} catch (Exception e) {
		}
	}

	public Result compressGZIP() throws Exception {
		JDKGzipDriver driver = new JDKGzipDriver();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(uncompressed.length);
		long start = System.nanoTime();
		out.reset();
		driver.compressToStream(uncompressed, out);
		size = out.size();
		long time = System.nanoTime() - start;

		System.gc();
		Thread.sleep(50L);

		return new Result(size, time);
	}
	
	public Result compressBZIP2() throws Exception {
		BZip2Driver driver = new BZip2Driver();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(uncompressed.length);
		long start = System.nanoTime();
		out.reset();
		driver.compressToStream(uncompressed, out);
		size = out.size();
		long time = System.nanoTime() - start;

		System.gc();
		Thread.sleep(50L);

		return new Result(size, time);
	}
	
	public Result compressLZMA() throws Exception {
		LzmaJavaDriver lzma = new LzmaJavaDriver();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(uncompressed.length);
		long start = System.nanoTime();
		out.reset();
		lzma.compressToStream(uncompressed, out);
		size = out.size();
		long time = System.nanoTime() - start;

		System.gc();
		Thread.sleep(50L);

		return new Result(size, time);
	}
}
