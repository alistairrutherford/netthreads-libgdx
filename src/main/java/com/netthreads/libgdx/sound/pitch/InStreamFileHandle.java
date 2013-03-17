package com.netthreads.libgdx.sound.pitch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Can be used to hijack the file read to get the data.
 * 
 * Unused at the moment.
 * 
 */
public class InStreamFileHandle extends FileHandle
{
	private FileHandle fileHandle;
	private byte[] data;
	
	public InStreamFileHandle(FileHandle fileHandle)
	{
		this.fileHandle = fileHandle;

		data = null;
	}
	
	@Override
	public InputStream read()
	{
		InputStream inputStream = fileHandle.read();
		inputStream.mark(0);
		
		try
		{
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			
			int value = 0xFF;
			while (value > 0)
			{
				value = inputStream.read();
				if (value > 0)
				{
					arrayOutputStream.write(value);
				}
			}
			
			arrayOutputStream.flush();
			
			// Make byte array of stream contents.
			data = arrayOutputStream.toByteArray();
			
			// Reset input stream.
			inputStream.reset();
			
		}
		catch (IOException e)
		{
			Gdx.app.log("InStreamFileHandle", "Error reading stream, " + e);
		}
		
		return inputStream;
	}
	
	public byte[] data()
	{
		return data;
	}
}
