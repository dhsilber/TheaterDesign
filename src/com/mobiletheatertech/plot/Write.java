package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <code>Write</code> deals with output file issues and relies on {@link Draw Draw} to generate SVG
 * content.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Write {

    private String home = null;

    /**
     * Draw each of the Plot items that have been defined to a SVG file.
     * <p/>
     * The complete pathname is built as {@literal <user's home directory>}{@code
     * /Plot/out/}{@literal <basename>}{@code .xml}
     * <p/>
     * The non-drawing updates to the generated SVG document have to be done after all of the
     * drawing is completed. Because of this, {@code Write} needs to interact with {@link Draw} and
     * {@link Minder} methods in a very specific order.
     *
     * @param basename basename of the file to be written.
     */
    public Write( String basename ) throws MountingException, ReferenceException {
        home = System.getProperty( "user.home" );
//        if (null == home) {
//            // throw exception
//        }

        String pathname = home + "/Plot/out/" + basename;

        writeDirectory( pathname );
        writeIndex( pathname );
        writePlan( pathname );
        writeSection( pathname );
        writeFront( pathname );
    }

    private void writeDirectory( String basename ) /*throws MountingException, ReferenceException*/ {
        File directory = new File( basename );
        Boolean dir = directory.mkdir();
        System.err.println( "Directory: " + basename + ". Good? " + dir.toString() );
    }

    private void writeIndex( String basename ) throws ReferenceException {
        String filename = basename + "/index.html";
        File file = new File( filename );

        String output = "" +
                "<!DOCTYPE html>\n" +
                "<head>\n" +
                "<title>" + Venue.Name() + "</title>\n" +
                "<script>\n" +
                "function show()\n" +
                "{\n" +
                "  var links = plan.document.getElementsByClassName(\"chairblock\");\n" +
                "  for (var i=0; i < links.length; i++) {\n" +
                "      links[i].setAttribute(\"visibility\", \"visible\");\n" +
                "  } \n" +
                "}\n" +
                "function hide()\n" +
                "{\n" +
                "  var links = plan.document.getElementsByClassName(\"chairblock\");\n" +
                "  for (var i=0; i < links.length; i++) {\n" +
                "      links[i].setAttribute(\"visibility\", \"hidden\");\n" +
                "  } \n" +
                "}\n" +
                "function process()\n" +
                "{\n" +
                "  if( document.getElementById('process').checked)\n" +
                "    show();\n" +
                "  else\n" +
                "    hide();\n" +
                "}\n" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "<form>\n" +
                "<input type=\"checkbox\" onclick=\"parent.process();\" name=\"show chairs\"" +
                " id=\"process\" checked=\"checked\" /> Show Chairs\n" +
                "</form>\n" +
                "</div>\n" +
                "<iframe id=\"plan\" src=\"plan.svg\"></iframe>\n" +
                "</body>\n" +
                "</html>\n";

        try ( FileOutputStream stream = new FileOutputStream( file ) ) {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = output.getBytes();

            stream.write( bytes );
            stream.flush();
            stream.close();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void writePlan( String basename ) throws MountingException, ReferenceException {
        Draw draw = new Draw();

        Minder.DrawAllPlan( draw.canvas() );

        Hack.Draw( draw.canvas() );

        draw.getRoot();

        // Specify the size of the generated SVG so that when it is larger than the display area,
        // scrollbars will be provided.
        Element rootElement = draw.root();
        Integer width = Venue.Width() + Legend.Widest();
        width += width / 20;
        rootElement.setAttribute( "width", width.toString() );
        Integer height = Venue.Depth();
        height += height / 20;
        rootElement.setAttribute( "height", height.toString() );
//        rootElement.setAttribute( "overflow", "visible" );

        Grid.DOM( draw );

        Legend.Startup( draw );

        Minder.DomAllPlan( draw );

        Hack.Dom( draw );

        Legend.Callback();

        String pathname = basename + "/plan.svg";

        draw.create( pathname );
    }

    private void writeSection( String basename ) throws MountingException, ReferenceException {
        Draw draw = new Draw();

        Minder.DrawAllSection( draw.canvas() );

//        Hack.Draw( drawPlan.canvas() );

        draw.getRoot();

        Minder.DomAllSection( draw );

        Hack.Dom( draw );

        String pathname = basename + "/section.svg";

        draw.create( pathname );
    }

    private void writeFront( String basename ) throws MountingException, ReferenceException {
        Draw draw = new Draw();

        Minder.DrawAllFront( draw.canvas() );

//        Hack.Draw( drawPlan.canvas() );

        draw.getRoot();

        Minder.DomAllFront( draw );

        Hack.Dom( draw );

        String pathname = basename + "/front.svg";

        draw.create( pathname );
    }
}

