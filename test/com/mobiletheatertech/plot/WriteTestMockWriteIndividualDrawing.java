package com.mobiletheatertech.plot;

import org.testng.annotations.Test;

/**
 * Created by dhs on 12/15/14.
 */
public class WriteTestMockWriteIndividualDrawing {
    class WriteMockedIndividualDrawing extends Write {
        @Override
        Draw writeIndividualDrawing( Drawing drawing )
                throws InvalidXMLException, MountingException, ReferenceException {
            return null;
        }
    }

    @Test
    public void writeDrawingsSetsViewPlan() {

    }

    @Test
    public void writeDrawingsSetsViewSchematic() {

    }
}
