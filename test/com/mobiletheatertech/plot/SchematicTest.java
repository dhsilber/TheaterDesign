package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by dhs on 12/16/14.
 */
public class SchematicTest {

    public class Scheamer implements Schematicable {
        public PagePoint schematicPosition = null;
        public Rectangle2D.Double schematicBox = null;

        @Override
        public PagePoint schematicPosition() {
            return schematicPosition;
        }

        @Override
        public Rectangle2D.Double schematicBox() {
            return schematicBox;
        }

    }
    Double firstX = 100.0;
    Double firstY = 100.0;
    Double height = 12.9;
    Double width = 99.4;
    Double distance = 200.0;
    Double x = 56.7;
    Double y = 82.0;

    @Test
    public void constants() {
        assertEquals( Schematic.FirstX, firstX );
        assertEquals( Schematic.FirstY, firstY );
    }

    @Test
    public void positionFirst() {
        assertEquals( Schematic.Position( width, height), new PagePoint( firstX, firstY ) );
    }

    @Test
    public void positionSecond() {
        Schematic.Position( width, height );
        assertEquals( Schematic.Position( width, height), new PagePoint( firstX + distance, firstY ) );
    }

    @Test
    public void obstructionList() throws Exception {
        ArrayList<Rectangle2D.Double> list = (ArrayList)
                TestHelpers.accessStaticObject(
                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        assertEquals( list.getClass().getName(), "java.util.ArrayList" );
    }

    @Test
    public void obstruction() throws Exception {
        ArrayList<Rectangle2D.Double> list = (ArrayList)
                TestHelpers.accessStaticObject(
                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        assertEquals( list.size(), 0 );

        Schematic.Obstruction( new Scheamer() );
//        Schematic.Obstruction( x, y, width, height );

        assertEquals( list.size(), 1 );
    }

    @Test
    public void findObstruction() throws Exception {
        Scheamer one = new Scheamer();
        Scheamer two = new Scheamer();
        Scheamer three = new Scheamer();
        one.schematicBox = new Rectangle2D.Double( 1.0, 1.0, 3.0, 3.0 );
        two.schematicBox = new Rectangle2D.Double( 5.0, 1.0, 3.0, 3.0 );
        three.schematicBox = new Rectangle2D.Double( 1.0, 5.0, 3.0, 3.0 );
        ArrayList<Schematicable> list = (ArrayList)
                TestHelpers.accessStaticObject(
                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        list.add( one );
        list.add( two );
        list.add( three );
        Line2D.Double line = new Line2D.Double( 0.5, 7.0, 1.5, 7.0 );

        ArrayList<Schematicable> found = Schematic.FindObstruction( line );

        assertEquals( found.size(), 1 );
        assert found.contains( three );
    }

    @Test
    public void findObstructionNone() throws Exception {
        Scheamer one = new Scheamer();
        Scheamer two = new Scheamer();
        Scheamer three = new Scheamer();
        one.schematicBox = new Rectangle2D.Double( 1.0, 1.0, 3.0, 3.0 );
        two.schematicBox = new Rectangle2D.Double( 5.0, 1.0, 3.0, 3.0 );
        three.schematicBox = new Rectangle2D.Double( 1.0, 5.0, 3.0, 3.0 );
        ArrayList<Schematicable> list = (ArrayList)
                TestHelpers.accessStaticObject(
                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        list.add( one );
        list.add( two );
        list.add( three );
        Line2D.Double line = new Line2D.Double( 0.5, 4.5, 12.5, 4.5 );

        ArrayList<Schematicable> found = Schematic.FindObstruction( line );

        assertEquals(found.size(), 0);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        ArrayList<Rectangle2D.Double> list = (ArrayList)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        list.clear();

        Schematic.Count = 0;
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
