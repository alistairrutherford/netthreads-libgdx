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

package com.netthreads.libgdx.action;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

/**
 * BodyGravityUpdateAction - Box2D Action
 * 
 * Apply increment of gravity force to body to simulate sinking or floating.
 * 
 */
public class BodyGravityAction extends Action
{
	private World world;
	private Body body;
	private float gravityOffset;
	private boolean randomise;
	
	private static final Pool<BodyGravityAction> pool = new Pool<BodyGravityAction>(10, 100)
	{
		@Override
		protected BodyGravityAction newObject()
		{
			BodyGravityAction action = new BodyGravityAction();
			
			action.setPool(this);
			
			return action;
		}
	};
	
	/**
	 * Get instance from pool.
	 * 
	 * @param world
	 *            The Box2D world reference.
	 * @param body
	 *            The Box2D body reference.
	 * @param gravityOffset
	 *            The gravity increment from the world setting to apply.
	 * @param randomise
	 *            Whether to randomise across the offset value to get more
	 *            realistic behaviour.
	 * @return
	 */
	public static BodyGravityAction $(World world, Body body, float gravityOffset, boolean randomise)
	{
		BodyGravityAction action = pool.obtain();
		
		action.setWorld(world);
		action.setBody(body);
		action.setGravityOffset(gravityOffset);
		action.setRandomise(randomise);
		
		return action;
	}
	
	/**
	 * Action.
	 * 
	 */
	@Override
	public boolean act(float delta)
	{
		float gravityToApply = world.getGravity().y;
		
		if (randomise)
		{
			gravityToApply = gravityToApply + ((float) Math.random() * gravityOffset);
		}
		else
		{
			gravityToApply = gravityToApply + gravityOffset;
		}
		
		body.applyForceToCenter(0, gravityToApply);
		
		return false;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	/**
	 * Set Body reference.
	 * 
	 * @param body
	 */
	private void setBody(Body body)
	{
		this.body = body;
	}
	
	/**
	 * Fetch Body reference.
	 * 
	 * @return The Body reference.
	 */
	public Body getBody()
	{
		return body;
	}
	
	public float getGravityOffset()
	{
		return gravityOffset;
	}
	
	public void setGravityOffset(float gravityOffset)
	{
		this.gravityOffset = gravityOffset;
	}
	
	public boolean isRandomise()
	{
		return randomise;
	}
	
	public void setRandomise(boolean randomise)
	{
		this.randomise = randomise;
	}
	
}
