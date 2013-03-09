package com.netthreads.libgdx.sound.pitch;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;


/**
 * Helper class to build 'wav' file format in memory.
 *
 * Format: https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
 */
public class WaveHelper
{
	public static final String TYPE = "wav";
	
	private static byte[] WAVE_HEADER =
	{
		'R', 'I', 'F', 'F',
		 0x00, 0x00, 0x00, 0x00,
		 'W', 'A', 'V', 'E', 
		 'f', 'm', 't', ' ', 		/* Subchunk1ID */
		 0x10, 0x00, 0x00, 0x00, 	/* Subchunk1Size */
		 0x01, 0x00, 				/* AudioFormat */
		 0x02, 0x00, 				/* NumChannels */
		 0x44, (byte) 0xAC, 0x00,   /* SampleRate */
		 0x00, 0x00, 0x00, 
		 0x00, 0x00, 0x00, 0x00,
		 0x10, 0x00, 				/* BitsPerSample */
		 'd', 'a', 't', 'a',
		 0x00, 0x00, 0x00, 0x00
	};
	
	/**
	 * Build data for stream.
	 * 
	 * @param channels Number of channels.
	 * @param sampleRate The sample rate.
	 * @param shortBuffer A buffer of sample data.
	 * @return A byte byte buffer representing 'wav' file contents.
	 */
	public static byte[] buildStream(int channels, int sampleRate, ShortBuffer shortBuffer)
	{
		// Extract into bytes
		int size = shortBuffer.capacity() * 2;
		byte[] bytes = new byte[WAVE_HEADER.length + (size)];

		// Number of channels.
		WAVE_HEADER[22] = (byte) (channels & 0xff);
		WAVE_HEADER[23] = (byte) ((channels >>8) & 0xff);

		// Sample rate.
		WAVE_HEADER[24] = (byte) (sampleRate & 0xff);
		WAVE_HEADER[25] = (byte) ((sampleRate >> 8) & 0xff);
		WAVE_HEADER[26] = (byte) ((sampleRate >> 16) & 0xff);
		WAVE_HEADER[27] = (byte) ((sampleRate >> 24) & 0xff);
		
		// Set sample size.
		WAVE_HEADER[40] = (byte) (size & 0xff);
		WAVE_HEADER[41] = (byte) ((size >> 8) & 0xff);
		WAVE_HEADER[42] = (byte) ((size >> 16) & 0xff);
		WAVE_HEADER[43] = (byte) ((size >> 24) & 0xff);

		ByteBuffer.wrap(bytes).put(WAVE_HEADER).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shortBuffer);

		return bytes;
	}
	
}
