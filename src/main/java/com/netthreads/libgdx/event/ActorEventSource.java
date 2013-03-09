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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Event source.
 * 
 * Broadcast events to subscribed observers.
 * 
 */
public class ActorEventSource
{
	private final static int OBSERVERS_CAPACITY = 10;

	private final static int MAX_EVENTS = 100;

	private ArrayList<ActorEventObserver> observers;
	private LinkedList<ActorEvent> pool;
	private PriorityQueue<ActorEvent> events;

	/**
	 * Create observer list.
	 * 
	 */
	public ActorEventSource()
	{
		observers = new ArrayList<ActorEventObserver>(OBSERVERS_CAPACITY);

		pool = new LinkedList<ActorEvent>();
		events = new PriorityQueue<ActorEvent>(MAX_EVENTS);

		// Initialise the event pool.
		for (int index = 0; index < MAX_EVENTS; index++)
		{
			pool.add(new ActorEvent());
		}
	}

	/**
	 * Update any observers.
	 * 
	 */
	public void update()
	{
		if (events.size() > 0)
		{
			ActorEvent event = events.poll();

			if (event != null)
			{
				boolean handled = false;

				int size = observers.size();
				for (int index = 0; index < size; index++)
				{
					handled = observers.get(index).handleEvent(event);

					if (handled)
						break;
				}

				// Place data event back onto pool.
				pool.add(event);
			}
		}
	}

	/**
	 * Add observer.
	 * 
	 * @param observer
	 *            The target observer.
	 */
	public synchronized void addObserver(ActorEventObserver observer)
	{
		if (!observers.contains(observer))
		{
			observers.add(observer);
		}
	}

	/**
	 * Remove observer.
	 * 
	 * @param observer
	 *            Target event observer.
	 */
	public synchronized void removeObserver(ActorEventObserver observer)
	{
		if (observers.contains(observer))
		{
			observers.remove(observer);
		}
	}

	/**
	 * Enqueue event
	 * 
	 * @param id
	 *            The event id.
	 * @param actor
	 *            The associated actor.
	 */
	public void sendEvent(int id, Actor actor, int priority)
	{
		processEvent(id, actor, priority);
	}

	/**
	 * Map event data to pooled event.
	 * 
	 * Place pooled event on to broadcast queue.
	 * 
	 * @param id
	 *            The event id.
	 * @param actor
	 *            The associated actor.
	 */
	private void processEvent(int id, Actor actor, int priority)
	{
		ActorEvent event = pool.poll();

		if (event == null)
		{
			event = new ActorEvent();

			Gdx.app.log("ActorEventSource", "Warning, having to create pooled event, consider making the inital pool size larger, " + pool.size());
		}

		event.populate(id, actor, System.currentTimeMillis(), priority);

		events.add(event);
	}

	/**
	 * Clear structures.
	 * 
	 */
	public void clear()
	{
		observers.clear();
		events.clear();
	}

}
