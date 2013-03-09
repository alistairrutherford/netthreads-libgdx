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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Helper methods for traversing through scenes and actors.
 * 
 * I have put this stuff here to void having to subclass the base types from LibGdx anymore than I have to.
 * 
 */
public class SceneHelper
{
	private static final Vector2 point = new Vector2();

	/**
	 * Find hit class.
	 * 
	 * @param x
	 *            Current x position.
	 * @param y
	 *            Current y position
	 * @param group
	 *            The starting Group.
	 * @param targetClass
	 *            The target class type.
	 * 
	 * @return The target or null if not found.
	 */
	@SuppressWarnings("rawtypes")
	public static Actor hit(float x, float y, Group group, Class targetClass)
	{
		SnapshotArray<Actor> children = group.getChildren();

		Actor hit = null;
		boolean found = false;
		int index = children.size - 1;
		while (!found && index >= 0)
		{
			Actor child = children.get(index);

			if (child.getClass().isAssignableFrom(targetClass))
			{
				point.x = x;
				point.y = y;

				group.localToDescendantCoordinates(child, point);

				if (child.hit(point.x, point.y, true) != null)
				{
					found = true;
					hit = child;
				}
				else if (child instanceof Group)
				{
					child = hit(x, y, (Group) child, targetClass);
				}
			}

			index--;
		}

		return hit;
	}

	/**
	 * Look for intersection between two actors.
	 * 
	 * I have defined this here as I can't extend the base classes Actor and Group.
	 * 
	 * @param x
	 *            The x pos rectangle.
	 * @param y
	 *            The y pos rectangle.
	 * @param width
	 *            The rectangle width.
	 * @param height
	 *            The rectangle height.
	 * @param group
	 *            The group.
	 * @param targetClass
	 *            The target class.
	 * 
	 * @return Target class, null if no intersection.
	 */
	@SuppressWarnings("rawtypes")
	public static Actor intersects(float x, float y, float width, float height, Group group, Class targetClass)
	{
		SnapshotArray<Actor> children = group.getChildren();

		Actor hit = null;
		int index = children.size - 1;
		while (hit == null && index >= 0)
		{
			Actor child = children.get(index);

			point.x = x;
			point.y = y;

			group.localToDescendantCoordinates(child, point);

			// If child is our target class then immediately check for
			// intersection
			if (child.getClass().equals(targetClass))
			{
				if (isIntersect(point.x, point.y, width, height, 0, 0, child.getWidth(), child.getHeight()))
				{
					hit = child;
				}
			}
			else if (child instanceof Group)
			{
				hit = intersects(point.x, point.y, width, height, (Group) child, targetClass);
			}

			index--;
		}

		return hit;
	}

	/**
	 * Makes simple intersection check.
	 * 
	 * @param sourceX
	 *            Source rectangle x pos.
	 * @param sourceY
	 *            Source rectangle x pos.
	 * @param sourceWidth
	 *            Source rectangle width.
	 * @param sourceHeight
	 *            Source rectangle height.
	 * @param targetX
	 *            Target rectangle x pos.
	 * @param targetY
	 *            Source rectangle y pos.
	 * @param targetWidth
	 *            Source rectangle width.
	 * @param targetHeight
	 *            Source rectangle height.
	 * 
	 * @return True if two rectangles intersect.
	 */
	public static boolean isIntersect(float sourceX, float sourceY, float sourceWidth, float sourceHeight, float targetX, float targetY, float targetWidth, float targetHeight)
	{
		float left1 = sourceX;
		float left2 = targetX;
		float right1 = sourceX + sourceWidth;
		float right2 = targetX + targetWidth;

		float top1 = sourceY;
		float top2 = targetY;
		float bottom1 = sourceY + sourceHeight;
		float bottom2 = targetY + targetHeight;

		if (bottom1 < top2)
			return false;
		if (top1 > bottom2)
			return false;

		if (right1 < left2)
			return false;
		if (left1 > right2)
			return false;

		return true;
	}

	/**
	 * Look for target hit of specified class.
	 * 
	 * @param x
	 *            Current x position.
	 * @param y
	 *            Current y position
	 * @param stage
	 *            The starting stage.
	 * @param targetClass
	 *            The target class type.
	 * @return Target class or null if not found.
	 */
	@SuppressWarnings("rawtypes")
	public static Actor hit(float x, float y, Stage stage, Class targetClass)
	{
		Group root = stage.getRoot();

		SnapshotArray<Actor> children = root.getChildren();

		Actor hit = null;
		boolean found = false;
		int index = children.size - 1;
		while (!found && index >= 0)
		{
			Actor child = children.get(index);

			point.x = x;
			point.y = y;

			root.localToDescendantCoordinates(child, point);

			Actor childHit = root.hit(point.x, point.y, true);

			if (childHit != null && childHit.getClass().isAssignableFrom(targetClass))
			{
				found = true;
				hit = childHit;
			}
			else
			{
				index--;
			}
		}

		return hit;
	}

}
