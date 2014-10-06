package com.mobiletheatertech.plot;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:20 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.7
 */
public class LegendTest {

    private int calledBack = 0;
    private Element venueElement;

    class testLegendable implements Legendable {
        @Override
        public PagePoint domLegendItem( Draw draw, PagePoint start ) {
            calledBack++;
            return new PagePoint( 12, 13 );
        }
    }

    @Test
    public void hasList() throws Exception {
        Object thingy = TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend",
                                                        "LEGENDLIST" );
        assertNotNull( thingy );
        assert ArrayList.class.isInstance( thingy );
    }

    @Test
    public void registerCallback() throws Exception {
        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1, 2, LegendOrder.Show );

        ArrayList<Legendable> thingy = (ArrayList<Legendable>) TestHelpers.accessStaticObject(
                "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertNotNull( thingy );
        assertEquals( thingy.size(), 1 );
    }

    @Test
    public void invokeCallback() throws Exception {
        new Venue( venueElement );

        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1, 2, LegendOrder.Show );
        Draw draw = new Draw();
        draw.getRoot();

        Legend.Startup( draw );
//        TestHelpers.setStaticObject(
//                "com.mobiletheatertech.plot.Legend", "INITIAL", new PagePoint( 1, 2 ) );

        Legend.Callback();

        assertEquals( calledBack, 1 );
    }

    @Test
    public void widest() {
        testLegendable legendableObject = new testLegendable();
        Integer wildlyLarge = 10240;
        Legend.Register( legendableObject, wildlyLarge, 7, LegendOrder.Show );

        assertEquals( Legend.Widest(), wildlyLarge );
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.LegendReset();
        calledBack = 0;

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
    }

}
