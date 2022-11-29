package com.Biztonsagok.CAFFShop.narcano.jni;

public class CaffParser {

    static {
        System.loadLibrary("native");
    }

    public native CAFF parse(String filename);
}
