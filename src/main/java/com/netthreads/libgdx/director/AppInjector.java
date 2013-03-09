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
	public static Injector injector = Guice.createInjector();

	public static Injector getInjector()
	{
		return injector;
	}
}
