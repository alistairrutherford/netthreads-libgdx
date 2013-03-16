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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Static sprite is texture encapsulated by Actor. It cannot be scaled or
 * rotated.
 * 
 */
public class StaticSprite extends Actor
{
	private TextureRegion textureRegion;
	
	/**
	 * Create sprite.
	 * 
	 * @param texture
	 *            The animation texture.
	 */
	public StaticSprite(TextureRegion textureRegion)
	{
		this.textureRegion = textureRegion;
		
		// Set the sprite width and height.
		this.setWidth(textureRegion.getRegionWidth());
		this.setHeight(textureRegion.getRegionHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.draw(textureRegion, getX(), getY());
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable)
	{
		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this : null;
	}
	
}
