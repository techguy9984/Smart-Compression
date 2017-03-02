package com.cpjd.compression.methods;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class BZip2Driver  extends DriverBase
{
    private final static int COMP_LEVEL = 2; // from 1 to 9; 100 - 900k blocks
    
    public BZip2Driver() {
        super("bzip2");
    }

    // No native Block API; but need some impl for test framework

    @Override
    protected int compressBlock(byte[] uncompressed, byte[] compressBuffer) throws IOException {
        return compressBlockUsingStream(uncompressed, compressBuffer);
    }

    @Override
    protected int uncompressBlock(byte[] compressed, byte[] uncompressBuffer) throws IOException {
        return uncompressBlockUsingStream(new BZip2CompressorInputStream(new ByteArrayInputStream(compressed)), uncompressBuffer);
    }

    @Override
    public void compressToStream(byte[] uncompressed, OutputStream rawOut) throws IOException
    {
        BZip2CompressorOutputStream out = new BZip2CompressorOutputStream(rawOut, COMP_LEVEL);
        out.write(uncompressed);
        out.close();
    }

    @Override
    protected int uncompressFromStream(InputStream compIn, byte[] inputBuffer) throws IOException
    {
        BZip2CompressorInputStream in = new BZip2CompressorInputStream(compIn);

        int total = 0;
        int count;
        
        while ((count = in.read(inputBuffer)) >= 0) {
            total += count;
        }
        in.close();
        return total;
    }
}
