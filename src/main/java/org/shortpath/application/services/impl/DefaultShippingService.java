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

import org.shortpath.application.services.NoShippingRouteServiceException;
import org.shortpath.application.services.ServiceException;
import org.shortpath.application.services.ShippingRatingService;
import org.shortpath.application.services.ShippingService;
import org.shortpath.domain.model.LogisticsNetwork;
import org.shortpath.domain.model.Route;
import org.shortpath.domain.model.ShippingDetails;
import org.shortpath.infrastructure.repository.RouteNotFoundRepositoryExeption;
import org.shortpath.infrastructure.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default ShippingService interface implementation.
 * 
 * @author Daniel Perestrelo Vicente
 */
@Service
public class DefaultShippingService implements ShippingService {

	/** The underlying route repository implementation. */
	private RouteRepository routeRepository = null;

	/** The underlying shipping rating service implementation. */
	private ShippingRatingService shippingRatingService = null;

	/**
	 * Constructs a new DefaultShippingService object.
	 * 
	 * @param routeRepository
	 *            the underlying route repository implementation.
	 * @param shippingRatingService
	 *            the underlying shipping rating service implementation.
	 * @throws IllegalArgumentExcpetion
	 *             if routeRepository or shippingRatingService is null.
	 */
	@Autowired
	public DefaultShippingService(RouteRepository routeRepository,
			ShippingRatingService shippingRatingService) {
		setRouteRepository(routeRepository);
		setShippingRatingService(shippingRatingService);
	}

	/**
	 * Gets the underlying route repository used by this service.
	 * 
	 * @return the underlying route repository used by this service.
	 */
	public RouteRepository getRouteRepository() {
		return routeRepository;
	}

	/**
	 * Sets the underlying route repository used by this service.
	 * 
	 * @param routeRepository
	 *            the repository to set.
	 * @throws IllegalArgumentException
	 *             if routeRepository is null.
	 */
	private void setRouteRepository(RouteRepository routeRepository) {
		if (routeRepository == null) {
			throw new IllegalArgumentException("routeRepository is null");
		}
		this.routeRepository = routeRepository;
	}

	/**
	 * Gets the underlying shipping rating service implementation.
	 * 
	 * @return the underlying shipping rating service implementation.
	 */
	public ShippingRatingService getShippingRatingService() {
		return shippingRatingService;
	}

	/**
	 * Sets the underlying shipping rating service implementation.
	 * 
	 * @param shippingRatingService
	 *            the rating service to set
	 * @throws IllegalArgumentException
	 *             if shippingRatingService is null.
	 */
	private void setShippingRatingService(
			ShippingRatingService shippingRatingService) {
		if (shippingRatingService == null) {
			throw new IllegalArgumentException("shippingRatingService is null");
		}
		this.shippingRatingService = shippingRatingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.shortpath.application.services.ShippingService#
	 * addLogisticsNetwork(org.shortpath.domain.model.LogisticsNetwork)
	 */
	@Override
	public void addLogisticsNetwork(LogisticsNetwork logisticsNetwork)
			throws ServiceException {
		if (logisticsNetwork == null) {
			throw new IllegalArgumentException("logisticsNetwork is null");
		}

		try {
			// Saves all given Legs into the route repository
			routeRepository.saveLegs(logisticsNetwork.getLegs());
		} catch (Exception e) {
			// Propagates the exception
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.shortpath.application.services.ShippingService#
	 * getShippingDetails(java.lang.String, java.lang.String, double, double)
	 */
	@Override
	public ShippingDetails getShippingDetails(String origin,
			String destination, double vehicleMileage, double fuelPrice)
			throws NoShippingRouteServiceException, ServiceException {
		if (origin == null) {
			throw new IllegalArgumentException("origin is null");
		}
		if (destination == null) {
			throw new IllegalArgumentException("destination is null");
		}
		if (vehicleMileage <= 0) {
			throw new IllegalArgumentException("vehicleMileage is invalid");
		}
		if (fuelPrice < 0) {
			throw new IllegalArgumentException("fuelPrice is negative");
		}

		try {
			// Gets the shortest shipping route
			final Route route = routeRepository.findShortestRoute(origin,
					destination);

			// Returns the order shipping details
			return new ShippingDetails(shippingRatingService.getShippingRate(
					route.getLength(), vehicleMileage, fuelPrice), route);
		} catch (RouteNotFoundRepositoryExeption e) {
			// Propagates the exception
			throw new NoShippingRouteServiceException(e.getMessage(), e);
		} catch (Exception e) {
			// Propagates the exception
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
