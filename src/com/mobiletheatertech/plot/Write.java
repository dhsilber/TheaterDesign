package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;


/**
 * <code>Write</code> deals with output file issues and relies on {@link Draw Draw} to generate SVG
 * content.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Write {

    private String home = null;

    private String CSS = "\n"
            + ".heading { font-size: 14pt; text-anchor: middle; font-weight: bold; stroke: none }\n"
            + "iframe { display: inline }\n"
            + "@media print {\n"
            + "  .noprint { display: none }"
            + "}\n";


    /**
     * Draw each of the Plot items that have been defined to a SVG file.
     * <p/>
     * The complete pathname is built as {@literal <user's home directory>}{@code
     * /Plot/out/}{@literal <basename>}{@code .xml}
     * <p/>
     * The non-drawing updates to the generated SVG document have to be done after all of the
     * drawing is completed. Because of this, {@code Write} needs to interact with {@link Draw} and
     * {@ link Minder} methods in a very specific order.
     *
     * @param basename
     * @throws MountingException
     * @throws ReferenceException
     */
    public void init( String basename ) throws MountingException, ReferenceException {
        home = System.getProperty("user.home");

        // TODO Is it even possible for this to happen?
//        if (null == home) {
//            // throw exception
//        }

        String pathname = home + "/Dropbox/Plot/out/" + basename;

        writeDirectory( pathname );
        writeFile(pathname, "drawings.html", generateHTMLDrawingList( basename ) );
        writeFile(pathname, "designer.html", generateDesigner() );
        writeFile(pathname, "styles.css", CSS );
        // TODO factor out heading generation for these:
        drawPlan().create( pathname + "/plan.svg" );
        drawSection().create( pathname + "/section.svg" );
        drawFront().create( pathname + "/front.svg" );
        drawTruss().create( pathname + "/truss.svg" );
        writeDrawings( pathname );
        writeSpreadsheet( pathname + "/gear.ods" );
    }

    private void writeDirectory(String basename) /*throws MountingException, ReferenceException*/ {
        File directory = new File(basename);
        /*Boolean dir =*/ directory.mkdir();
//        System.err.println("Directory: " + basename + ". Good? " + dir.toString());
    }

    public String OLDgenerateIndex( String basename ) throws ReferenceException {
        String output = "" +
                "<!DOCTYPE html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\">\n" +
                "<title>Arisia '15 Tech Room Drawings</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Arisia '15 Tech Room Drawings</h1>\n" +
                "<h2>Generated Drawings</h2>\n" +
                "<p>\n" +
                "Here are drawings generated for the hotel resume.\n" +
                "Please review them and let me know what changes need to be made " +
                "and what additional drawings should be created.\n" +
                "</p>\n" +
                "<p>\n" +
                "I can be contacted at " +
                "<a href=\"mailto:theater@davidsilber.name\">theater@davidsilber.name</a> " +
                "or at 240-997-6646.\n" +
                "</p>\n" +
                generateHTMLDrawingList( basename ) +
                "<h2>Designers' View</h2>\n" +
                "<p>\n" +
                "The Designers' View allows users to turn off selected layers " +
                "to focus in on the details that matter to them at the moment.\n" +
                "</p>\n" +
                "<p>\n" +
                "<a href=\"designer.html\">Designers' View</a>\n" +
                "</p>\n" +
                "<p>\n" +
                "Thanks to Peter & Mary O., the javascript for the Designers' View now works in Firefox and Chrome " +
                "as well as Safari and Rekonq.\n" +
                "</p>\n" +
                "<p>\n" +
                "I have noticed that Rekonq does not properly display dashed lines.\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>\n";

        return output;
    }

    private String generateHTMLDrawingList( String basename ) throws ReferenceException {
//        System.out.println();
//        System.out.println( "In writeDrawings." );
        StringBuilder generated = new StringBuilder( "<p>" );

        for (ElementalLister thingy : MinderDom.List() ) {
//            System.out.println( "Found a thing of type: "+ thingy.getClass().getCanonicalName() );
            if ( Drawing.class.isInstance( thingy ) ) {
                Drawing drawing = (Drawing) thingy;
//                System.out.println( "About to write drawing for "+drawing.filename() );
//                writeIndividualDrawing( drawing ).create( pathname + "/" + drawing.filename() + ".svg" );
                generated.append( "<a href=\"" + basename + "/" + drawing.filename + ".svg\">" +
                        Venue.Name() + ": " + drawing.id + "</a><br/>\n" );
            }
        }
//        System.out.println();

        generated.append( "</p>" );

        return generated.toString();
    }

    public String generateDesigner() throws ReferenceException {
//        Integer width = Venue.Width() + Legend.PlanWidth() + SvgElement.OffsetX();
        Double width = Venue.Width() + Legend.PlanWidth() + SvgElement.OffsetX() * 2 + 7;
        width += width / 100 + 5;

        Double height = Venue.Depth();
        height += SvgElement.OffsetY().intValue() * 2 + height / 100;

        String output = "" +
                "<!DOCTYPE html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\">\n" +
                "<title>" + Venue.Name() + "</title>\n" +
                "<script>\n" +
                "function show( victim )\n" +
                "{\n" +
                "  var links = plan.contentDocument ? " +
                "plan.contentDocument.getElementsByClassName( victim ) : " +
                "plan.document.getElementsByClassName( victim );\n" +
                "  for (var i=0; i < links.length; i++) {\n" +
                "      links[i].setAttribute(\"visibility\", \"visible\");\n" +
                "  } \n" +
                "}\n" +
                "function hide( victim )\n" +
                "{\n" +
                "  var links = plan.contentDocument ? " +
                "plan.contentDocument.getElementsByClassName( victim ) : " +
                "plan.document.getElementsByClassName( victim );\n" +
                "  for (var i=0; i < links.length; i++) {\n" +
                "      links[i].setAttribute(\"visibility\", \"hidden\");\n" +
                "  } \n" +
                "}\n" +
//                "function process()\n" +
//                "{\n" +
//                "  if( document.getElementById('process').checked)\n" +
//                "    show( \"chairblock\" );\n" +
//                "  else\n" +
//                "    hide( \"chairblock\" );\n" +
//                "}\n" +
                HTML.SelectFunctions(Layer.List()) +

//                "function selectLayer()\n" +
//                "{\n" +
//                "  if( document.getElementById('" + HangPoint.LAYERTAG + "layer').checked)\n" +
//                "    show( \"" + HangPoint.LAYERTAG + "\" );\n" +
//                "  else\n" +
//                "    hide( \"" + HangPoint.LAYERTAG + "\" );\n" +
//                "}\n" +
//                "function selectLayer()\n" +
//                "{\n" +
//                "  if( document.getElementById('" + Luminaire.LAYERTAG + "layer').checked)\n" +
//                "    show( \"" + Luminaire.LAYERTAG + "\" );\n" +
//                "  else\n" +
//                "    hide( \"" + Luminaire.LAYERTAG + "\" );\n" +
//                "}\n" +
                "function selectsetup()\n" +
                "{\n" +
                "  for(i=0;i<document.configure.setup.length; i++) {\n" +
                "    if( document.configure.setup[i].checked==true) {\n" +
                "      setsetup( document.configure.setup[i].value );\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "function setsetup(tag)\n" +
                "{\n" +
                "  alert( tag );\n" +
                "}\n" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"noprint\" >\n" +
                "<form name=\"configure\" >\n" +
//                Setup.List() +
                HTML.Checkboxes(Layer.List()) +
//                "<input type=\"checkbox\" onclick=\"parent.process();\" name=\"show chairs\"" +
//                " id=\"process\" checked=\"checked\" /> Session Chairs\n" +
                "</form>\n" +
                "</div>\n" +
                "<iframe id=\"plan\" src=\"plan.svg\" height=\"" + height + "\" width=\"" +
//                Venue.Width() + "\" ></iframe>\n" +
//                "<iframe id=\"plan\" src=\"legend.html\" height=\"" + Venue.Depth() + "\" width=\"" +
                width + "\" ></iframe>\n" +
                "</body>\n" +
                "</html>\n";

        return output;
    }

    private Draw drawPlan() throws MountingException, ReferenceException {
        Draw draw = startFile();
        Legend.Startup(draw, View.PLAN,
                Venue.Width() + SvgElement.OffsetX() * 2 + 5,
                Legend.PlanWidth() );
        MinderDom.DomAllPlan(draw);

//        System.out.println( "drawPlan: About to Hack.Dom.");
        Hack.Dom(draw, View.PLAN );

//        System.out.println( "drawPlan: About to Legend.Callback.");
        Legend.Callback();

//        String pathname = basename + "/plan.svg";
//
//        draw.create(pathname);
//        System.out.println( "drawPlan: done.");
        return draw;
    }

    private Draw startFile() throws ReferenceException {
        Draw draw = new Draw();
        draw.establishRoot();

        // Specify the size of the generated SVG so that when it is larger than the display area,
        // scrollbars will be provided.
        Element rootElement = draw.root();

        Double width = Venue.Width() + Legend.PlanWidth() + SvgElement.OffsetX() * 2 + 5;
        width += width / 100 + 5;
        rootElement.setAttribute("width", width.toString());

        Double height = Venue.Depth();
        height += SvgElement.OffsetY().intValue() * 2 + height / 100;
        rootElement.setAttribute("height", height.toString());

//        rootElement.setAttribute( "overflow", "visible" );

        Text textNode = draw.document().createCDATASection(CSS);
        SvgElement style = draw.element("style");
        style.attribute("type", "text/css");
        style.appendChild(textNode);
        rootElement.appendChild(style.element());

        return draw;
    }

    private Draw drawSection() throws MountingException, ReferenceException {
        Draw draw = new Draw();

//        Minder.DrawAllSection(draw.canvas());

//        Hack.Draw( drawPlan.canvas() );

        draw.establishRoot();

//        Minder.DomAllSection(draw);

        Hack.Dom(draw, View.SECTION);

        return draw;
    }

    private Draw drawFront() throws MountingException, ReferenceException {
        Draw draw = new Draw();

//        Minder.DrawAllFront(draw.canvas());

//        Hack.Draw( drawPlan.canvas() );

        draw.establishRoot();

//        Minder.DomAllFront(draw);

        Hack.Dom(draw, View.FRONT);

//        String pathname = basename + "/front.svg";
//
//        draw.create(pathname);

        return draw;
    }

    private Draw drawTruss() throws MountingException, ReferenceException {
//        System.out.println( "writeTruss: About to start.");
        Draw draw = startFile();


//        Grid.DOM(draw);

        // Hardcoded values here are for Arisia '14 flying truss.
        Legend.Startup(draw, View.TRUSS, 700.0, 300 );

//        System.out.println( "writeTruss: About to MinderDom.DomAllTruss.");

        MinderDom.DomAllTruss(draw);

//        System.out.println( "writeTruss: About to Hack.Dom.");
        Hack.Dom(draw, View.TRUSS);

//        System.out.println( "writeTruss: About to Legend.Callback.");
        Legend.Callback();

//        String pathname = basename + "/truss.svg";
//
//        draw.create(pathname);
//        System.out.println( "writeTruss: done.");

        return draw;
    }

    private void writeFile(String basename, String filename, String output) {
        String pathname = basename + "/" + filename;
        File file = new File(pathname);

        try (FileOutputStream stream = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = output.getBytes();

            stream.write(bytes);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDrawings( String pathname ) throws MountingException, ReferenceException {
//        System.out.println();
//        System.out.println( "In writeDrawings." );
        for (ElementalLister thingy : MinderDom.List() ) {
//            System.out.println( "Found a thing of type: "+ thingy.getClass().getCanonicalName() );
            if ( Drawing.class.isInstance( thingy ) ) {
                Drawing drawing = (Drawing) thingy;
//                System.out.println( "About to write drawing for "+drawing.filename() );
                writeIndividualDrawing( drawing ).create( pathname + "/" + drawing.filename() + ".svg" );
            }
        }
//        System.out.println();
    }

    private Draw writeIndividualDrawing( Drawing drawing )
            throws MountingException, ReferenceException {
//        System.out.println( "writeIndividualDrawing: About to start.");
        Draw draw = startFile();


//        Grid.DOM(draw);

        HangPoint.SYMBOLGENERATED = false;

        System.err.println();
        for ( String categoryName : drawing.displayList ) {
            System.err.println( "For " + drawing.filename() +", " + categoryName + " requested." );
            if ( categoryName.equals( Legend.CATEGORY )) {
                System.err.println( "For " + drawing.filename() +", special-case Legend processing." );
//                Legend.Startup( draw, View.PLAN,  Venue.Width() + 5, Legend.PlanWidth() );
                Legend.Startup(draw, View.PLAN,
                        Venue.Width() + SvgElement.OffsetX() * 2 + 5,
                        Legend.PlanWidth() );
                Legend.Callback();
                continue;
            }
            System.err.println( "For " + drawing.filename() +", regular processing." );
            Category category = Category.Select( categoryName );
            if ( null == category ) {
                System.err.println( "For " + drawing.filename() +", " + categoryName + " is not a Category." );
                continue;
            }
            Class requested = category.clazz();
            String layer = category.layer();
            System.err.println( "For " + drawing.filename() +", " + categoryName + " processing." );
            for ( Object thing : MinderDom.List() ) {
                if ( requested.equals( thing.getClass() ) ) {
                    System.err.println( "For " + drawing.filename() +", found a " + categoryName + " to process." );
                    MinderDom item = (MinderDom) thing;
                    if( Device.class.isInstance( item ) )
                    {
                        Device device = (Device) item;
                        if ( null != layer && layer.equals( device.layer() ) ) {
                            device.dom( draw, View.PLAN );
                        }
                    }
                    else {
                        item.dom(draw, View.PLAN);
                    }
                }
            }
        }

//        for ( Object thing : MinderDom.List() ) {
//            if ( Wall.class.isInstance( thing )) {
//                Wall wall = (Wall) thing;
//                wall.dom( draw, View.PLAN );
//            }
//        }
//        for ( Object thing : MinderDom.List() ) {
//            if ( HangPoint.class.isInstance( thing )) {
//                HangPoint hangPoint = (HangPoint) thing;
//                hangPoint.dom( draw, View.PLAN );
//            }
//        }
//        for ( Object thing : MinderDom.List() ) {
//            if ( Truss.class.isInstance( thing )) {
//                Truss truss = (Truss) thing;
//                truss.dom( draw, View.PLAN );
//            }
//        }

        return draw;

    }

    private void writeSpreadsheet( String pathname) {
        // Create the data to save.
        final Object[][] data = GearList.Report();
//        new Object[6][2];
//        data[0] = new Object[] { "January", 1 };
//        data[1] = new Object[] { "February", 3 };
//        data[2] = new Object[] { "March", 8 };
//        data[3] = new Object[] { "April", 10 };
//        data[4] = new Object[] { "May", 15 };
//        data[5] = new Object[] { "June", 18 };

        if ( 0 == data.length ) {
            System.err.println( "No data in GearList, not generating spreadsheet.");
            return;
        }

        String[] columns = new String[] { "Item", "Quantity" };

        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        final File file = new File( pathname );

        try {
        SpreadSheet.createEmpty(model).saveAs(file);

//        OOUtils.open(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

//
//    private String checkboxes( HashMap<String,String> data){
//        return "";
//    }
}

