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

import org.shortpath.application.services.ServiceException;
import org.shortpath.application.services.ShippingRatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DefaultShippingRatingService test case.
 * 
 * @author Daniel Perestrelo Vicente
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DefaultShippingRatingServiceTest {

	/** Shipping rating service. */
	@Autowired
	private ShippingRatingService shippingRatingService;

	/**
	 * Test method for
	 * {@link org.shortpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRateZeroDistance() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(0, 10, 2.50),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.shortpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRateZeroPrice() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(25, 10, 0), 0.001);
	}

	/**
	 * Test method for
	 * {@link org.shortpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetShippingRateZeroMileage() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(25, 0, 2.5),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.shortpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRate() throws ServiceException {
		assertEquals(6.25, shippingRatingService.getShippingRate(25, 10, 2.50),
				0.001);
	}

}
