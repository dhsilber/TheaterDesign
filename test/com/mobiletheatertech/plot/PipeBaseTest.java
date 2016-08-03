package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Created by dhs on 7/15/15.
 */
public class PipeBaseTest {


/**
 * Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:01 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.5
 */
    Element baseElement = null;
    Pipe pipe = null;
    Element prosceniumElement = null;

    String id = "Truss ID";
    Double x = 12.0;
    Double y = 32.2;
    Double z = -7.0;

    Double prosceniumX = 100.0;
    Double prosceniumY = 200.0;
    Double prosceniumZ = 15.0;

    @Test
    public void isA() throws Exception {
        PipeBase instance = new PipeBase(baseElement);

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assertTrue( Legendable.class.isInstance( instance ) );
    }

    @Test
    public void constantTag() {
        assertEquals( PipeBase$.MODULE$.Tag(), "pipebase" );
    }

    @Test
    public void constantColor() {
        assertEquals( PipeBase$.MODULE$.Color(), "blue" );
    }

    @Test
    public void constantLegendHeight() {
        assertEquals( PipeBase$.MODULE$.LegendHeight(), 2.0 );
    }

    @Test
    public void globalVariableLegendCount() {
        assertEquals( PipeBase$.MODULE$.LegendCount(), 0 );
    }

    @Test
    public void globalVariableLegendRegistered() {
        assertEquals( PipeBase$.MODULE$.LegendRegistered(), false );
    }

    @Test
    public void storesAttributes() throws Exception {
        PipeBase base = new PipeBase(baseElement);

        assertEquals( TestHelpers.accessDouble(base, "x"), x );
        assertEquals( TestHelpers.accessDouble(base, "y"), y );
        assertEquals( TestHelpers.accessDouble(base, "z"), 0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        baseElement.setAttribute( "z", z.toString() );
        PipeBase base = new PipeBase(baseElement);

        assertEquals( TestHelpers.accessDouble(base, "x"), x );
        assertEquals( TestHelpers.accessDouble(base, "y"), y );
        assertEquals( TestHelpers.accessDouble(base, "z"), z );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        PipeBase base = new PipeBase( baseElement );

        assertNull( TestHelpers.accessString( base, "id" ) );
    }

    @Test
    public void marksProcessed() throws Exception {
        String emptyMark = baseElement.getAttribute( "processedMark" );
        assertEquals( emptyMark, "", "Should be unset" );

        PipeBase base = new PipeBase( baseElement );

        String baseMark = TestHelpers.accessString( base, "processedMark" );
        String elementMark = baseElement.getAttribute( "processedMark" );
        assertNotNull( baseMark );
        assertNotEquals( baseMark, "", "Should be set in TrussBase object" );
        assertNotEquals( elementMark, "", "Should be set in Element" );
        assertEquals( baseMark, elementMark, "should match" );
    }

    @Test
    public void findNull() throws Exception {
        new PipeBase( baseElement );

        PipeBase found = PipeBase.Find( null );

        assertNull(found);
    }

    @Test
    public void findsMarked() throws Exception {
        PipeBase instance = new PipeBase(baseElement);

        PipeBase found = PipeBase.Find( baseElement.getAttribute( "processedMark" ) );

        assertSame( found, instance );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new PipeBase( baseElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "PipeBase instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        baseElement.removeAttribute("x");
        new PipeBase( baseElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "PipeBase instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        baseElement.removeAttribute("y");
        new PipeBase(baseElement);
    }

    @Test
    public void x() throws Exception {
        PipeBase base = new PipeBase( baseElement );

        assertEquals( base.x(), x );
    }

    @Test
    public void y() throws Exception {
        PipeBase base = new PipeBase( baseElement );

        assertEquals( base.y(), y );
    }

    @Test
    public void z() throws Exception {
        baseElement.setAttribute( "z", z.toString() );
        PipeBase base = new PipeBase( baseElement );

        assertEquals( base.z(), z );
    }

    @Test
    public void verify() throws Exception {
        baseElement.setAttribute( "z", z.toString() );
        PipeBase instance = new PipeBase( baseElement );
        instance.verify();

        assertEquals( TestHelpers.accessObject(instance, "drawPlace"),
                new Point( x, y, z ) );
    }

    @Test
    public void verifyProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        baseElement.setAttribute( "z", z.toString() );
        PipeBase instance = new PipeBase( baseElement );
        instance.verify();

        assertEquals( TestHelpers.accessObject(instance, "drawPlace"),
                new Point( prosceniumX + x, prosceniumY - y, prosceniumZ + z ) );
    }



//    @Test
//    public void locate() throws Exception {
//        TrussBase base = new TrussBase(baseElement);
//
//        Point location = base.locate();
//
//        assertEquals( location.x(), (Integer) 12 );
//        assertEquals( location.y(), (Integer) 32 );
//        assertEquals( location.z(), 0 );
//    }


    @Test
    public void domPlan() throws Exception {
        PipeBase base = new PipeBase( baseElement );

        Draw draw = new Draw();
        draw.establishRoot();
        base.verify(); // No actual code in there.

        NodeList preGroupList = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroupList.getLength(), 1 );
        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        base.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), PipeBase$.MODULE$.Tag() );

