package com.kk.api.station.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kk.api.station.model.Station;
import com.kk.api.station.service.StationService;

/**
 * 
 * @author Krishna Kumar
 *
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-01-02T02:13:32.583+05:30")

@Controller
public class StationsApiController implements StationsApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(StationsApiController.class);

	private StationService stationService;

	@Autowired
	public StationsApiController(StationService stationService) {
		this.stationService = stationService;
	}

	@Override
	public ResponseEntity<Void> addStation(@Valid @RequestBody final Station station) {
		LOGGER.info("Creating new station");
		HttpHeaders httpHeaders = null;
		final String stationId = this.stationService.addStation(station);

		httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stationId).toUri());

		LOGGER.info("Successfully creating new station with station ID [{}]", stationId);
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<Void> deleteStation(@NotNull @PathVariable("stationId") final UUID stationId,
			@Valid @RequestBody final Station station) {
		LOGGER.info("Deleting existing station of ID [{}]", stationId);
		this.stationService.deleteStation(stationId, station);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<List<Station>> findHdStations() {
		LOGGER.info("Listing all HD enabled stations");
		final List<Station> stations = this.stationService.findHdEnabledStations();
		return ResponseEntity.ok().body(stations);
	}

	@Override
	public ResponseEntity<Station> findStationById(@NotNull @PathVariable("stationId") final UUID stationId) {
		LOGGER.info("Getting details of a station by station ID [{}]", stationId);
		final Station returnObject = this.stationService.findByStationId(stationId);
		return ResponseEntity.ok().body(returnObject);
	}

	@Override
	public ResponseEntity<Station> findStationByName(
			@NotNull @Valid @RequestParam(value = "name", required = true) final String name) {
		LOGGER.info("Getting details of a station by name [{}]", name);
		final Station returnObject = this.stationService.findByStationName(name);
		return ResponseEntity.ok().body(returnObject);
	}

	@Override
	public ResponseEntity<List<Station>> listStation() {
		LOGGER.info("Listing all stations");
		final List<Station> stations = this.stationService.listAllStations();
		return ResponseEntity.ok().body(stations);
	}

	@Override
	public ResponseEntity<Station> updateStation(@PathVariable("stationId") final UUID stationId,
			@Valid @RequestBody final Station station) {
		LOGGER.info("Updating existing station of ID [{}]", stationId);
		final Station returnObject = this.stationService.updateStation(stationId, station);
		return ResponseEntity.ok().body(returnObject);
	}

}
