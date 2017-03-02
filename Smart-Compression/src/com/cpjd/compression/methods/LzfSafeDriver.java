package com.cpjd.compression.methods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.LZFInputStream;
import com.ning.compress.lzf.LZFOutputStream;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import com.ning.compress.lzf.util.ChunkEncoderFactory;

public class LzfSafeDriver extends DriverBase
{
    public LzfSafeDriver() {
        super("LZF-safe");
    }

    @Override
    protected int compressBlock(byte[] uncompressed, byte[] compressBuffer) throws IOException
    {
        int outPtr = LZFEncoder.safeAppendEncoded(uncompressed, 0, uncompressed.length,
                compressBuffer, 0);
        return outPtr;
    }

    @Override
    protected int uncompressBlock(byte[] compressed, byte[] uncompressBuffer) throws IOException
    {
        return LZFDecoder.safeDecode(compressed, 0, compressed.length, uncompressBuffer);
    }

    @Override
    protected void compressToStream(byte[] uncompressed, OutputStream rawOut)
        throws IOException
    {
        LZFOutputStream out = new LZFOutputStream(ChunkEncoderFactory.safeInstance(1 << 16), rawOut);
        out.write(uncompressed);
        out.close();
    }

    @Override
    protected int uncompressFromStream(InputStream compIn, byte[] inputBuffer)
        throws IOException
    {
        LZFInputStream in = new LZFInputStream(ChunkDecoderFactory.safeInstance(), compIn);

        int total = 0;
        int count;
        
        while ((count = in.read(inputBuffer)) >= 0) {
            total += count;
        }
        in.close();
        return total;
    }
}
