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

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

/**
 * Tween action runs a Tween separate from the TweenManager.
 * 
 */
public class TweenAction extends Action
{
	private static final Pool<TweenAction> pool = new Pool<TweenAction>(10, 100)
	{
		@Override
		protected TweenAction newObject()
		{
			TweenAction action = new TweenAction();
			
			action.setPool(this);
			
			return action;
		}
		
		public void free(TweenAction object)
		{
			super.free(object);

			object.getTween().free();
		}
		
	};
	
	private Tween tween;
	protected boolean done;
	
	/**
	 * Get instance from pool.
	 * 
	 * @param tween
	 *            The associated tween.
	 * 
	 * @return Pooled instance.
	 */
	public static TweenAction $(Tween tween)
	{
		TweenAction action = pool.obtain();
		
		action.setTween(tween);
		
		return action;
	}
	
	@Override
	public boolean act(float delta)
	{
		boolean done = tween.isFinished();
		
		if (!done)
		{
			tween.update((int) (delta * 1000));
		}
		
		return done;
	}
	
	private void setTween(Tween tween)
	{
		this.tween = tween;
	}
	
	public Tween getTween()
	{
		return tween;
	}
	
	/**
	 * Set done status.
	 * 
	 * @param done
	 */
	public void setDone(boolean done)
	{
		this.done = done;
	}
	
}
