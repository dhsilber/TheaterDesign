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

    static String CSS = "\n"
            + ".heading { font-size: 14pt; text-anchor: middle; font-weight: bold; stroke: none }\n"
            + "iframe { display: inline }\n"
            + "@media print {\n"
            + "  .noprint { display: none }"
            + "}\n";

    static String ECMAScript = "\n"
            + "    function showData(evt) {\n"
            + "        var box = evt.target.getBoundingClientRect();\n"
            + "        var id = evt.target.getAttributeNS( null, \"id\" );\n"
            + "        var textElement = document.getElementById( \"persistent\" );\n"
            + "        textElement.textContent = id;\n"
            + "        textElement.setAttributeNS( null,  \"visibility\", \"visible\" );\n"
            + "        textElement.setAttributeNS( null,  \"x\", box.left );\n"
            + "        textElement.setAttributeNS( null,  \"y\", box.bottom - box.height );\n"
            + "    }\n"
            + "\n"
            + "    function showData_didnotwork(evt) {\n"
            + "        var id = evt.target.getAttributeNS( null, \"id\" );\n"
            + "        var textElement = document.createElementNS(null,\"text\" );\n"
            + "        var textNode = document.createTextNode( id );\n"
            + "        textElement.setAttributeNS( null,  \"font-size\", \"12\" );\n"
            + "        textElement.setAttributeNS( null,  \"fill\", \"blacK\" );\n"
            + "        textElement.setAttributeNS( null,  \"stroke\", \"blacK\" );\n"
            + "        textElement.setAttributeNS( null,  \"text-anchor\", \"left\" );\n"
            + "        textElement.setAttributeNS( null,  \"visibility\", \"visible\" );\n"
            + "        textElement.appendChild( textNode );\n"
            + "        var svgRoot = document.getElementsByTagName(\"svg\")[0];\n"
            + "        svgRoot.appendChild( textElement );\n"
            + "    }\n"
            + "\n"
            + "    function hideData(evt) {\n"
            + "        var textElement = document.getElementById( \"persistent\" );\n"
            + "        textElement.setAttributeNS( null,  \"visibility\", \"hidden\" );\n"
            + "    }\n"
            + "\n"
            + "    function fragments(evt) {\n"
            + "        textElement.setAttributeNS( null,  \"x\", \"100\" );\n"
            + "        textElement.setAttributeNS( null,  \"y\", \"100\" );\n"
            + "        textElement.setAttribute( \"transform\", \"translate(\" + box.left + \", \" + box.top + \")\" );\n"
            + "        var data = evt.target.nextElementSibling();\n"
            + "        var reference = evt.target.getAttributeNS( null, \"id\" );\n"
            + "        document.appendChild( textElement );\n"
            + "    }\n"
            + "// ";


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
    public void init( /*String basename*/ )
            throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
    {
//        home = System.getProperty("user.home");
//
//        // TODO Is it even possible for this to happen?
////        if (null == home) {
////            // throw exception
////        }

        String pathname = Configuration.SinkDirectory();

        System.out.println( "Write path: " + pathname );

        writeDirectory(pathname);
        writeFile( pathname, "drawings.html", generateHTMLDrawingList( Configuration.BaseName() ) );
        writeFile( pathname, "designer.html", generateDesigner() );
        writeFile( pathname, "styles.css", CSS );
        // TODO factor out heading generation for these:
//        System.err.println( " Plan");
        drawPlan().create( pathname + "/plan.svg" );
//        System.err.println( " Section");
        drawSection().create( pathname + "/section.svg" );
//        System.err.println( " Front");
        drawFront().create( pathname + "/front.svg" );
//        System.err.println( " Create");
        drawTruss().create( pathname + "/truss.svg" );

//        System.err.println( " Drawings");
        writeDrawings( pathname );
        writeWeightCalculations( pathname );
//        System.err.println( " Spreadsheet");
//        writeGearSpreadsheet(pathname + "/gear.ods");
//        writeLuminaireSpreadsheet( pathname + "/luminaires.ods" );
    }

    private void writeDirectory(String basename) /*throws MountingException, ReferenceException*/ {
        File directory = new File(basename);
        Boolean dir = directory.mkdir();
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
        StringBuilder generated = new StringBuilder( "<p>\n" );

        for (ElementalLister thingy : MinderDom.List() ) {
            if ( Drawing.class.isInstance( thingy ) ) {
                Drawing drawing = (Drawing) thingy;
                String extension =".svg";
                switch (drawing.viewString) {
                    case "spreadsheet":
                        extension = ".ods";
                        break;
                }
                generated.append( "<a href=\"" + basename + "/" + drawing.filename + extension + "\">" +
                        Venue.Name() + ": " + drawing.id + "</a><br/>\n" );
            }
//            generated.append( "<a href=\"" + basename + "/gear.ods\">" +
//                    Venue.Name() + ": Gear spreadsheet</a><br/>\n" );
//            generated.append( "<a href=\"" + basename + "/luminaires.ods\">" +
//                    Venue.Name() + ": Luminaires spreadsheet</a><br/>\n" );
        }

        generated.append( "</p>\n" );

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

    private Draw drawPlan() throws InvalidXMLException, MountingException, ReferenceException {

        resetOneOffs();

        Draw draw = startFile();
        Legend.Startup(draw, View.PLAN,
                Venue.Width() + SvgElement.OffsetX() * 2 + 5,
                Legend.PlanWidth() );
        MinderDom.DomAllPlan(draw);

        Hack.Dom(draw, View.PLAN );

        Legend.Callback();

//        String pathname = basename + "/plan.svg";
//
//        draw.create(pathname);
        return draw;
    }

    Draw startFile() throws ReferenceException {
        Draw draw = new Draw();
        draw.establishRoot();

        // Specify the size of the generated SVG so that when it is larger than the display area,
        // scrollbars will be provided.
        Element rootElement = draw.root();
        rootElement.setAttribute( "xmlns:plot", "http://www.davidsilber.name/namespaces/plot" );

        Double width = Venue.Width() + Legend.PlanWidth() + SvgElement.OffsetX() * 2 + 5;
        width += width / 100 + 5;
        rootElement.setAttribute("width", width.toString());

        Double height = Venue.Depth();
        height += SvgElement.OffsetY().intValue() * 2 + height / 100;
        rootElement.setAttribute("height", height.toString());

//        rootElement.setAttribute( "overflow", "visible" );

        SvgElement style = draw.element("style");
        style.attribute("type", "text/css");
        Text cssText = draw.document().createCDATASection(CSS);
        style.appendChild(cssText);
        rootElement.appendChild(style.element());

        SvgElement script = draw.element("script");
        script.attribute("type", "text/ecmascript");
        Text scriptText = draw.document().createCDATASection(ECMAScript);
        script.appendChild(scriptText);
        rootElement.appendChild(script.element());

        SvgElement textBox = draw.element("text");
        textBox.attribute("id", "persistent");
        textBox.attribute("fill", "black");
        textBox.attribute("stroke", "none");
        textBox.attribute("font-size", "12");
        textBox.attribute("visibility", "hidden");
        Text innerText = draw.document().createTextNode( "initial content" );
        textBox.appendChild( innerText );
        rootElement.appendChild(textBox.element());

        return draw;
    }

    private Draw drawSection() throws InvalidXMLException, MountingException, ReferenceException {

        resetOneOffs();

        Draw draw = startFile();
        Legend.Startup(draw, View.SECTION,
                Venue.Width() + SvgElement.OffsetX() * 2 + 5,
                Legend.PlanWidth() );
        MinderDom.DomAllSection(draw);

        Hack.Dom(draw, View.SECTION );

        Legend.Callback();

//        String pathname = basename + "/plan.svg";
//
//        draw.create(pathname);
        return draw;
    }

    private Draw drawFront() throws MountingException, ReferenceException {

        resetOneOffs();

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

    private Draw drawTruss() throws InvalidXMLException, MountingException, ReferenceException {

        resetOneOffs();

        Draw draw = startFile();


//        Grid.DOM(draw);

        // Hardcoded values here are for Arisia '14 flying truss.
        Legend.Startup(draw, View.TRUSS, 700.0, 300);

        MinderDom.DomAllTruss(draw);

        Hack.Dom(draw, View.TRUSS);

        Legend.Callback();

//        String pathname = basename + "/truss.svg";
//
//        draw.create(pathname);

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

    private void writeDrawings( String pathname )
            throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
    {
        for (ElementalLister thingy : ElementalLister.List() ) {
            if ( Drawing.class.isInstance( thingy ) ) {
                Drawing drawing = (Drawing) thingy;
                if ("spreadsheet".equals( drawing.viewString )) { return; }
                writeIndividualDrawing( drawing ).create( pathname + "/" + drawing.filename() + ".svg" );
            }
        }
    }

    Draw writeIndividualDrawing( Drawing drawing )
            throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
    {
System.out.println( "Drawing: " + drawing.filename() );

        resetOneOffs();

        Draw draw = startFile();
        View view = drawing.view();

        for ( String mountableName : drawing.mountables ) {
            Mountable mountable = Mountable.Select( mountableName );
            if ( null == mountable ) {
                System.err.println(
                        "For " + drawing.filename() +", " + mountableName + " is not a valid mountable." );
                continue;
            }
//            mountable.preview( view);
        }

        for ( String deviceName : drawing.devices ) {
            Device device = Device.Select( deviceName );
            if ( null == device ) {
                System.err.println(
                        "For " + drawing.filename() +", " + deviceName + " is not a valid device." );
                continue;
            }
//            device.preview( view);
        }

        for ( String layerName : drawing.layers ) {
            Layer layer = Layer.List().get( layerName );
            if ( null == layer ) {
                if( ! "legend".equals( layerName ) ) {
                    System.err.println(
                            "For " + drawing.filename() + ", " + layerName + " is not a Layer.");
                }
                continue;
            }
//            for ( Layerer item : layer.contents() ) {
//                if( Schematicable.class.isInstance( item ) ) {
//                    Schematicable thingy = (Schematicable) item;
//                    thingy.preview( view );
//                }
//            }
        }
//        Multicable.PreviewAll( view );

//        switch (view) {
//            case SCHEMATIC:
////                CableRun.Collate();
//                break;
//            default:
//                break;
//        }

        for ( String mountableName : drawing.mountables ) {
            Mountable mountable = Mountable.Select( mountableName );
            if ( null == mountable ) {
                continue;
            }
            mountable.dom( draw, view );
        }

        for ( String deviceName : drawing.devices ) {
            Device device = Device.Select( deviceName );
            if ( null == device ) {
                continue;
            }
            device.dom( draw, view );
        }

        for ( String layerName : drawing.layers ) {
System.out.println( "LayerName: " + layerName );
            if ( layerName.equals( Legend.CATEGORY )) {
                switch (view) {
                    case PLAN:
                        Legend.Startup(draw, drawing, View.PLAN,
                                Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS + 45,
                                Legend.PlanWidth() );
                        break;
//                    case SCHEMATIC:
//                        Legend.Startup(draw, drawing, View.SCHEMATIC,
//                                Schematic.TotalWidth + 100, Legend.PlanWidth() );
//                        break;
                }
                Legend.Callback();
                continue;
            }

//            if ( layerName.equals( "LuminaireTable" )) {
//                LuminaireTable.dom( draw, View.SCHEMATIC );
//                continue;
//            }

            Layer layer = Layer.List().get( layerName );
            if ( null == layer ) {
                continue;
            }
            for ( Layerer item : layer.contents() ) {
System.out.println( "Item: " + item.id );
                if( MinderDom.class.isInstance( item ) ) {
                    MinderDom thingy = (MinderDom) item;
                    thingy.dom( draw, view );
                }
            }

//            if ( layerName.equals( "LuminaireTable" )) {
//                LuminaireTable.dom( draw, View.SCHEMATIC );
//                continue;
//            }
        }
//        Multicable.DomAll( draw, view );
//        CableRun.DomAll( draw, view );

        return draw;
    }

    protected void writeWeightCalculations( String pathname )  {
        String weightsPath = pathname + "/weights";

        writeDirectory( weightsPath );

        for( Mountable mount : Mountable.MountableList() ) {
            String text = "";
            try {
                text = mount.weights();
            }
            catch (Exception e) {
                System.err.println( "writing weights: " + mount.id() + "  " + e.getMessage() );
                continue;
            }
            String name = mount.id();
            writeFile( weightsPath, name, text );
        }
    }

    private void writeGearSpreadsheet( String pathname) {
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

    private void writeLuminaireSpreadsheet( String pathname) {
        // Create the data to save.
        final Object[][] data = Luminaire.Report();
//        new Object[6][2];
//        data[0] = new Object[] { "January", 1 };
//        data[1] = new Object[] { "February", 3 };
//        data[2] = new Object[] { "March", 8 };
//        data[3] = new Object[] { "April", 10 };
//        data[4] = new Object[] { "May", 15 };
//        data[5] = new Object[] { "June", 18 };

        if ( 0 == data.length ) {
            System.err.println( "No data in Luminaire list, not generating spreadsheet.");
            return;
        }

        String[] columns = new String[]
                { "Unit", "Type", "Location", "Dimmer", "Channel", "Address", "Notes" };

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

    private void resetOneOffs() {
        HangPoint.SYMBOLGENERATED = false;
        Chair.SYMBOLGENERATED = false;
//        Chair.LEGENDREGISTERED = false;
        Chair.CHAIRCOUNT = 0;
        LuminaireDefinition.CountReset();
        DeviceTemplate.CountReset();
        DanceTile.Count = 0;
        Truss$.MODULE$.Reset();
        LightingStand.SYMBOLGENERATED = false;
        LightingStand.Count = 0;
//        Legend.
//        Schematic.Reset();
//        CableRun.Reset();
        Pipe.SchematicPositionReset();
        LuminaireTable.clear();
    }

//
//    private String checkboxes( HashMap<String,String> data){
//        return "";
//    }
}
