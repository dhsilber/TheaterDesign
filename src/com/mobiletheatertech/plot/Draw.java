package com.mobiletheatertech.plot;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.awt.*;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Manage the Graphics2D canvas that is drawn to. An SVG file may be generated.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Draw {

    private Document document = null;
    private SVGGraphics2D svgGenerator = null;
    private Element root = null;

    /**
     * Create a Document and an SVGGraphics2D canvas on which to draw.
     */
    public Draw() {

        // Get a DOMImplementation.
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();

        // create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        document = domImpl.createDocument( svgNS, "svg", null );

        // create an instance of the SVG Generator.
        svgGenerator = new SVGGraphics2D( document );
    }

    /**
     * Provide the canvas to be drawn on.
     *
     * @return canvas
     */
    public Graphics2D canvas() {
        return svgGenerator;
    }

    /**
     * Write the generated SVG to the specified file.
     *
     * @param pathname pathname of file for generated SVG
     */
    public void create( String pathname ) {

        // Finally, stream out SVG to the specified pathname
//        boolean useCSS = true; // we want to use CSS style attributes
        try {
            Writer out = new FileWriter( pathname );
            
            /*
             * Per http://osdir.com/ml/text.xml.batik.user/2003-01/msg00121.html
             * getting the root element from the SVGGraphics2D object resets
             * SVGGraphics2D, so we have to make sure we generate the stream
             * from the root that we grabbed.
             */
            svgGenerator.stream( root, out );//, useCSS );
        }
        catch ( Exception e ) {
            System.err.println( e.toString() );
        }
    }

    /**
     * Extract the root of the generated SVG DOM.
     * <p/>
     * Getting the root element from the SVGGraphics2D object resets SVGGraphics2D, so all of the
     * drawing must be done before this method is invoked and all of the non-drawing modifications
     * to the DOM should be done after this.
     */
    public void getRoot() {
        root = svgGenerator.getRoot();
    }

    /**
     * Set the document title to the specified string.
     *
     * @param title New document title.
     */
    void setDocumentTitle( String title ) {
        Node first = root.getFirstChild();
        Element titleElement = document.createElement( "title" );
        titleElement.appendChild( document.createTextNode( title ) );
        root.insertBefore( titleElement, first );
    }

}
