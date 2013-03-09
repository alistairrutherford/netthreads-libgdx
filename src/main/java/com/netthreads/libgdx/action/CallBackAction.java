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
 * Delay which will wait stated amount before calling callback method. Execute N times or forever if N<0.
 * 
 */
public class CallBackAction extends Action
{
	static final Pool<CallBackAction> pool = new Pool<CallBackAction>(4, 100)
	{
		@Override
		protected CallBackAction newObject()
		{
			CallBackAction action = new CallBackAction();

			action.setPool(this);

			return action;
		}
	};

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
	public static CallBackAction $(ActionCallBack callBack)
	{
		CallBackAction callBackDelay = pool.obtain();
		callBackDelay.callBack = callBack;

		return callBackDelay;
	}

	/**
	 * Execute action.
	 * 
	 */
	@Override
	public boolean act(float delta)
	{
		boolean done = true;

		callBack.onCallBack();

		return done;
	}

}
