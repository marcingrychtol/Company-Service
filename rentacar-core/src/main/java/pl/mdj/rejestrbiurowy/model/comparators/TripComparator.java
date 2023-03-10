package pl.mdj.rejestrbiurowy.model.comparators;

import pl.mdj.rejestrbiurowy.model.dto.TripDto;

public interface TripComparator {
    int compare(TripDto o1, TripDto o2);
}
