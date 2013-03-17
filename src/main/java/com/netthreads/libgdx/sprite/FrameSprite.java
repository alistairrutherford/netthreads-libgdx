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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Frame animation Sprite.
 * 
 * This is more or less just a generalised version of the example from the
 * library.
 * 
 */
public class FrameSprite extends Actor
{
	private TextureRegion[] frames;
	
	private Animation animation;
	
	private TextureRegion currentFrame;
	
	private float stateTime;
	private boolean looping;
	
	/**
	 * Construct sprite.
	 * 
	 * @param texture
	 *            Sprite-sheet texture.
	 * @param rows
	 *            Sprite-sheet rows.
	 * @param cols
	 *            Sprite-sheet columns.
	 * @param frameDuration
	 *            Duration between frames.
	 * @param looping
	 *            Loop animation.
	 */
	public FrameSprite(TextureRegion texture, int rows, int cols, float frameDuration, boolean looping)
	{
		this.looping = looping;
		
		int tileWidth = texture.getRegionWidth() / cols;
		int tileHeight = texture.getRegionHeight() / rows;
		TextureRegion[][] tmp = texture.split(tileWidth, tileHeight);
		frames = new TextureRegion[cols * rows];
		
		int index = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				frames[index++] = tmp[i][j];
			}
		}
		
		// Set the sprite width and height.
		setWidth(tileWidth);
		setHeight(tileHeight);
		
		animation = new Animation(frameDuration, frames);
		stateTime = 0f;
	}
	
	/**
	 * Draw sprite.
	 * 
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, looping);
		
		batch.draw(currentFrame, getX(), getY());
	}
	
	/**
	 * We have to implement hit check at lowest level.
	 * 
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable)
	{
		return x > 0 && x < getWidth() && y > 0 && y < getHeight() ? this : null;
	}
	
	/**
	 * Reset animation.
	 * 
	 * You can use this to ensure the animation plays from the start again. It's
	 * handy if you have one-shot animations like explosions but you are using
	 * re-usable Sprites. You must reset the animation to ensure the animation
	 * plays back again.
	 */
	public void resetAnimation()
	{
		stateTime = 0;
	}
	
	/**
	 * Check to see if animation finished.
	 * 
	 * @param stateTime
	 * 
	 * @return True if finished.
	 */
	public boolean isAnimationFinished()
	{
		return animation.isAnimationFinished(stateTime);
	}
	
}
