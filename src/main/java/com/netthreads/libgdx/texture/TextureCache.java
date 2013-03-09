/*
 * -----------------------------------------------------------------------
 * Copyright 2012 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.netthreads.libgdx.texture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Singleton;

/**
 * Represents a generic texture cache backed by the {@link TextureAtlas}.
 * 
 */
@Singleton
public class TextureCache implements Disposable
{
	private TextureAtlas textureAtlas = null;

	private Map<String, TextureDefinition> definitions;

	/**
	 * Construct cache.
	 * 
	 */
	public TextureCache()
	{
		definitions = new HashMap<String, TextureDefinition>();
	}

	/**
	 * Pack pack file textures (textures file resides in same directory).
	 * 
	 * @param packFile
	 */
	public void load(String packFile)
	{
		if (textureAtlas == null)
		{
			textureAtlas = new TextureAtlas(Gdx.files.internal(packFile));
		}
	}

	/**
	 * Load predefined textures.
	 * 
	 * This requires texture definitions to be added to the {@link AppActorTextures} structure.
	 */
	public void load(List<TextureDefinition> textureDefinitions)
	{
		if (textureAtlas == null)
		{
			textureAtlas = new TextureAtlas();
		}
		else
		{
			dispose();

			textureAtlas = new TextureAtlas();
		}

		for (TextureDefinition definition : textureDefinitions)
		{
			Texture texture = new Texture(Gdx.files.internal(definition.getPath()));
			TextureRegion textureRegion = new TextureRegion(texture);

			textureAtlas.addRegion(definition.getName(), textureRegion);
			definitions.put(definition.getName(), definition);
		}
	}

	/**
	 * Fetch texture region from cache.
	 * 
	 * @param name
	 *            The texture name.
	 * 
	 * @return The texture region.
	 */
	public TextureRegion getTexture(TextureDefinition definition)
	{
		return textureAtlas.findRegion(definition.getName());
	}

	/**
	 * Fetch texture region from cache.
	 * 
	 * @param name
	 *            The texture name.
	 * 
	 * @return The texture region.
	 */
	public TextureDefinition getDefinition(String name)
	{
		return definitions.get(name);
	}

	/**
	 * Dispose of cache data.
	 * 
	 */
	@Override
	public void dispose()
	{
		definitions.clear();
		textureAtlas.dispose();
	}
}
