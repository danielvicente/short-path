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
package org.shortpath.presentation.converter;

import java.util.List;

import org.shortpath.domain.model.Leg;

/**
 * Logistics network converter.
 * 
 * @author Daniel Perestrelo Vicente
 */
public interface LogisticsNetworkConverter {

	/**
	 * Converts the string argument into a list of legs.
	 * 
	 * @param lexicalLegs
	 *            a string containing the lexical representation of the list of
	 *            legs.
	 * @return a list of legs value represented by the string argument.
	 * @throws IllegalArgumentException
	 *             if string parameter does not conform to lexical the lexical
	 *             representation of the list of legs.
	 */
	public List<Leg> parseLegs(String lexicalLegs);

}
