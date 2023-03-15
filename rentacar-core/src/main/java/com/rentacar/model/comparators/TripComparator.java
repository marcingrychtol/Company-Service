package com.rentacar.model.comparators;

import com.rentacar.model.dto.TripDto;

public interface TripComparator {
    int compare(TripDto o1, TripDto o2);
}