        NodeList circleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 1 );
        Node node = circleList.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "cx" ), x.toString() );
        assertEquals( element.getAttribute( "cy" ), y.toString() );
        assertEquals( element.getAttribute( "r" ), "18.0" );

        assertEquals( element.getAttribute( "fill" ), "none" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.5" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );


//        NodeList textList = draw.root().getElementsByTagName( "text" );
//        assertEquals( textList.getLength(), 1 );
//        Node textNode = textList.item( 0 );
//        assertEquals( textNode.getNodeType(), Node.ELEMENT_NODE );
//        Element textElement = (Element) textNode;
////        assertEquals( textElement.getAttribute( "fill" ), defaultColor );

    }

    @Test
    public void domPlanProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        PipeBase base = new PipeBase( baseElement );

        Draw draw = new Draw();
        draw.establishRoot();
        base.verify(); // No actual code in there.

        NodeList preGroupList = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroupList.getLength(), 1 );
        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        base.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.getAttribute( "class" ), PipeBase.LAYERTAG );

        NodeList circleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 1 );
        Node node = circleList.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals(element.getAttribute("r"), "18.0");
        Double xDrawn = prosceniumX + x;
        assertEquals( element.getAttribute( "cx" ), xDrawn.toString() );
        Double yDrawn = prosceniumY - y;
        assertEquals( element.getAttribute( "cy" ), yDrawn.toString() );

        assertEquals( element.getAttribute( "fill" ), "none" );
//        assertEquals( element.getAttribute( "stroke" ), defaultColor );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.5" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );


//        NodeList textList = draw.root().getElementsByTagName( "text" );
//        assertEquals( textList.getLength(), 1 );
//        Node textNode = textList.item( 0 );
//        assertEquals( textNode.getNodeType(), Node.ELEMENT_NODE );
//        Element textElement = (Element) textNode;
////        assertEquals( textElement.getAttribute( "fill" ), defaultColor );

    }

    @Test
    public void legendRegistered() throws Exception {
        assertEquals(PipeBase$.MODULE$.LegendRegistered(), false );

        new PipeBase( baseElement );

        TreeMap<Integer, Legendable> legendList =
                (TreeMap<Integer, Legendable>)
                        TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
        Integer order = legendList.lastKey();
        assert( order >= LegendOrder.Structure.initial() );
        assert( order < LegendOrder.Luminaire.initial() );

        assertEquals(PipeBase$.MODULE$.LegendRegistered(), true );
    }

    @Test
    public void legendRegisteredOnce() throws Exception {
        new PipeBase( baseElement );
        baseElement.setAttribute( "id", "differentPipeBase");
        new PipeBase( baseElement );

        TreeMap<Integer, Legendable> legendList = (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
    }

    @Test
    public void legendCountIncrements() throws Exception {
        new PipeBase( baseElement );
        assertEquals( PipeBase$.MODULE$.LegendCount(), 1 );
        baseElement.setAttribute( "id", "differentPipeBase");
        new PipeBase( baseElement );
        assertEquals( PipeBase$.MODULE$.LegendCount(), 2 );
    }

    @Test
    public void domLegendItem() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        PipeBase pipebase = new PipeBase( baseElement );

        PagePoint startPoint = new PagePoint( 20.0, 10.0 );

        NodeList preGroup = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroup.getLength(), 1 );

        PagePoint endPoint = pipebase.domLegendItem( draw, startPoint );

