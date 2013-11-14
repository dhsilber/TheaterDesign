package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;

import static org.testng.Assert.assertEquals;

/**
 * Encapsulations of various useful test code.
 *
 * @author dhs
 * @since 0.0.2
 */
public class TestHelpers {

    public TestHelpers() {
    }

    protected static Field accessField( Object thingy, String name ) throws Exception {
        Field field;
        try {
            field = thingy.getClass().getDeclaredField( name );
        }
        catch ( NoSuchFieldException a ) {
            field = thingy.getClass().getField( name );
        }

        field.setAccessible( true );

        return field;
    }

    public static Object accessStaticObject( String classname, String name ) throws Exception {
        Field field = Class.forName( classname ).getDeclaredField( name );
        field.setAccessible( true );
        return field.get( Class.forName( classname ) );
    }

/*
    public static void setStaticObject( String classname, String name, Object value )
            throws Exception
    {
        Field field = Class.forName( classname ).getDeclaredField( name );
        field.setAccessible( true );
//        Object thing = field.get( Class.forName( classname ) );
        field.set( null, value );
    }
*/

    public static Object accessObject( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return field.get( thingy );
    }

    public static Integer accessInteger( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (Integer) field.get( thingy );
    }

    public static String accessString( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (String) field.get( thingy );
    }

    public static Point accessPoint( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (Point) field.get( thingy );
    }

    /**
     * Get the text from an element in the DOM with a unique tag
     *
     * @param draw
     * @param title
     * @return
     */
    public static String getDomElementText( Draw draw, String title ) {
        NodeList list = draw.root().getElementsByTagName( title );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        return element.getTextContent();
    }
}
