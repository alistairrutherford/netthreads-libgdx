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

package com.netthreads.libgdx.sound;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Singleton;

/**
 * Cache of sound objects.
 * 
 */
@Singleton
public class SoundCache implements Disposable
{
	private Map<String, Sound> data;
	private Map<String, SoundDefinition> definitions;

	/**
	 * Construct cache.
	 * 
	 */
	public SoundCache()
	{
		// Cache.
		this.data = new HashMap<String, Sound>();
		this.definitions = new HashMap<String, SoundDefinition>();
	}

	/**
	 * Load list of definitions.
	 * 
	 * @param definitions
	 */
	public void load(List<SoundDefinition> definitions)
	{
		// Load/Re-load sounds.
		for (SoundDefinition definition : definitions)
		{
			add(definition);
		}
	}

	/**
	 * Add sound.
	 * 
	 */
	private void add(SoundDefinition definition)
	{
		Sound sound = null;

		// Load sound
		sound = Gdx.audio.newSound(Gdx.files.internal(definition.getPath()));

		String name = definition.getName();

		// Cache data.
		data.put(name, sound);

		// Cache details.
		definitions.put(name, definition);
	}

	/**
	 * Return sound.
	 * 
	 * @param name
	 *            The name of sound.
	 * 
	 * @return The target sound or null if not found.
	 */
	public Sound get(String name)
	{
		return data.get(name);
	}

	/**
	 * Return {@link Sound} map.
	 * 
	 * @return The {@link Sound} map.
	 */
	public Map<String, Sound> getData()
	{
		return data;
	}

	/**
	 * Return {@link SoundDefinition} map.
	 * 
	 * @return The {@link SoundDefinition} map.
	 */
	public Map<String, SoundDefinition> getDefinitions()
	{
		return definitions;
	}

	/**
	 * Dispose of cache data.
	 * 
	 */
	@Override
	public void dispose()
	{
		Collection<Sound> values = data.values();
		for (Sound sound : values)
		{
			sound.dispose();
		}

		data.clear();
		definitions.clear();
	}
}
