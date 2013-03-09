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

package com.netthreads.libgdx.scene.transition;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;

import com.badlogic.gdx.utils.Pool;
import com.netthreads.libgdx.action.TimelineAction;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.tween.GroupAccessor;

/**
 * Transition: Move in from bottom right.
 * 
 */
public class MoveInBRTransitionScene extends TransitionScene
{
	private static Pool<MoveInBRTransitionScene> _pool = new Pool<MoveInBRTransitionScene>()
	{
		@Override
		protected MoveInBRTransitionScene newObject()
		{
			MoveInBRTransitionScene transitionScene = new MoveInBRTransitionScene();

			return transitionScene;
		}
	};

	/**
	 * Create transition.
	 * 
	 * @param inScene
	 *            The incoming scene.
	 * @param outScene
	 *            The outgoing scene.
	 * @param durationMillis
	 *            The duration of transition.
	 * @param easeEquation
	 *            The easing type.
	 */
	public static MoveInBRTransitionScene $(Scene inScene, Scene outScene, int durationMillis, TweenEquation easeEquation)
	{
		MoveInBRTransitionScene transitionScene = _pool.obtain();
		transitionScene.setInScene(inScene);
		transitionScene.setInSceneRoot(inScene.getRoot());
		transitionScene.setOutScene(outScene);
		transitionScene.setOutSceneRoot(outScene.getRoot());
		transitionScene.setDurationMillis(durationMillis);
		transitionScene.setEaseEquation(easeEquation);

		return transitionScene;
	}

	/**
	 * On entry build easing TimeLines.
	 * 
	 */
	@Override
	public void enter()
	{
		super.enter();
		
	    // In Scene TimeLine.
		Timeline inTimeline = Timeline.createSequence()
				.beginSequence()
					.push(Tween.to(getInSceneRoot(), GroupAccessor.POSITION_XY, 0).target(getInScene().getWidth(), -getInScene().getHeight()).ease(getEaseEquation()))
					.push(Tween.to(getInSceneRoot(), GroupAccessor.POSITION_XY, getDurationMillis()).target(0, 0).ease(getEaseEquation()))
				.end()
				.start();

	    // In Scene TimeLine Action.
		TimelineAction inTimelineAction = TimelineAction.$(inTimeline);
		getInSceneRoot().addAction(inTimelineAction);

	    // Out Scene TimeLine.
		Timeline outTimeline = Timeline.createSequence()
				.beginSequence()
					.push(Tween.to(getOutSceneRoot(), GroupAccessor.POSITION_XY, 0).target(0, 0).ease(getEaseEquation()))
					.push(Tween.to(getOutSceneRoot(), GroupAccessor.POSITION_XY, getDurationMillis()).target(-getOutScene().getWidth(), getOutScene().getHeight()).ease(getEaseEquation()))
					.setCallbackTriggers(TweenCallback.COMPLETE)
				    .setCallback(this)
				.end()
				.start();

	    // Out Scene TimeLine Action.
		TimelineAction outTimelineAction = TimelineAction.$(outTimeline);
		getOutSceneRoot().addAction(outTimelineAction);
	}

	/**
	 * On exit tidy up.
	 * 
	 */
	@Override
	public void exit()
	{
		super.exit();

		_pool.free(this);
	}
}
