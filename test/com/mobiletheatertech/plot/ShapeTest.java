package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 12/6/13 Time: 3:40 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.21
 */
public class ShapeTest {

//
//    Polygons drawn when a Proscenium is defined should have a location that makes sense.
//    E.G. The origin of the shape should be relative to the origin of the Proscenium.
//
//    Circles are already properly positioned.

    String square = "1 1 1 8 8 8 8 1"; // square
    String polygon = "1 1 21 1 21 34 12 34 12 54 1 54";

//    string empty="";
//    String badShape=""

    Double xRelative = 76.7;
    Double yRelative = 93.6;
    String polygonRelative = "-12 17 -8 20 8 20 12 17 14 6 14 -6 12 -17 8 -20 -8 -20 -12 -17 -14 -6 -14 6 ";
    String pathRelative = "M 64.7 110.6 68.7 113.6 84.7 113.6 88.7 110.6 90.7 99.6 90.7 87.6 88.7 76.6 84.7 73.6 68.7 73.6 64.7 76.6 62.7 87.6 62.7 99.6 Z";
    String pathRelativeProscenium = "M 298.0 371.0 302.0 368.0 318.0 368.0 322.0 371.0 324.0 382.0 324.0 394.0 322.0 405.0 318.0 408.0 302.0 408.0 298.0 405.0 296.0 394.0 296.0 382.0 Z";

    Double x = 10.0;
    Double y = 12.0;


    Element prosceniumElement;
    Double prosceniumWidth = 450.0;
    Double prosceniumDepth = 12.0;
    Double prosceniumHeight = 180.0;
    Double prosceniumX = 300.0;
    Double prosceniumY = 400.0;
    Double prosceniumZ = 48.0;

    Element venueElement;
    String venueRoom = "Room description";
    Double venueWidth = 700.0;
    Double venueDepth = 1200.0;
    Double venueHeight = 300.0;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Venue.Reset();
        Proscenium.Reset();
        TestResets.ElementalListerReset();

        prosceniumElement = new IIOMetadataNode( Proscenium.Tag() );
        prosceniumElement.setAttribute( "width", prosceniumWidth.toString() );
        prosceniumElement.setAttribute( "depth", prosceniumDepth.toString() );
        prosceniumElement.setAttribute( "height", prosceniumHeight.toString() );
        prosceniumElement.setAttribute( "x", prosceniumX.toString() );
        prosceniumElement.setAttribute( "y", prosceniumY.toString() );
        prosceniumElement.setAttribute( "z", prosceniumZ.toString() );

