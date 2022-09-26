package ru.practicum.explorewithme.dto.location.mapper;

import ru.practicum.explorewithme.dto.location.LocationDto;
import ru.practicum.explorewithme.model.Location;

public class LocationMapper {

    public static LocationDto locationToDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

    public static Location dtoToLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLatitude(locationDto.getLat());
        location.setLongitude(locationDto.getLon());
        return location;
    }
}
