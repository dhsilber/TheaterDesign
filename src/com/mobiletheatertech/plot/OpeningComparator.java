package com.mobiletheatertech.plot;

import java.util.Comparator;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/30/13 Time: 9:20 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Comparator for use with {@code Opening}s.
 *
 * @author dhs
 * @since 0.0.12
 */
public class OpeningComparator implements Comparator<Opening> {

    public int compare( Opening a, Opening b ) {
        if (a.start() < b.start()) {
            return -1;
        }
        if (a.start() > b.start()) {
            return 1;
        }
        else {
            return 0;
        }
    }

}
