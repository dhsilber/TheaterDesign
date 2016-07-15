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
public class Draw extends SvgElement {

    private Document document = null;
    private SVGGraphics2D svgGenerator = null;
    private Element root = null;
    private String namespace;

//    private SvgElement svgRoot = null;

    /**
     * Create a Document and an SVGGraphics2D canvas on which to draw.
     */
    public Draw() {
        super( null );

        // Get a DOMImplementation.
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();

        // create an instance of org.w3c.dom.Document.
        namespace = "http://www.w3.org/2000/svg";
        document = domImpl.createDocument( namespace, "svg", null );

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
     * Provide the document to be worked with.
     *
     * @return document
     */
    public Document document() {
        return document;
    }

    /**
     * Provide the root of the document.
     *
     * @return document root
     */
    public Element root() {
        return root;
    }

//    /**
//     * Provide the root of the document.
//     *
//     * @return document root
//     */
//    public SvgElement svgRoot() {
//        return svgRoot;
//    }

    /**
     * Create a new element in this document.
     *
     * @param type Type of element to be created
     * @return created element
     */
    public SvgElement element(String type) {
        return new SvgElement( document.createElementNS( namespace, type ) );
    }

    /**
     * Write the generated SVG to the specified file.
     *
     * @param pathname pathname of file for generated SVG
     */
    public void create( String pathname ) {

//        System.out.println( "In create. Pathname: " + pathname );
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
//            System.err.println( e.toString() );
        }
    }

    /**
     * Extract the root of the generated SVG DOM.
     * <p/>
     * Getting the root element from the SVGGraphics2D object resets SVGGraphics2D, so all of the
     * drawing must be done before this method is invoked and all of the non-drawing modifications
     * to the DOM should be done after this.
     */
    public void establishRoot() {
        root = svgGenerator.getRoot();
//        svgRoot = new SvgElement( root );
        element = root;
    }

    void setScript( String script ) {
        Element scriptElement = document.createElement( "script" );
        scriptElement.appendChild( document.createTextNode( script ) );
        Node first = root.getFirstChild();
        root.insertBefore( scriptElement, first );
    }

    /**
     * Set the document title to the specified string.
     *
     * @param title New document title
     */
    void setDocumentTitle( String title ) {
        Element titleElement = document.createElement( "title" );
        titleElement.appendChild( document.createTextNode( title ) );
        Node node = root.getFirstChild();
        if ( node.getNodeType() == Node.ELEMENT_NODE ) {
            Element firstElement = (Element) node;
            if ( firstElement.getTagName() == "script" ) {
                node = firstElement.getNextSibling();
            }
        }
        root.insertBefore( titleElement, node );
    }

    /**
     * Insert an element just after the first one under the document root.
     *
     * @param element Element to be inserted
     */
    void insertRootChild( SvgElement element ) {
        Node second = root.getFirstChild().getNextSibling();
        root.insertBefore( element.element(), second );
    }

    /**
     * Add an element directly under the document root.
     *
     * @param element Element to be added
     */
    void appendRootChild( SvgElement element ) {
        root.appendChild( element.element() );
    }

    @Override
    public SvgElement group( Draw draw, String className ) {
        SvgElement element = groupExceptAppend(draw, className);

        this.appendRootChild(element);

        return element;
    }


    // TODO Get rid of this.
    /**
     * Add an element directly under the document root.
     *
     * @param element Element to be added
     */
    void appendRootChild( Element element ) {
        root.appendChild( element );
    }

    @Override
    public void appendChild( SvgElement element ) {
        appendRootChild( element );
    }
}
