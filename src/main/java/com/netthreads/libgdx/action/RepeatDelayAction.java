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

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

/**
 * Delay which will wait stated amount before calling action. Execute N times or
 * forever if N<0. Will exit if associated action is done.
 * 
 */
public class RepeatDelayAction extends Action
{
	static final Pool<RepeatDelayAction> pool = new Pool<RepeatDelayAction>(4, 100)
	{
		@Override
		protected RepeatDelayAction newObject()
		{
			RepeatDelayAction action = new RepeatDelayAction();
			
			action.setPool(this);
			
			return action;
		}
	};
	
	private int count;
	private int current;
	private float taken;
	private float duration;
	private Action action;
	
	/**
	 * Pooled constructor.
	 * 
	 * @param count
	 *            Call back N times.
	 * @param duration
	 *            The duration between call-backs.
	 * @param action
	 *            Action to execute.
	 * 
	 * @return The pooled object.
	 */
	public static RepeatDelayAction $(int count, float duration, Action action)
	{
		RepeatDelayAction callBackDelay = pool.obtain();
		callBackDelay.duration = duration;
		callBackDelay.action = action;
		callBackDelay.count = count;
		callBackDelay.current = count;
		
		return callBackDelay;
	}
	
	@Override
	public void reset()
	{
		super.reset();
		
		this.taken = 0;
	}
	
	/**
	 * Execute action.
	 * 
	 */
	@Override
	public boolean act(float delta)
	{
		boolean done = false;
		
		taken += delta;
		if (taken > duration)
		{
			if (count < 0 || current > 0)
			{
				if (current > 0)
					current--;
				
				action.act(delta);
			}
			else
			{
				done = true;
			}
			
			// Reset delay.
			this.taken = 0;
		}
		
		return done;
	}
	
}
