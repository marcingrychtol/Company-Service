package pl.mdj.rejestrbiurowy.model.comparators;

import pl.mdj.rejestrbiurowy.model.dto.TripDto;

import java.util.Comparator;

public class TripComparatorImpl implements TripComparator, Comparator<TripDto> {
    @Override
    public int compare(TripDto o1, TripDto o2) {
        return o2.getStartingDate().compareTo(o1.getStartingDate());
    }
}
