package com.rentacar.model.comparators;

import org.springframework.stereotype.Component;
import com.rentacar.model.dto.TripDto;

import java.util.Comparator;

@Component
public class TripComparatorImpl implements TripComparator, Comparator<TripDto> {
    @Override
    public int compare(TripDto o1, TripDto o2) {
        return o2.getStartingDate().compareTo(o1.getStartingDate());
    }
}
