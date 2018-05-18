package com.fuck.manspace.event;

/**
 * Created by Mou on 2017/6/6.
 */

public interface PickerListener<T> {
    void pickerData(int position, T data);
}
