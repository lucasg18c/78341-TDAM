package com.slavik.tdam.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil<T> {

    public List<T> emptyArray(int elements) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < elements; i++) {
            list.add(null);
        }

        return list;
    }
}
