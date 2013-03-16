package com.netthreads.libgdx.director;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Central injector which holds our singletons.
 *
 */
public class AppInjector
{
	/**
	 * Guice injector.
	 */
	public static Injector injector = null;

	public static Injector getInjector()
	{
		if (injector==null)
		{
			injector = Guice.createInjector();			
		}
		
		return injector;
	}
}
