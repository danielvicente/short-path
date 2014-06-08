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
package org.shortpath.presentation.converter.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shortpath.domain.model.Leg;
import org.shortpath.presentation.converter.LogisticsNetworkConverter;
import org.springframework.stereotype.Component;

/**
 * Default LogisticsNetworkConverter interface implementation.
 * <p>
 * A Logistics Network consists of multiple Legs, each one defined as a line of
 * characters terminated either by a set of line terminator characters (\n or \r
 * or \r\n) or by the end of the stream. Each Leg has the syntax
 * <b>origin&lt;space&gt;destination&lt;space&gt;distance</b>.
 * </p>
 * Example:
 * 
 * <pre>
 * A B 10
 * B D 15
 * A C 20
 * C D 30
 * B E 50
 * D E 30
 * </pre>
 * 
 * @author Daniel Perestrelo Vicente
 */
@Component
public class DefaultLogisticsNetworkConverter implements
		LogisticsNetworkConverter {

	/** Lines delimiters. */
	private static final String LINE_DELIMITERS = "\r\n";

	/** Leg pattern. */
	private static final Pattern LEG_PATTERN = Pattern
			.compile("\\s*(\\w+)\\s*(\\w+)\\s*(\\d+)\\s*");

	/**
	 * Constructs a DefaultLogisticsNetworkConverter object.
	 */
	public DefaultLogisticsNetworkConverter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.shortpath.presentation.converter.LegConverter#parseLegs(
	 * java.lang.String)
	 */
	@Override
	public List<Leg> parseLegs(String lexicalLegs) {
		if (lexicalLegs == null) {
			throw new IllegalArgumentException("lexicalLegs is null");
		}

		// Lines tokenizer
		final StringTokenizer linesTokenizer = new StringTokenizer(lexicalLegs,
				LINE_DELIMITERS);

		// The legs list
		final List<Leg> legs = new LinkedList<Leg>();

		// Iterates over each line
		for (int lineNumber = 1; linesTokenizer.hasMoreTokens(); lineNumber++) {
			// Returns the next token in this string tokenizer's string
			final String line = linesTokenizer.nextToken();

			// Gets a matcher that will match the input against the pattern
			final Matcher matcher = LEG_PATTERN.matcher(line);

			// Checks if the line formatted correctly
			if (!matcher.matches()) {
				throw new IllegalArgumentException(String.format(
						"Invalid leg format at line %d", lineNumber));
			}

			// Parses the current line
			legs.add(new Leg(matcher.group(1), matcher.group(2), Double
					.valueOf(matcher.group(3))));
		}

		return legs;
	}

}
