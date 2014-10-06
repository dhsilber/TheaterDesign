package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 6/16/13 Time: 3:34 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.5
 */
public class Mark {
    private static Integer NUMBER = 0;

    public static String Generate() {
        return (++NUMBER).toString();
    }
}
