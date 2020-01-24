package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtils {

	private CompressionUtils(){}
	
	public static byte[] CompressArray(byte[] input){

        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        compressor.setInput(input);
        compressor.finish();


        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        byte[] output = new byte[1024];

        while (!compressor.finished()){

            int count = compressor.deflate(output);
            bos.write(output, 0, count);

        }

        try{

            bos.close();

        } catch (IOException e){

        }

        byte[] compressedData = bos.toByteArray();

        return compressedData;
    }

    public static byte[] DecompressArray(byte[] input){


        Inflater decompressor = new Inflater();
        decompressor.setInput(input);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

        byte[] output = new byte[1024];
        while (!decompressor.finished()){

            try{

                int count = decompressor.inflate(output);
                bos.write(output, 0, count);

            } catch (DataFormatException e){

            }
        }

        try{

            bos.close();

        } catch (IOException e){

        }

        byte[] decompressedData = bos.toByteArray();

        return decompressedData;
    }

}
