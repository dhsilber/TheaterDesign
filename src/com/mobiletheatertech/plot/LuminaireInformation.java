package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 12/15/14.
 */
public class LuminaireInformation extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     */
    public static final String LAYERNAME = "Luminaire Information";

    /**
     * Name of {@code Layer} of {@code Luminare}s.
     */
    public static final String LAYERTAG = "luminaireinformation";


    private Luminaire luminaire;

    // Ths element argument is only there as a placebo to be passed along to Element.
    // LuminaireInformation needs to display as a MinderDom, but doesn't need the element parsing side.
    public LuminaireInformation( Element element, Luminaire luminaire ) throws DataException, InvalidXMLException {
        super( element );

        this.luminaire = luminaire;

        new Layer( LAYERTAG, LAYERNAME, Luminaire.COLOR );
    }

    public Luminaire luminaire() {
        return luminaire;
    }

    @Override
    public void verify() { }

    @Override
    public void dom( Draw draw, View mode ) throws MountingException, ReferenceException {

        Truss truss = null;

        // There is no good place to display extra information in section and front views.
        switch (mode) {
            case PLAN:
                break;

            case TRUSS:
                if( Truss.class.isInstance( luminaire.mount() )) {
                    truss = (Truss) luminaire.mount();
                }
                break;

            case SECTION:
            case FRONT:
                return;
        }


        SvgElement group = svgClassGroup( draw, LAYERTAG );


//        Text textUnit = draw.document().createTextNode(unit);
//        unitText.appendChild(textUnit);
//        System.out.println("Luminaire.dom: added unit stuff.");


//        SvgElement infogroup = svgClassGroup( draw, INFOLAYERTAG );
////        draw.element("g");
//
////        infogroup.setAttribute("class", INFOLAYERTAG);
//        group.appendChild(infogroup);
//        System.out.println("Luminaire.dom: added infogroup.");

//        SvgElement circuitHexagon = draw.element("path");
////        circuitHexagon.setAttribute("transform", transform);
//        circuitHexagon.setAttribute("fill", "none");
//        circuitHexagon.setAttribute("stroke", "black");
//        circuitHexagon.setAttribute("stroke-width", "1");
//        circuitHexagon.setAttribute("width", "18");
//        circuitHexagon.setAttribute("height", "12");

//        SvgElement dimmerRectangle = draw.element("rect");
////        dimmerRectangle.setAttribute("transform", transform);
//        dimmerRectangle.setAttribute("fill", "none");
//        dimmerRectangle.setAttribute("height", "11");

//        SvgElement channelCircle = draw.element("circle");
////        channelCircle.setAttribute("transform", transform);
//        channelCircle.setAttribute("fill", "none");
//        channelCircle.setAttribute("r", "8");

//        SvgElement circuitText = draw.element("text");
////        circuitText.setAttribute("transform", transform);
//        circuitText.setAttribute("fill", "black");
//        circuitText.setAttribute("stroke", "none");
//        circuitText.setAttribute("font-family", "serif");
//        circuitText.setAttribute("font-size", "9");
//        circuitText.setAttribute("text-anchor", "middle");

//        SvgElement dimmerText = draw.element("text");
////        dimmerText.setAttribute("transform", transform);
//        dimmerText.setAttribute("fill", "black");
//        dimmerText.setAttribute("stroke", "none");
//        dimmerText.setAttribute("font-family", "serif");
//        dimmerText.setAttribute("font-size", "9");
//        dimmerText.setAttribute("text-anchor", "middle");

//        SvgElement channelText = draw.element("text");
////        channelText.setAttribute("transform", transform);
//        channelText.setAttribute("fill", "black");
//        channelText.setAttribute("fill", "black");
//        channelText.setAttribute("font-family", "serif");
//        channelText.setAttribute("font-size", "9");
//        channelText.setAttribute("text-anchor", "middle");

//        Text textCircuit = draw.document().createTextNode(circuit);
//        Text textDimmer = draw.document().createTextNode(dimmer);
//        Text textChannel = draw.document().createTextNode(channel);

        boolean farSide = (null != truss && truss.farSide( luminaire.locationValue() ) );

        int direction = 1;
        Double hexagonYOffset = 0.0;
        Double rectangleYOffset = 0.0;
        Double circleYOffset = 0.0;
        Double dimmerTextYOffset = 0.0;
        Double channelTextYOffset = 0.0;
        if (View.TRUSS == mode && farSide ) {
            direction = -1;
            hexagonYOffset = 7.0;
            rectangleYOffset = 12.0;
            dimmerTextYOffset = -4.0;
            circleYOffset = 6.0;
        }

        Double dimmerTextY;
        Double dimmerRectangleY;
        Double channelCircleY;
        Double channelTextY;
        Double dimmerTextX;
        Double circuitTextX=0.0;
        Double circuitTextY=0.0;
        Double dimmerRectangleWidth;
        Integer unitTextX;
        Integer unitTextY;
        Integer colorTextX;
        Integer colorTextY;
        Integer xColorText = 0;
        Integer yColor = 0;
        Integer yDimmerRectangle = 0;
        Integer xDimmerRectangle = 0;
        Integer yDimmerText = 0;
        Integer addressOvalRadiusY;
        Integer xAddressOval;
        Integer yAddressOval;
        Integer addressTextY;
        String circuitPath="";


        Double ex = luminaire.point().x();
        Double wy = luminaire.point().y();
//        Double z = Venue.Height() - luminaire.point().z();

        switch (Venue.Circuiting()) {
            case Venue.ONETOONE:
            case Venue.ONETOMANY:
                dimmerTextY = wy - 20 * direction - hexagonYOffset - dimmerTextYOffset;
                dimmerRectangleY = wy - 28 * direction - hexagonYOffset - rectangleYOffset;
                channelCircleY = wy - 39 * direction - circleYOffset;
                channelTextY = wy - 36 * direction - channelTextYOffset;
                break;
            default:
                // Hexagon for circuit
                Double x = ex - 9;
                Double y = wy - 25 * direction - hexagonYOffset;
//                circuitHexagon.setAttribute("d",
//                        "M " + (x + 1) + " " + (y + 5) +
//                                " L " + (x + 4) + " " + y +
//                                " L " + (x + 14) + " " + y +
//                                " L " + (x + 17) + " " + (y + 5) +
//                                " L " + (x + 14) + " " + (y + 10) +
//                                " L " + (x + 4) + " " + (y + 10) +
//                                " Z");
                circuitPath = "M " + (x + 1) + " " + (y + 5) +
                        " L " + (x + 4) + " " + y +
                        " L " + (x + 14) + " " + y +
                        " L " + (x + 17) + " " + (y + 5) +
                        " L " + (x + 14) + " " + (y + 10) +
                        " L " + (x + 4) + " " + (y + 10) +
                        " Z";
//                circuitHexagon.setAttribute("x", x.toString());
//                circuitHexagon.setAttribute("y", y.toString());
//                infogroup.appendChild(circuitHexagon);

                circuitTextX = ex;
                circuitTextY = wy - 17 * direction;
//                circuitText.setAttribute("x", circuitTextX.toString());
//                circuitText.setAttribute("y", circuitTextY.toString());
//                infogroup.appendChild(circuitText);
//                circuitText.appendChild(textCircuit);

                dimmerTextY = wy - 31 * direction;
                dimmerRectangleY = wy - 39 * direction;
                channelCircleY = wy - 50 * direction;
                channelTextY = wy - 46 * direction;
                break;
        }
//        System.out.println( "Luminaire.dom: after circuiting switch.");

        // Text for dimmer
        dimmerTextX = ex;
//        if (3 < textDimmer.getLength()) {
//            dimmerTextX -= 5;
//        }
//        dimmerText.setAttribute("x", dimmerTextX.toString());
//        dimmerText.setAttribute("y", dimmerTextY.toString());

        // Rectangle for dimmer
//        x = ex - 9;
        dimmerRectangleWidth = 18.0;
//        if (3 < textDimmer.getLength()) {
//            dimmerRectangleWidth = 30;
//            x -= 4;
//        }

//        dimmerRectangle.setAttribute("x", x.toString());
//        dimmerRectangle.setAttribute("y", dimmerRectangleY.toString());
//        dimmerRectangle.setAttribute( "width", dimmerRectangleWidth.toString() );

//        if ( "" != dimmer ) {
//        infogroup.appendChild(dimmerRectangle);

//        infogroup.appendChild(dimmerText);
//        switch (Venue.Circuiting()) {
//            case Venue.ONETOMANY:
//                dimmerText.appendChild(textCircuit);
//                break;
//            case Venue.ONETOONE:
//            default:
//                dimmerText.appendChild(textDimmer);
//                break;
//        }
//        System.out.println( "Luminaire.dom: added dimmer stuff.");
//        }

        // Circle and text for channel number
//        x = point.x();
//        channelCircle.setAttribute("cx", x.toString());
//        channelCircle.setAttribute("cy", channelCircleY.toString());
//        infogroup.appendChild(channelCircle);

        Double channelTextX = ex;
//        channelText.setAttribute("x", channelTextX.toString());
//        channelText.setAttribute("y", channelTextY.toString());
//        infogroup.appendChild(channelText);

//        channelText.appendChild(textChannel);
//        System.out.println("Luminaire.dom: added channel stuff.");


//
//
//        // Color designation to display
//        SvgElement colorText = draw.element("text");
////        colorText.setAttribute("transform", transform);
//        colorTextX = point.x();
//        colorTextY = point.y() + 18;
//        colorText.setAttribute("x", colorTextX.toString());
//        colorText.setAttribute("y", colorTextY.toString());
//        colorText.setAttribute("fill", "black");
//        colorText.setAttribute("stroke", "none");
//        colorText.setAttribute("font-family", "sans-serif");
//        colorText.setAttribute("font-weight", "100");
//        colorText.setAttribute("font-size", "5");
//        colorText.setAttribute("stroke-width", "1px");
//        colorText.setAttribute("text-anchor", "middle");
//        infogroup.appendChild(colorText);
//
//        Text textColor = draw.document().createTextNode(color);
//        colorText.appendChild(textColor);
////        System.out.println("Luminaire.dom: added color stuff.");
//
//        if (View.PLAN == mode ) {

//            SvgElement addressOval= draw.element("ellipse");
//            SvgElement addressText = draw.element("text");
//
//            if( "" != address){
//                addressOval.setAttribute( "ry", "7" );
//                addressOvalRadiusY = dimmerRectangleWidth / 2 + 1;
//                addressOval.setAttribute( "rx", addressOvalRadiusY.toString() );
//                addressOval.setAttribute( "fill", "none" );
//                infogroup.appendChild( addressOval );
//
//                addressText.setAttribute("fill", "black");
//                addressText.setAttribute("stroke", "none");
//                addressText.setAttribute("font-family", "sans-serif");
//                addressText.setAttribute("font-weight", "100");
//                addressText.setAttribute("font-size", "9");
//                addressText.setAttribute("stroke-width", "1px");
//                addressText.setAttribute("text-anchor", "middle");
//                infogroup.appendChild(addressText);
//
//                Text textAddress=draw.document().createTextNode( address );
//                addressText.appendChild( textAddress );
//            }
//
//            switch (info) {
//                case "right":
//                    xColorText = point.x()+definition.width() + dimmerRectangleWidth;
//                    yColor = point.y() - 9;
//                    colorText.setAttribute("x", xColorText.toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 3;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2 + definition.width()  + 8;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 5;
//                    dimmerText.setAttribute("x", xColorText.toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        Integer xAddressOval = xDimmerRectangle + dimmerRectangleWidth * 2;
//                        addressOval.setAttribute( "cx", xAddressOval.toString());
//                        Integer yAddressOval = yDimmerRectangle + 5;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", xAddressOval.toString());
//                        Integer addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "left":
//                    xColorText = point.x() - definition.width() - dimmerRectangleWidth;
//                    yColor = point.y() - 9;
//                    colorText.setAttribute("x", xColorText.toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 4;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2 - definition.width() - 8;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 5;
//                    dimmerText.setAttribute("x", xColorText.toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        xAddressOval = xDimmerRectangle - dimmerRectangleWidth;
//                        addressOval.setAttribute( "cx", xAddressOval.toString());
//                        yAddressOval = yDimmerRectangle + 5;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", xAddressOval.toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "up":
//                     yColor = point.y() - definition.height();
//                    colorText.setAttribute("x", point.x().toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor - 17;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 9;
//                    dimmerText.setAttribute("x", point.x().toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        addressOval.setAttribute( "cx", point.x().toString());
//                        yAddressOval = yDimmerRectangle - 10;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", point.x().toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "down":
//                    yColor = point.y() + definition.height();
//                    colorText.setAttribute("x", point.x().toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 2;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 9;
//                    dimmerText.setAttribute("x", point.x().toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        addressOval.setAttribute( "cx", point.x().toString());
//                        yAddressOval = yDimmerRectangle + 20;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", point.x().toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//            }
//        }

        SvgElement infogroup = svgClassGroup( draw, LAYERTAG);
        group.appendChild(infogroup);

        switch (Venue.Circuiting() ) {
            case Venue.ONETOMANY:
            case Venue.ONETOONE:
                break;
            default:
                SvgElement circuitHexagon = infogroup.path( draw, circuitPath, Luminaire.COLOR );
//                infogroup.appendChild(circuitHexagon);
                SvgElement circuitText =
                        infogroup.text( draw, luminaire.circuit(), circuitTextX, circuitTextY, Luminaire.COLOR );
//                infogroup.appendChild(circuitText);

                break;
        }

        SvgElement dimmerRectangle = infogroup.rectangle(
                draw, ex, dimmerRectangleY, dimmerRectangleWidth, 11.0, Luminaire.COLOR );
//        infogroup.appendChild(dimmerRectangle);
        SvgElement dimmerText;
        switch (Venue.Circuiting()) {
            case Venue.ONETOMANY:
                dimmerText = infogroup.text(
                        draw, luminaire.circuit(), dimmerTextX, dimmerTextY, Luminaire.COLOR );
                break;
            case Venue.ONETOONE:
            default:
                dimmerText = infogroup.text(
                        draw, luminaire.dimmer(), dimmerTextX, dimmerTextY, Luminaire.COLOR );
                break;
        }
//        infogroup.appendChild(dimmerText);

        SvgElement channelCircle = infogroup.circle( draw, ex, channelCircleY, 8.0, Luminaire.COLOR );
//        infogroup.appendChild(channelCircle);
        SvgElement channelText =
                infogroup.text( draw, luminaire.channel(), channelTextX, channelTextY, Luminaire.COLOR );
//        infogroup.appendChild(channelText);

    }
}
