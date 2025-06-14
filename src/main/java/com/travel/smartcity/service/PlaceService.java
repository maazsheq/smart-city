package com.travel.smartcity.service;

import com.travel.smartcity.dao.PlaceDAO;
import com.travel.smartcity.model.Place;

import java.util.List;

public class PlaceService {
  private final PlaceDAO placeDAO = new PlaceDAO();

  public List<Place> search(String query) {
    return placeDAO.searchByNameOrType(query);
  }

  public Place getPlaceDetails(int placeId) {
    return placeDAO.findById(placeId);
  }

  /** Create a new place. */
  public boolean addPlace(Place place) {
    return placeDAO.create(place);
  }

  /** Update an existing place. */
  public boolean updatePlace(Place place) {
    return placeDAO.update(place);
  }

  /** Delete a place by its id. */
  public boolean deletePlace(int placeId) {
    return placeDAO.delete(placeId);
  }

  /** Get all places for the admin table. */
  public List<Place> listAllPlaces() {
    return placeDAO.findAll();
  }

  public List<Place> listPlacesByType(String type) {
    return placeDAO.findByType(type);
  }

}