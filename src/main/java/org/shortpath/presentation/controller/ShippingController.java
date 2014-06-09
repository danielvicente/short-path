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
package org.shortpath.presentation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.shortpath.application.services.NoShippingRouteServiceException;
import org.shortpath.application.services.ServiceException;
import org.shortpath.application.services.ShippingService;
import org.shortpath.domain.model.LogisticsNetwork;
import org.shortpath.domain.model.ShippingDetails;
import org.shortpath.presentation.converter.LogisticsNetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Shipping controller.
 * 
 * @author Daniel Perestrelo Vicente
 */
@Controller
public class ShippingController {

	/** Logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ShippingController.class);

	/** Shipping service. */
	@Autowired
	private ShippingService shippingService = null;

	/** Logistics network converter. */
	@Autowired
	private LogisticsNetworkConverter logisticsNetworkConverter = null;

	/**
	 * Adds a new logistics network used for shipping route selection.
	 * 
	 * @param name
	 *            network name.
	 * @param legs
	 *            the shipping legs contained in this network.
	 * @throws ServiceException
	 *             if a service access error occurs.
	 */
	@RequestMapping(value = "/putLogisticsNetwork/{name}", method = RequestMethod.POST, headers = "content-type=text/plain")
	@ResponseStatus(HttpStatus.OK)
	public void addLogisticsNetwork(@PathVariable String name,
			@RequestBody String legs) throws ServiceException {
		// Adds a new logistics network used for shipping route selection
		shippingService.addLogisticsNetwork(new LogisticsNetwork(name,
				logisticsNetworkConverter.parseLegs(legs)));
	}

	/**
	 * Gets an order shipping details.
	 * 
	 * @param origin
	 *            the origin of shipment.
	 * @param destination
	 *            the destination of shipment.
	 * @param vehicleMileage
	 *            vehicle mileage (in Kilometers per liter or KMPL).
	 * @param fuelPrice
	 *            fuel price per liter.
	 * @return the order shipping details.
	 * @throws ServiceException
	 *             if a service access error occurs.
	 */
	@RequestMapping(value = "/findShippingDetails/{origin}/{destination}", method = RequestMethod.GET)
	public @ResponseBody
	ShippingDetails getShippingDetails(@PathVariable String origin,
			@PathVariable String destination,
			@RequestParam double vehicleMileage, @RequestParam double fuelPrice)
			throws NoShippingRouteServiceException, ServiceException {
		// Gets an order shipping details
		return shippingService.getShippingDetails(origin, destination,
				vehicleMileage, fuelPrice);
	}

	/**
	 * Custom exception handler.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(IllegalArgumentException e,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// Logging
		logger.error(e.getMessage(), e);

		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	}

	/**
	 * Custom exception handler.
	 */
	@ExceptionHandler(NoShippingRouteServiceException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No route found between the origin and destination")
	public void handleNoShippingRouteServiceException(
			NoShippingRouteServiceException e) {
		// Logging
		logger.error(e.getMessage(), e);
	}

	/**
	 * Custom exception handler.
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error has occurred while calculating the shortest path")
	public void handleServiceException(ServiceException e) {
		// Logging
		logger.error(e.getMessage(), e);
	}

}
