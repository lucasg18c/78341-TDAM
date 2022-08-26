package com.slavik.tdam.util;

public interface Response<T> {

    void onResponse(T data, boolean isSuccess);
}
