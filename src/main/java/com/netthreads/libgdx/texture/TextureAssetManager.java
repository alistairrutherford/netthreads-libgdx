package com.netthreads.libgdx.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TextureAssetManager implements Disposable
{
	private AssetManager assetManager;
	
	@Inject
	public TextureAssetManager(TextureDefinitions textureDefinitions)
	{
		assetManager = new AssetManager();
		
		for (TextureDefinition textureDefinition : textureDefinitions.getDefinitions())
		{
			assetManager.load(textureDefinition.getPath(), Texture.class);
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
