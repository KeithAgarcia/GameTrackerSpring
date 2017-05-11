package com.theironyard.charlotte;

public class SubClass extends SuperClass{

    // control + o
    // will show you the methods you can override

    @Override
    public int getNb() {
        // calling super.getNb(); will call the
        // super class's getNb() function. You don't
        // have to use it if you don't want to.
        return super.getNb();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}