        venueElement = new IIOMetadataNode( Venue.Tag() );
        venueElement.setAttribute( "room", venueRoom );
        venueElement.setAttribute( "width", venueWidth.toString() );
        venueElement.setAttribute( "depth", venueDepth.toString() );
        venueElement.setAttribute( "height", venueHeight.toString() );
        venueElement.appendChild( prosceniumElement );
    }

    @Test
    public void constantTag() {
        assertEquals( Shape.Tag, "shape" );
    }

    @Test
    public void isA() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", square );
        Shape instance = new Shape( element );

        assert Elemental.class.isInstance( instance );
        assertFalse( ElementalLister.class.isInstance( instance ) );
        assertFalse( Verifier.class.isInstance( instance ) );
        assertFalse( Layerer.class.isInstance( instance ) );
        assertFalse( MinderDom.class.isInstance( instance ) );
        assertFalse( Populate.class.isInstance( instance ) );

        assertFalse( Legendable.class.isInstance( instance ) );
    }

    @Test
    public void polygonCreatesSquareVertexList() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", square );

        Shape shape = new Shape( element );

        Field xPointsField = TestHelpers.accessField( shape, "xPoints" );
        int[] pointsX = (int[]) xPointsField.get( shape );
        assertEquals( pointsX[0], 1 );
        assertEquals( pointsX[1], 1 );
        assertEquals( pointsX[2], 8 );
        assertEquals( pointsX[3], 8 );

        Field yPointsField = TestHelpers.accessField( shape, "yPoints" );
        int[] pointsY = (int[]) yPointsField.get( shape );
        assertEquals( pointsY[0], 1 );
        assertEquals( pointsY[1], 8 );
        assertEquals( pointsY[2], 8 );
        assertEquals( pointsY[3], 1 );
    }

    @Test
    public void polygonCreatesPolygonVertexList() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygon );
        
        Shape shape = new Shape( element );

        Field xPointsField = TestHelpers.accessField( shape, "xPoints" );
        int[] pointsX = (int[]) xPointsField.get( shape );
        assertEquals( pointsX[0], 1 );
        assertEquals( pointsX[1], 21 );
        assertEquals( pointsX[2], 21 );
        assertEquals( pointsX[3], 12 );
        assertEquals( pointsX[4], 12 );
        assertEquals( pointsX[5], 1 );

        Field yPointsField = TestHelpers.accessField( shape, "yPoints" );
        int[] pointsY = (int[]) yPointsField.get( shape );
        assertEquals( pointsY[0], 1 );
        assertEquals( pointsY[1], 1 );
        assertEquals( pointsY[2], 34 );
        assertEquals( pointsY[3], 34 );
        assertEquals( pointsY[4], 54 );
        assertEquals( pointsY[5], 54 );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Empty Shape specification.")
    public void empty() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        new Shape( element );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Invalid Shape specification: too few points in polygon.")
    public void tooFewVertices() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", "1 2 3 4" );
        new Shape( element );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Invalid Shape specification.")
    public void incompleteVertex() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", "1 2 3 4 5 6 7" );
        new Shape( element );
    }

    @Test
    public void fitsSquarePass() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", square );
        Shape shape = new Shape( element );

        assertTrue(shape.fits(2, 2, 3, 3));
    }

    @Test
    public void fitsPolygonPass() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygon );
        Shape shape = new Shape( element );

        assertTrue(shape.fits(2, 2, 3, 3));
    }

    @Test
    public void fitsSquareFail() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", square );
        Shape shape = new Shape( element );

        assertFalse(shape.fits(2, 2, 7, 5));
    }

    @Test
    public void fitsPolygonOutsideFail() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygon );
        Shape shape = new Shape( element );

        assertFalse(shape.fits(2, 37, 5, 18 ));
    }

    @Test
    public void fitsPolygonIntersectsFail() throws Exception {
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygon );
        Shape shape = new Shape( element );

        assertFalse(shape.fits( 32, 2, 5, 5));
    }

    @Test
    public void toSvgPolygon() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygonRelative );
        Shape instance = new Shape( element );

        SvgElement svg = instance.toSvg( draw.element( "parent" ), draw,
                xRelative, yRelative );

        assertEquals( svg.tag(), "path" );
        String path = svg.element.getAttribute( "d" );
        assertEquals( path, pathRelative );
    }

    @Test
    public void toSvgPolygonProscenium() throws Exception {
        new Venue( venueElement );
        Draw draw = new Draw();
        draw.establishRoot();
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "polygon", polygonRelative );
        Shape instance = new Shape( element );
        SvgElement parent = draw.element( "parent" );
        parent.element.setAttribute( "stroke", "orange" );

        SvgElement svg = instance.toSvg( parent, draw, x, y );

        assertEquals( svg.tag(), "path" );
        String path = svg.element.getAttribute( "d" );
        assertEquals( path, pathRelativeProscenium );
        assertEquals( svg.element.getAttribute("stroke"), "orange" );
    }

    @Test
    public void toSvgCircle() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "circle", "7" );
        Shape instance = new Shape( element );
        SvgElement parent = draw.element( "parent" );
        parent.element.setAttribute( "stroke", "purple" );

        SvgElement svg = instance.toSvg( parent, draw,
                xRelative, yRelative );

        assertEquals( svg.tag(), "circle" );
        assertEquals( svg.element.getAttribute( "cx" ), xRelative.toString() );
        assertEquals( svg.element.getAttribute( "cy" ), yRelative.toString() );
        assertEquals( svg.element.getAttribute( "r" ), "7.0" );
        assertEquals( svg.element.getAttribute("stroke"), "purple" );
    }

    @Test
    public void toSvgCircleProscenium() throws Exception {
        new Venue( venueElement );
        Draw draw = new Draw();
        draw.establishRoot();
        Element element = new IIOMetadataNode( Shape.Tag );
        element.setAttribute( "circle", "7" );
        Shape instance = new Shape( element );

        SvgElement svg = instance.toSvg( draw.element( "parent" ), draw,
                xRelative, yRelative );

        assertEquals( svg.tag(), "circle" );
        Double ex = Proscenium.Origin().x() + xRelative;
        Double wy = Proscenium.Origin().y() - yRelative;
        assertEquals( svg.element.getAttribute( "cx" ), ex.toString() );
        assertEquals( svg.element.getAttribute( "cy" ), wy.toString() );
        assertEquals( svg.element.getAttribute( "r" ), "7.0" );
    }
}
