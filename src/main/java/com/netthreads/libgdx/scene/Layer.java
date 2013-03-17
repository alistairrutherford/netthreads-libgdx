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

package com.netthreads.libgdx.scene;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Represents a visual layer which can receive input.
 * 
 * Layers are a group of actors or other groups.
 * 
 */
public class Layer extends Group implements InputProcessor, Node
{
	
	@Override
	public void enter()
	{
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		for (int i = 0; i < size; i++)
		{
			Actor actor = list.get(i);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				layer.enter();
			}
		}
	}
	
	@Override
	public void exit()
	{
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		for (int i = 0; i < size; i++)
		{
			Actor actor = list.get(i);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				layer.exit();
			}
		}
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		boolean handled = false;
		
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		int index = 0;
		while (index < size && !handled)
		{
			Actor actor = list.get(index);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				handled = layer.touchDown(x, y, pointer, button);
			}
			
			index++;
		}
		
		return handled;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		boolean handled = false;
		
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		int index = 0;
		while (index < size && !handled)
		{
			Actor actor = list.get(index);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				handled = layer.touchUp(x, y, pointer, button);
			}
			
			index++;
		}
		
		return handled;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		boolean handled = false;
		
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		int index = 0;
		while (index < size && !handled)
		{
			Actor actor = list.get(index);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				handled = layer.touchDragged(x, y, pointer);
			}
			
			index++;
		}
		
		return handled;
	}
	
	@Override
	public boolean mouseMoved(int x, int y)
	{
		boolean handled = false;
		
		SnapshotArray<Actor> list = getChildren();
		
		int size = list.size;
		int index = 0;
		while (index < size && !handled)
		{
			Actor actor = list.get(index);
			
			if (actor instanceof Layer)
			{
				Layer layer = (Layer) actor;
				
				handled = layer.mouseMoved(x, y);
			}
			
			index++;
		}
		
		return handled;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * We override the hit test for the layer to ensure it does not take part in
	 * the search for a target. This is to stop the layer from blocking passing
	 * on any hit tests to items on layers below it.
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable)
	{
		Actor hit = super.hit(x, y, touchable);
		
		if (hit == this)
		{
			hit = null;
		}
		
		return hit;
	}
}
