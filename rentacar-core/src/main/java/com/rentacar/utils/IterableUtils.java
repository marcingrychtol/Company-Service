package com.rentacar.utils;

import java.util.List;
import java.util.ArrayList;

public class IterableUtils {

    public static <T> List<T> iterableToList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        iterable.forEach(t -> resultList.add(t));
        return resultList;
    }
}
