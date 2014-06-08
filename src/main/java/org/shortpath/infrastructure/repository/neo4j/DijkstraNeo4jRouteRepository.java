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
package org.shortpath.infrastructure.repository.neo4j;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * AbstractNeo4jRouteRepository implementation using the Dijkstra algorithm.
 * 
 * @author Daniel Perestrelo Vicente
 */
@Repository
public class DijkstraNeo4jRouteRepository extends AbstractNeo4jRouteRepository {

	/** Distance evaluator */
	final CostEvaluator<Double> costEvaluator = CommonEvaluators
			.doubleCostEvaluator(DISTANCE_PROPERTY_KEY);

	/**
	 * Path finder which uses the Dijkstra algorithm to find the cheapest path
	 * between two nodes.
	 */
	final PathFinder<WeightedPath> pathFinder = GraphAlgoFactory.dijkstra(
			PathExpanders.forTypeAndDirection(LocationRelationshipType.CONNECTED,
					Direction.OUTGOING), costEvaluator);
	
	/**
	 * Constructs a DijkstraNeo4jRouteRepository object.
	 * 
	 * @param graphDatabase
	 *            the graphDatabase to use.
	 * @throws IllegalArgumentException
	 *             if graphDatabase is null.
	 */
	@Autowired
	public DijkstraNeo4jRouteRepository(GraphDatabaseService graphDatabase) {
		super(graphDatabase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.poc.dijkstra.infrastructure.repository.neo4j.
	 * AbstractNeo4jRouteRepository#getPathFinder()
	 */
	@Override
	protected PathFinder<? extends Path> getPathFinder() {
		return pathFinder;
	}

}
