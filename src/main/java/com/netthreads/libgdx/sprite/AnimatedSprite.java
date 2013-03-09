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

package com.netthreads.libgdx.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * An AnimatedSprite is a group view into which the frame sprite draws itself.
 * We have to do this or we can't apply transformations from actions like rotate
 * or scale directly to the {@link FrameSprite}.
 * 
 */
public class AnimatedSprite extends Group
{
	private FrameSprite frameSprite;
	
	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 * @param rows
	 *            The animation texture rows.
	 * @param cols
	 *            The animation texture rows.
	 * @param frameDuration
	 *            The animation frame duration.
	 * @param looping
	 *            Flag to indicate we should loop the animation.
	 */
	public AnimatedSprite(TextureRegion textureRegion, int rows, int cols, float frameDuration, boolean looping)
	{
		frameSprite = new FrameSprite(textureRegion, rows, cols, frameDuration, looping);
		
		this.setWidth(frameSprite.getWidth());
		this.setHeight(frameSprite.getHeight());
		
		addActor(frameSprite);
	}
	
	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 * @param rows
	 *            The animation texture rows.
	 * @param cols
	 *            The animation texture rows.
	 * @param frameDuration
	 *            The animation frame duration.
	 */
	public AnimatedSprite(TextureRegion textureRegion, int rows, int cols, float frameDuration)
	{
		this(textureRegion, rows, cols, frameDuration, true);
	}
	
}
