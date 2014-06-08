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
package org.shortpath.domain.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * This class represents a route between two locations.
 * 
 * @author Daniel Perestrelo Vicente
 */
public class Route implements Serializable {

	/** Serial version id. */
	private static final long serialVersionUID = 1L;

	/** Origin. */
	private String origin;

	/** Destination. */
	private String destination;

	/** The list of legs associated with this route. */
	private List<Leg> legs = null;

	/**
	 * Creates a new Route object.
	 * 
	 * @param origin
	 *            the route origin.
	 * @param destination
	 *            the route destination.
	 * @param legs
	 *            the list of legs associated with this route.
	 */
	public Route(String origin, String destination, List<Leg> legs) {
		setOrigin(origin);
		setDestination(destination);
		setLegs(legs);
	}

	/**
	 * Gets the route origin.
	 * 
	 * @return the route origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the route origin.
	 * 
	 * @param origin
	 *            the origin to set.
	 * @throws IllegalArgumentException
	 *             if origin is null.
	 */
	private void setOrigin(String origin) {
		if (origin == null) {
			throw new IllegalArgumentException("origin is null");
		}
		this.origin = origin;
	}

	/**
	 * Gets the route destination.
	 * 
	 * @return the route destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the route destination.
	 * 
	 * @param destination
	 *            the destination to set.
	 * @throws IllegalArgumentException
	 *             if destination is null.
	 */
	private void setDestination(String destination) {
		if (destination == null) {
			throw new IllegalArgumentException("destination is null");
		}
		this.destination = destination;
	}

	/**
	 * Gets an unmodifiable view of the list of legs associated with this route.
	 * 
	 * @return an unmodifiable view of the list of legs associated with this
	 *         route.
	 */
	public List<Leg> getLegs() {
		return Collections.unmodifiableList(legs);
	}

	/**
	 * Sets the list of legs associated with this route.
	 * 
	 * @param distance
	 *            the list of legs associated with this route.
	 * @throws IllegalArgumentException
	 *             if legs is null.
	 */
	private void setLegs(List<Leg> legs) {
		if (legs == null) {
			throw new IllegalArgumentException("legs is null");
		}
		this.legs = legs;
	}

	/**
	 * Gets the length of the route.
	 * 
	 * @return the length of the route.
	 */
	public double getLength() {
		double length = 0;
		for (Leg leg : legs) {
			length += leg.getDistance();
		}
		return length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(origin).append(destination)
				.append(legs).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Route rhs = (Route) obj;
		return new EqualsBuilder().append(origin, rhs.origin)
				.append(destination, rhs.destination).append(legs, rhs.legs)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
