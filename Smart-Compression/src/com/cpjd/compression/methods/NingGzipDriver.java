package com.cpjd.compression.methods;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ning.compress.gzip.OptimizedGZIPInputStream;
import com.ning.compress.gzip.OptimizedGZIPOutputStream;

public class NingGzipDriver extends DriverBase
{
    public NingGzipDriver() {
        super("GZIP/Ning");
    }

    @Override
    protected int compressBlock(byte[] uncompressed, byte[] compressBuffer) throws IOException
    {
        return compressBlockUsingStream(uncompressed, compressBuffer);
    }

    @Override
    protected int uncompressBlock(byte[] compressed, byte[] uncompressBuffer)
        throws IOException
    {
        OptimizedGZIPInputStream in = new OptimizedGZIPInputStream(new ByteArrayInputStream(compressed));
        int i = uncompressBlockUsingStream(in, uncompressBuffer);
        in.close();
        return i;
    }

    @Override
    protected void compressToStream(byte[] uncompressed, OutputStream rawOut) throws IOException
    {
        OptimizedGZIPOutputStream out = new OptimizedGZIPOutputStream(rawOut);
        out.write(uncompressed);
        out.close();
    }

    @Override
    protected int uncompressFromStream(InputStream compIn, byte[] inputBuffer) throws IOException
    {
        OptimizedGZIPInputStream in = new OptimizedGZIPInputStream(compIn);

        int total = 0;
        int count;
        
        while ((count = in.read(inputBuffer)) >= 0) {
            total += count;
        }
        in.close();
        return total;
        
    }
}
