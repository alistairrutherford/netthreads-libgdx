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

package com.netthreads.libgdx.director;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Singleton;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.event.ActorEventSource;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.tween.ActorAccessor;
import com.netthreads.libgdx.tween.GroupAccessor;

/**
 * Scene director.
 * 
 * This class routes actor events around observers and updates the view. It also maintains global note of view width and
 * height.
 * 
 */
@Singleton
public class Director implements Disposable
{
	private static final float DEFAULT_CLEAR_COLOUR_RED = 0.0f;
	private static final float DEFAULT_CLEAR_COLOUR_BLUE = 0.0f;
	private static final float DEFAULT_CLEAR_COLOUR_GREEN = 0.0f;
	private static final float DEFAULT_CLEAR_COLOUR_ALPHA = 1.0f;

	private ActorEventSource eventSource;

	private Viewport viewport;

	private Scene scene;

	private float scaleFactorX;
	private float scaleFactorY;

	private SpriteBatch spriteBatch;

	private float clearColourR = DEFAULT_CLEAR_COLOUR_RED;
	private float clearColourB = DEFAULT_CLEAR_COLOUR_BLUE;
	private float clearColourG = DEFAULT_CLEAR_COLOUR_GREEN;
	private float clearColourA = DEFAULT_CLEAR_COLOUR_ALPHA;

	private static final int DEFAULT_WIDTH = 320;
	private static final int DEFAULT_HEIGHT = 480;

	/**
	 * Construct Director elements.
	 * 
	 */
	public Director()
	{
		scene = null;

		// This required Graphics context.
		spriteBatch = new SpriteBatch();

		// Director maintains event source.
		eventSource = new ActorEventSource();

		// These are scale factors for adjusting touch events to the actual size
		// of the view-port.
		scaleFactorX = 1;
		scaleFactorY = 1;

		// All scenes share the same viewport.
		viewport = null;

		// Set up tween default configuration.
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		Tween.registerAccessor(Group.class, new GroupAccessor());
	}

	/**
	 * Update main loop.
	 * 
	 */
	public void update()
	{
		// Update events.
		eventSource.update();

		// Update View
		Gdx.gl.glClearColor(clearColourR, clearColourB, clearColourG, clearColourA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (scene != null)
		{
			float delta = Gdx.graphics.getDeltaTime();

			scene.act(delta);

			scene.draw();
		}
		else
		{
			Gdx.app.log("Director", "WTF! - No scene");
		}
	}

	/**
	 * Set the current scene.
	 * 
	 * @param scene
	 */
	public synchronized void setScene(Scene scene)
	{
		// If already active scene...
		if (this.scene != null)
		{
			// Exit stage left..
			this.scene.exit();
		}

		this.scene = scene;

		if (this.scene != null)
		{
			// Enter stage right..
			this.scene.enter();

			// NOTE: Route input events to the scene.
			Gdx.input.setInputProcessor(scene.getInputMultiplexer());
		}

	}

	/**
	 * Return currently running scene.
	 * 
	 * @return The current scene view.
	 */
	public synchronized Scene getScene()
	{
		return scene;
	}

	/**
	 * Return scene width.
	 * 
	 * @return The width.
	 */
	public float getWidth()
	{
		return getViewport().getWorldWidth();
	}

	/**
	 * Return scene height.
	 * 
	 * @return The height.
	 */
	public float getHeight()
	{
		return getViewport().getWorldHeight();
	}

	/**
	 * Set display width/height.
	 * 
	 * We need to ensure this is done when a client application is created otherwise the default viewport will be used.
	 * 
	 * @param width
	 * @param height
	 */
	public void setWidthHeight(int width, int height)
	{
		if (viewport == null)
		{
			viewport = new StretchViewport(width, height);
		}

		if (scene != null)
		{
			viewport.update(width, height, true);
		}
	}

	/**
	 * Adjust the scale factors for touch/mouse events to match the size of the stage.
	 * 
	 * @param width
	 *            The new width.
	 * @param height
	 *            The new height.
	 */
	public void resize(int width, int height)
	{
		setWidthHeight(width, height);

		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();

		scaleFactorX = (float) worldWidth / width;
		scaleFactorY = (float) worldHeight / height;
	}

	/**
	 * Send event to observers.
	 * 
	 * @param id
	 *            The event id.
	 * @param actor
	 *            The associated actor.
	 */
	public void sendEvent(int id, Actor actor)
	{
		eventSource.sendEvent(id, actor, ActorEvent.DEFAULT_PRIORITY);
	}

	/**
	 * Send event to observers.
	 * 
	 * @param id
	 *            The event id.
	 * @param actor
	 *            The associated actor.
	 * @param priority
	 *            Adopt a priority value. Must be > 0.
	 */
	public void sendEvent(int id, Actor actor, int priority)
	{
		eventSource.sendEvent(id, actor, priority);
	}

	/**
	 * Add event observer event handler.
	 * 
	 * DO NOT PUT THIS INTO THE CONSTRUCTOR. IT MUST GO INTO THE "ENTER" HANDLER.
	 * 
	 * @param observer
	 *            The event observer.
	 */
	public void registerEventHandler(ActorEventObserver observer)
	{
		eventSource.addObserver(observer);
	}

	/**
	 * Remove event observer event handler.
	 * 
	 * DO NOT FORGET TO PUT THIS INTO THE "EXIT" HANDLER IF YOU HAVE MATCHING "REGISTER" IN THE ENTER HANDLER.
	 * 
	 * @param observer
	 *            The event observer.
	 */
	public void deregisterEventHandler(ActorEventObserver observer)
	{
		eventSource.removeObserver(observer);
	}

	/**
	 * Clear all handlers.
	 * 
	 */
	public void clearEventHandlers()
	{
		// Clear all event subscriptions.
		eventSource.clear();
	}

	/**
	 * Return scale factor for touch/mouse events.
	 * 
	 * @return The x scale factor.
	 */
	public float getScaleFactorX()
	{
		return scaleFactorX;
	}

	/**
	 * Return scale factor for touch/mouse events.
	 * 
	 * @return The y scale factor.
	 */
	public float getScaleFactorY()
	{
		return scaleFactorY;
	}

	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch)
	{
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void dispose()
	{
		spriteBatch.dispose();
	}

	public void setEventSource(ActorEventSource eventSource)
	{
		this.eventSource = eventSource;
	}

	public void setClearColourR(float clearColourR)
	{
		this.clearColourR = clearColourR;
	}

	public void setClearColourB(float clearColourB)
	{
		this.clearColourB = clearColourB;
	}

	public void setClearColourG(float clearColourG)
	{
		this.clearColourG = clearColourG;
	}

	public void setClearColourA(float clearColourA)
	{
		this.clearColourA = clearColourA;
	}

	/**
	 * We have to create a dummy viewport if one doesn't exist or things will crash.
	 * 
	 * @return The current viewport.
	 */
	public Viewport getViewport()
	{
		if (viewport == null)
		{
			viewport = new StretchViewport(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		}

		return viewport;
	}

}
