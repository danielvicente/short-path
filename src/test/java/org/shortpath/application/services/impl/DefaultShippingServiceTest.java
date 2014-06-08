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
package org.shortpath.application.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.shortpath.application.services.NoShippingRouteServiceException;
import org.shortpath.application.services.ServiceException;
import org.shortpath.application.services.ShippingService;
import org.shortpath.domain.model.Leg;
import org.shortpath.domain.model.LogisticsNetwork;
import org.shortpath.domain.model.Route;
import org.shortpath.domain.model.ShippingDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DefaultShippingService test case.
 * 
 * @author Daniel Perestrelo Vicente
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DefaultShippingServiceTest {

	/** Shipping service. */
	@Autowired
	private ShippingService shippingService;

	/**
	 * Sets up the fixture.
	 * 
	 * @throws Exception
	 *             if an error occurs.
	 */
	@Before
	public void setUp() throws Exception {
		// Creates the route legs
		final List<Leg> legs = new LinkedList<Leg>();
		legs.add(new Leg("A", "B", 10));
		legs.add(new Leg("B", "D", 15));
		legs.add(new Leg("A", "C", 20));
		legs.add(new Leg("C", "D", 20));
		legs.add(new Leg("B", "E", 50));
		legs.add(new Leg("D", "E", 50));

		// Creates the logistics network
		final LogisticsNetwork logisticsNetwork = new LogisticsNetwork(
				"Sample Logistics Network", legs);

		// Registers the logistics network
		shippingService.addLogisticsNetwork(logisticsNetwork);
	}

	/**
	 * Test method for
	 * {@link org.shortpath.application.services.impl.DefaultShippingService#getShippingDetails(java.lang.String, java.lang.String, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingDetails()
			throws NoShippingRouteServiceException, ServiceException {
		// Gets the order shipping details
		final ShippingDetails shippingDetails = shippingService
				.getShippingDetails("A", "D", 10, 2.5);

		// Expected legs
		final List<Leg> expectedLegs = new LinkedList<Leg>();
		expectedLegs.add(new Leg("A", "B", 10));
		expectedLegs.add(new Leg("B", "D", 15));

		// Expected route
		final Route expectedRoute = new Route("A", "D", expectedLegs);

		// Asserts that the expected and the actual values are equals
		assertEquals(6.25, shippingDetails.getShippingRate(), 0.001);
		assertEquals(expectedRoute, shippingDetails.getShippingRoute());
	}

}
