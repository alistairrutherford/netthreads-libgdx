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

package com.netthreads.libgdx.event;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Base event type.
 * 
 */
public class ActorEvent implements Comparable<ActorEvent>
{
	public static final int HIGH_PRIORITY = 1;
	public static final int DEFAULT_PRIORITY = -1;

	private int id;
	private long time;
	private long priority;
	private Actor actor;

	/**
	 * Populate event with default priority.
	 * 
	 * @param id
	 * @param node
	 * @param time
	 */
	public void populate(int id, Actor node, long time)
	{
		// When no priority is specified we adopt the time-stamp.
		populate(id, node, time, time);
	}

	/**
	 * Populate data event from system event.
	 * 
	 * @param id
	 *            Event id.
	 * @param node
	 *            Associated event actor.
	 * @param time
	 *            Event time.
	 */
	public void populate(int id, Actor node, long time, long priority)
	{
		// Copy fields.
		this.id = id;

		this.actor = node;

		this.time = time;

		if (priority == DEFAULT_PRIORITY)
		{
			this.priority = time;
		}
		else
		{
			this.priority = priority;
		}

	}

	/**
	 * Get event id.
	 * 
	 * @return The id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Get actor.
	 * 
	 * @return The Actor.
	 */
	public Actor getActor()
	{
		return actor;
	}

	/**
	 * Get timestamp.
	 * 
	 * @return
	 */
	public long getTime()
	{
		return time;
	}

	/**
	 * Return priority value.
	 * 
	 * @return The value.
	 */
	public long getPriority()
	{
		return priority;
	}

	/**
	 * Set priority.
	 * 
	 * @param priority
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	/**
	 * Implements comparison.
	 * 
	 * The lower the priority value the higher the
	 */
	@Override
	public int compareTo(ActorEvent o)
	{
		if (priority < o.getPriority())
		{
			return -1;
		}
		else if (priority > o.getPriority())
		{
			return 1;
		}

		return 0;
	}

}
