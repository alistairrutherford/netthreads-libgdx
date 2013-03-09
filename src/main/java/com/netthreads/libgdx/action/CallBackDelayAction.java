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
 * Delay which will wait stated amount before calling callback method. Execute N
 * times or forever if N<0.
 * 
 */
public class CallBackDelayAction extends Action
{
	static final Pool<CallBackDelayAction> pool = new Pool<CallBackDelayAction>(4, 100)
	{
		@Override
		protected CallBackDelayAction newObject()
		{
			CallBackDelayAction action = new CallBackDelayAction();

			action.setPool(this);
			
			return action;
		}
	};
	
	private int count;
	private int current;
	private float taken;
	private float duration;
	private ActionCallBack callBack;
	
	/**
	 * Pooled constructor.
	 * 
	 * @param count
	 *            Call back N times.
	 * @param duration
	 *            The duration between call-backs.
	 * @param callBack
	 *            The call back object reference.
	 * 
	 * @return The pooled object.
	 */
	public static CallBackDelayAction $(int count, float duration, ActionCallBack callBack)
	{
		CallBackDelayAction callBackDelay = pool.obtain();
		callBackDelay.duration = duration;
		callBackDelay.callBack = callBack;
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
				
				callBack.onCallBack();
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
