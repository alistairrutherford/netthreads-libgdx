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

package com.netthreads.libgdx.scene.transition;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Scene;

/**
 * Base transition class.
 * 
 * This class forms basis of scene transition. Draws the incoming and outgoing
 * scenes. Ensures scene contents positions are noted on entry and reset on
 * exit. Implements transition "complete" handler to set incoming scene as main
 * scene.
 * 
 */
public class TransitionScene extends Scene implements TweenCallback
{
	private boolean complete;
	
	private float inX;
	private float inY;
	private float outX;
	private float outY;
	
	private Scene inScene;
	private Scene outScene;
	private Group inSceneRoot;
	private Group outSceneRoot;
	
	private int durationMillis;
	private TweenEquation easeEquation;
	
	/**
	 * The one and only director.
	 */
	private static Director director = AppInjector.getInjector().getInstance(Director.class);;
	
	/**
	 * Enter handler makes a note of scene contents position.
	 * 
	 */
	@Override
	public void enter()
	{
		this.complete = false;
		
		// Make note of starting positions. We are going to have to reset these
		// back when we finish.
		inX = inSceneRoot.getX();
		inY = inSceneRoot.getY();
		
		outX = outSceneRoot.getX();
		outY = outSceneRoot.getY();
	}
	
	/**
	 * Exit handler resets scene contents positions.
	 * 
	 */
	@Override
	public void exit()
	{
		this.complete = true;
		
		inSceneRoot.setX(inX);
		inSceneRoot.setY(inY);
		
		outSceneRoot.setX(outX);
		outSceneRoot.setY(outY);
	}
	
	/**
	 * Draw both scenes as we animated contents.
	 * 
	 */
	@Override
	public void draw()
	{
		// Draw
		if (!complete)
		{
			outScene.draw();
		}
		
		inScene.draw();
	}

	/**
	 * Keep the incoming and outgoing scene action pipelines running.
	 * 
	 */
	@Override
	public void act(float delta)
	{
	    super.act(delta);
	    
		// Move
		inSceneRoot.act(delta);
		outSceneRoot.act(delta);
	}
	
	/**
	 * Default transition handlers sets inScen to centre-stage when transition
	 * complete.
	 */
	@Override
	public void onEvent(int eventType, BaseTween<?> source)
	{
		switch (eventType)
		{
			case COMPLETE:
				director.setScene(this.inScene);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Transition complete.
	 * 
	 * @return The transition complete handler.
	 */
	public boolean isComplete()
	{
		return complete;
	}
	
	public float getInX()
	{
		return inX;
	}
	
	public void setInX(float inX)
	{
		this.inX = inX;
	}
	
	public float getInY()
	{
		return inY;
	}
	
	public void setInY(float inY)
	{
		this.inY = inY;
	}
	
	public float getOutX()
	{
		return outX;
	}
	
	public void setOutX(float outX)
	{
		this.outX = outX;
	}
	
	public float getOutY()
	{
		return outY;
	}
	
	public void setOutY(float outY)
	{
		this.outY = outY;
	}
	
	public Scene getInScene()
	{
		return inScene;
	}
	
	public void setInScene(Scene inScene)
	{
		this.inScene = inScene;
	}
	
	public Scene getOutScene()
	{
		return outScene;
	}
	
	public void setOutScene(Scene outScene)
	{
		this.outScene = outScene;
	}
	
	public Group getInSceneRoot()
	{
		return inSceneRoot;
	}
	
	public void setInSceneRoot(Group inSceneRoot)
	{
		this.inSceneRoot = inSceneRoot;
	}
	
	public Group getOutSceneRoot()
	{
		return outSceneRoot;
	}
	
	public void setOutSceneRoot(Group outSceneRoot)
	{
		this.outSceneRoot = outSceneRoot;
	}
	
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
	
	public int getDurationMillis()
	{
		return durationMillis;
	}
	
	public void setDurationMillis(int durationMillis)
	{
		this.durationMillis = durationMillis;
	}
	
	public TweenEquation getEaseEquation()
	{
		return easeEquation;
	}
	
	public void setEaseEquation(TweenEquation easeEquation)
	{
		this.easeEquation = easeEquation;
	}
	
}
