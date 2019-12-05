// ICallService.aidl
package com.example.myapplication;

// Declare any non-default types here with import statements

interface ICallService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    String getMessage(String name);
    int getResult(int a, int b);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

}