////        NodeList group = draw.root().getElementsByTagName( "g" );
////        assertEquals( group.getLength(), 1 );
////        Node groupNod = group.item(0);
////        Element groupElem = (Element) groupNod;

        NodeList groupList = draw.root().getElementsByTagName( "g" );
        // item 0 exists before domLegendItem() adds any content.
        Node groupNode = groupList.item(1);
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), PipeBase$.MODULE$.Tag() );
        assertEquals( groupElement.getAttribute( "transform" ),
                "translate(" + startPoint.x() + "," + startPoint.y() + ")" );

        assertEquals( groupList.getLength(), 2 );

        NodeList childList = groupElement.getChildNodes();

        Node outerCircleNode = childList.item( 0 );
        assertEquals( outerCircleNode.getNodeType(), Node.ELEMENT_NODE );
        Element outerCircleElement = (Element) outerCircleNode;
        assertEquals( outerCircleElement.getAttribute( "cx" ), "5.0" );
        assertEquals( outerCircleElement.getAttribute( "cy" ), "2.0" );
        assertEquals( outerCircleElement.getAttribute( "r" ), "12.0" );
        assertEquals( outerCircleElement.getAttribute( "stroke" ), PipeBase$.MODULE$.Color() );

        Node innerCircleNode = childList.item( 1 );
        assertEquals( innerCircleNode.getNodeType(), Node.ELEMENT_NODE );
        Element innerCircleElement = (Element) innerCircleNode;
        assertEquals( innerCircleElement.getAttribute( "cx" ), "5.0" );
        assertEquals( innerCircleElement.getAttribute( "cy" ), "2.0" );
        assertEquals( innerCircleElement.getAttribute( "r" ), "2.0" );
        assertEquals( innerCircleElement.getAttribute( "stroke" ), PipeBase$.MODULE$.Color() );

        Node descriptionNode = childList.item( 2 );
        assertEquals( descriptionNode.getNodeType(), Node.ELEMENT_NODE );
        Element descriptionElement = (Element) descriptionNode;
        Double x = Legend.TEXTOFFSET;
        Double y = 8.0;
        assertEquals( descriptionElement.getAttribute("x"), x.toString() );
        assertEquals( descriptionElement.getAttribute("y"), y.toString() );
        assertEquals( descriptionElement.getAttribute("fill"), Legend.TEXTCOLOR );

        assertEquals( descriptionElement.getTextContent(), PipeBase$.MODULE$.Tag() );

        Node quantityNode = childList.item( 3 );
        assertEquals( quantityNode.getNodeType(), Node.ELEMENT_NODE );
        Element quantityElement = (Element) quantityNode;
        x = Legend.QUANTITYOFFSET;
        assertEquals(quantityElement.getAttribute("x"), x.toString() );
        assertEquals(quantityElement.getAttribute("y"), y.toString() );
        assertEquals(quantityElement.getAttribute("fill"), Legend.TEXTCOLOR );

        assertEquals( quantityElement.getTextContent(), "1" );

        assertEquals( childList.getLength(), 4 );

        assertEquals( endPoint,
                new PagePoint( startPoint.x(), startPoint.y() + PipeBase$.MODULE$.LegendHeight() ) );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.YokeableReset();
        TestResets.ElementalListerReset();
        TestResets.ProsceniumReset();
        TestResets.LegendReset();
        PipeBase$.MODULE$.Reset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "TrussBase Venue Name" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "750" );
        venueElement.setAttribute( "depth", "900" );
        venueElement.setAttribute( "height", "240" );
//        Venue venue =
                new Venue( venueElement );
//        venue.getClass();
//        Venue.Height();

        prosceniumElement = new IIOMetadataNode( "proscenium" );
        prosceniumElement.setAttribute( "x", prosceniumX.toString() );
        prosceniumElement.setAttribute( "y", prosceniumY.toString() );
        prosceniumElement.setAttribute( "z", prosceniumZ.toString() );
        prosceniumElement.setAttribute( "width", "200" );
        prosceniumElement.setAttribute( "depth", "23" );
        prosceniumElement.setAttribute( "height", "144" );

//        Element otherBase = new IIOMetadataNode( "base" );
//        otherBase.setAttribute( "ref", "jane" );
//        otherBase.setAttribute( "distance", "200" );

        baseElement = new IIOMetadataNode( "pipebase" );
        baseElement.setAttribute("x", x.toString());
        baseElement.setAttribute("y", y.toString());

//        Element pipeElement = new IIOMetadataNode( "pipe" );
//        pipeElement.setAttribute("id", id);
//        pipeElement.setAttribute( "size", "12" );
//        pipeElement.setAttribute( "length", "320" );
//        pipeElement.appendChild(baseElement);
//        pipe = new Pipe( pipeElement );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}