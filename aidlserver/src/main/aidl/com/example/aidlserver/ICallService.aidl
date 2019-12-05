// CallService.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface ICallService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getMessage(String getMessage);
    int getResult(int a, int b);
}
