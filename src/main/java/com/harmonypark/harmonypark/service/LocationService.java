package com.harmonypark.harmonypark.service;

import com.harmonypark.harmonypark.dto.LiveLocationDto;
import com.harmonypark.harmonypark.exception.UserNotFoundException;

public interface LocationService {
    String extractUserCoordinates();
    LiveLocationDto extractCityAndDate();

    void emergencyAlert() throws UserNotFoundException;
}
