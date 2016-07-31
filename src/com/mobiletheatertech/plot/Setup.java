package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 11:40 AM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Collect a set of event setup options.
 * <p/>
 * Each option comes with a name and a tag.
 * <p/>
 * Other plot elements (e.g. 'chairblock's) might reference some particular value of setup tag to
 * determine when they should be displayed. When viewing the generated document, a user might select
 * an active setup state and those plot elements which reference that state's tag would be
 * displayed.
 *
 * @author dhs
 * @since 0.0.18
 */
public class Setup extends Elemental {
    private String name;
    private String tag;

    private static StringBuilder Accumulator = new StringBuilder();

    /**
     * Extract each setup description element from a list of XML nodes.
     *
     * @param list Set of setup nodes
     * @throws AttributeMissingException This ends up copied to each thing that inherits from
     *                                   Elemental. There needs to be a factory somewhere.
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     */

    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException/*, LocationException, ReferenceException*/
    {

        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new Setup( element );
                }
            }
        }
    }

    /**
     * Construct a {@code Setup} option from an XML element.
     *
     * @param element DOM element defining a setup option
     * @throws AttributeMissingException if any attribute is missing
     */
    public Setup( Element element )
            throws AttributeMissingException, InvalidXMLException {
        super(element);

        name = getStringAttribute( "name" );
        tag  = getStringAttribute( "tag" );

        if (Accumulator.length() < 1) {
            Accumulator.append(
                    "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
                            tag + "\" />" +
                            name + "<br />\n" );
        }
        else {
            Accumulator.append(
                    "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" value=\"" +
                            tag + "\" />" +
                            name + "<br />\n" );
        }
    }

    /**
     * Provide a set of HTML radio buttons which allow a user to select from the various setup
     * options.
     * <p/>
     * Note: This is fairly bogus way to deal with this. I expect that the next time I need to
     * generate a set of radio buttons, I will give {@code Write} a conversion routine and make this
     * provide a list of names & tags.
     *
     * @return preformatted HTML radio buttons
     */
    public static String List() {
        return Accumulator.toString();
    }
}
