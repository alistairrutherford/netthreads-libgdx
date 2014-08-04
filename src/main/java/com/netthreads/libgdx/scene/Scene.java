/*
 * -----------------------------------------------------------------------
 * Copyright 2014 - Alistair Rutherford - www.netthreads.co.uk
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

package com.netthreads.libgdx.scene;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;

/**
 * Scene is a Stage which has input multiplexer.
 * 
 * Scenes are composed of layers.
 * 
 */
public class Scene extends Stage implements Node
{
	private static final int DEFAULT_LAYER_CAPACITY = 10;

	/**
	 * Associated input multiplexer.
	 */
	private InputMultiplexer inputMultiplexer;

	/**
	 * Scene elements as nodes. We need this so we can call enter and exit on actors in order to manage registration and
	 * de-registration of event handlers.
	 * 
	 */
	private Array<Node> nodes;

	/**
	 * The one and only director.
	 */
	private static Director director = AppInjector.getInjector().getInstance(Director.class);

	// All scenes share the same viewport.
	Viewport viewport;
	
	/**
	 * Scene uses director view-port and batch.
	 * 
	 * @param viewport The scene view-port.
	 */
	public Scene()
	{
		this(director.getViewport(), director.getSpriteBatch());
	}

	/**
	 * Scene uses director view-port.
	 * 
	 * @param batch The sprite batch to use.
	 */
	public Scene(SpriteBatch batch)
	{
		this(director.getViewport(), batch);
	}
	
	/**
	 * Constructor where we supply our own sprite batch.
	 * 
	 * @param viewport The scene view-port.
	 * @param batch
	 */
	public Scene(Viewport viewport, SpriteBatch batch)
	{
		super(viewport, batch);

		inputMultiplexer = new InputMultiplexer(this);

		nodes = new Array<Node>(DEFAULT_LAYER_CAPACITY);
	}

	/**
	 * Get input multiplexer.
	 * 
	 * @return The input multiplexer.
	 */
	public InputMultiplexer getInputMultiplexer()
	{
		return inputMultiplexer;
	}

	/**
	 * Add scene layer.
	 * 
	 * Note layer in nodes list.
	 * 
	 * @param layer
	 *            The new layer.
	 */
	public void addLayer(Layer layer)
	{
		nodes.add(layer);

		addActor(layer);
	}

	/**
	 * Remove scene layer.
	 * 
	 * @param layer
	 *            The target layer.
	 */
	public void removeLayer(Layer layer)
	{
		int index = nodes.indexOf(layer, false);
		if (index >= 0)
		{
			nodes.removeIndex(index);

			getRoot().removeActor(layer);
		}
	}

	/**
	 * Handle pre-display tasks.
	 * 
	 */
	@Override
	public void enter()
	{
		int size = nodes.size;
		for (int i = 0; i < size; i++)
		{
			nodes.get(i).enter();
		}
	}

	/**
	 * Handle post-display tasks.
	 * 
	 */
	@Override
	public void exit()
	{
		int size = nodes.size;
		for (int i = 0; i < size; i++)
		{
			nodes.get(i).exit();
		}
	}

}
