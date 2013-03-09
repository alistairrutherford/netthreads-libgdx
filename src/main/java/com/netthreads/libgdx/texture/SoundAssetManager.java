package com.netthreads.libgdx.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netthreads.libgdx.sound.SoundDefinition;
import com.netthreads.libgdx.sound.SoundDefinitions;

@Singleton
public class SoundAssetManager implements Disposable
{
	private AssetManager assetManager;
	
	@Inject
	public SoundAssetManager(SoundDefinitions soundDefinitions)
	{
		assetManager = new AssetManager();
		
		for (SoundDefinition soundDefinition : soundDefinitions.getDefinitions())
		{
			assetManager.load(soundDefinition.getPath(), Sound.class);
		}
	}
	
	public AssetManager getAssetManager()
	{
		return assetManager;
	}
	
	@Override
	public void dispose()
	{
		assetManager.dispose();
	}
	
}
