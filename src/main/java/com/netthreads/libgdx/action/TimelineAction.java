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

import aurelienribon.tweenengine.Timeline;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

/**
 * Time-line action runs a Tween time-line separate from the TweenManager.
 * 
 */
public class TimelineAction extends Action
{
	private static final Pool<TimelineAction> pool = new Pool<TimelineAction>(10, 100)
	{
		@Override
		protected TimelineAction newObject()
		{
			TimelineAction action = new TimelineAction();

			action.setPool(this);

			return action;
		}

		/**
		 * Override the 'free to ensure the object is removed from the 'world' as well.
		 */
		public void free(TimelineAction object)
		{
			super.free(object);

			object.getTimeline().free();
		}

	};

	private Timeline timeline;
	protected boolean done;

	/**
	 * Get instance from pool.
	 * 
	 * @param timeline
	 *            The action time-line to execute.
	 * 
	 * @return Pooled instance.
	 */
	public static TimelineAction $(Timeline timeline)
	{
		TimelineAction action = pool.obtain();

		action.setTimeline(timeline);

		return action;
	}

	@Override
	public boolean act(float delta)
	{
		boolean done = timeline.isFinished();

		if (!done)
		{
			timeline.update((int) (delta * 1000));
		}

		return done;
	}

	private void setTimeline(Timeline timeline)
	{
		this.timeline = timeline;
	}

	/**
	 * Fetch TimeLine reference.
	 * 
	 * @return The TimeLine reference.
	 */
	public Timeline getTimeline()
	{
		return timeline;
	}

}
