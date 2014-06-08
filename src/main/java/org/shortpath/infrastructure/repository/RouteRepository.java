/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Daniel Perestrelo Vicente
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.shortpath.infrastructure.repository;

import java.util.List;

import org.shortpath.domain.model.Route;
import org.shortpath.domain.model.Leg;

/**
 * This interface defines a Route repository.
 * 
 * @author Daniel Perestrelo Vicente
 */
public interface RouteRepository {

	/**
	 * Saves a Leg into the repository. If the Leg already exists in the
	 * Repository, it will be updated.
	 * 
	 * @param leg
	 *            the leg to be saved.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public void saveLeg(Leg leg) throws RepositoryExeption;

	/**
	 * Saves all given Legs into the repository. If a Leg already exists in the
	 * Repository, it will be updated.
	 * 
	 * @param legs
	 *            the list of legs to be saved.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public void saveLegs(List<Leg> legs) throws RepositoryExeption;

	/**
	 * Tries to find the shortest route between the origin and the destination.
	 * 
	 * @param origin
	 *            the route origin.
	 * @param destination
	 *            the route destination.
	 * @return the shortest route between the origin and the destination.
	 * @throws RouteNotFoundRepositoryExeption
	 *             if no route could be found between the origin and
	 *             destination.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public Route findShortestRoute(String origin, String destination)
			throws RouteNotFoundRepositoryExeption, RepositoryExeption;

}
