package com.mobiletheatertech.plot;

import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Read deals with input file issues and relies on {@link Parse Parse} to deal with XML.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Read {

    /**
     * Look for a file named according to the specified basename and open a stream from it which is
     * {@link Parse parsed}.
     * <p/>
     * The complete pathname is built as {@literal <user's home directory>}{@code
     * /Plot/plotfiles/}{@literal <basename>}{@code .xml}
     *
     * @param basename basename of the file to be read.
     * @throws FileNotFoundException if the file cannot be found
     */
    public Read(String basename) throws Exception {

        ReadPlotfile( basename );
//        ReadInferenceSpreadsheet();
//        ReadEventSpreadsheet( basename );
////        Inference.Dump();
    }

    public static void ReadPlotfile(String basename) throws Exception {

        String home = System.getProperty("user.home");
        if (null == home) {
            // throw exception
        }

        String pathname = home + "/Dropbox/Plot/plotfiles/" + basename + ".xml";

        InputStream stream = new FileInputStream(pathname);

        new Parse(stream);
    }

    public static void ReadEventSpreadsheet(String basename) throws Exception {

        String home = System.getProperty("user.home");
        if (null == home) {
            // throw exception
        }

        String pathname = home + "/Dropbox/Plot/plotfiles/" + basename + ".ods";

        // Load the file.
        File file = new File( pathname );
        final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);

        String first = sheet.getCellAt(0,0).getValue().toString();
        int technicalColumn=0;
        for ( int index=1;index< 100; index++ ) {
            String tech = sheet.getCellAt(index,0).getValue().toString();
            if ( tech.equals("Technical")) {
                technicalColumn = index;
                break;
            }
            if (tech.isEmpty())
            {
                break;
            }
        }
        Integer rows = sheet.getRowCount();
        Integer columns = sheet.getColumnCount();

//        System.out.println( "First: " + first +
//                ".  Rows: "+ rows+
//                ".  Columns: "+ columns+
//                ".  Technical starts with column "+technicalColumn);

        int eventColumn=0;
        for (int index=1; index< rows; index++ ) {
            String sessionName = sheet.getCellAt( eventColumn, index).getValue().toString();
            if ( null==sessionName || sessionName.equals("") ) {
                break;
            }
            Session session = new Session( sessionName );

//            System.out.print( "Session: " + sessionName + ".  ");

            for (int column=technicalColumn; column < columns; column++) {
                Object contents = sheet.getCellAt( column, index ).getValue();
                if (null == contents) {
                    break;
                }
                String requirement = contents.toString();

                if (!requirement.isEmpty()) {
                    session.needs( requirement );
                }

//                System.out.print( requirement + ", " );
            }
//            System.out.println();
        }

        // Change date.
//        sheet.getCellAt("I10").setValue(new Date());
        // Change strings.
//        sheet.setValueAt("Filling test", 1, 1);
//        sheet.getCellAt("B27").setValue("On site support");
        // Change number.
//        sheet.getCellAt("F24").setValue(3);
        // Or better yet use a named range
        // (relative to the first cell of the range, wherever it might be).
//        sheet.getSpreadSheet().getTableModel("Products").setValueAt(1, 5, 4);
        // Save to file and open it.
//        File outputFile = new File("fillingTest.ods");
//        OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));

    }

    public static void ReadInferenceSpreadsheet() throws Exception {

        String home = System.getProperty("user.home");
        if (null == home) {
            // throw exception
        }

        String pathname = home + "/Dropbox/Plot/plotfiles/inference.ods";

//        System.out.println("About to open "+pathname );

        // Load the file.
        File file = new File( pathname );


//        System.out.println("Got file");
        final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
//        System.out.println("Got spreadsheet");

        String requirementsTitle = sheet.getCellAt(0,0).getValue().toString();
        if ( !requirementsTitle.equals( "Requirement")) {
            throw new ConfigurationException(
                    "Expected first column of inference data to be named \"Requirement\". ");
        }

        String impliesTitle = sheet.getCellAt(1,0).getValue().toString();
        if ( !impliesTitle.equals( "Implies")) {
            throw new ConfigurationException(
                    "Expected second column of inference data to be named \"Implies\". ");
        }

        Integer rows = sheet.getRowCount();
        Integer columns = sheet.getColumnCount();

//        System.out.println( "Rows: "+ rows+
//                ".  Columns: "+ columns );

        int requirementColumn=0;
        int impliesColumn=1;
        for (int index=1; index< rows; index++ ) {
            String requirement = sheet.getCellAt( requirementColumn, index).getValue().toString();
            if ( null==requirement || requirement.equals("") ) {
                break;
            }
            String implication = sheet.getCellAt( impliesColumn, index).getValue().toString();
            if ( null==implication || implication.equals("") ) {
                break;
            }
            Inference.Add(requirement,implication);
//            System.out.println(requirement+"/"+implication);
        }

        // Change date.
//        sheet.getCellAt("I10").setValue(new Date());
        // Change strings.
//        sheet.setValueAt("Filling test", 1, 1);
//        sheet.getCellAt("B27").setValue("On site support");
        // Change number.
//        sheet.getCellAt("F24").setValue(3);
        // Or better yet use a named range
        // (relative to the first cell of the range, wherever it might be).
//        sheet.getSpreadSheet().getTableModel("Products").setValueAt(1, 5, 4);
        // Save to file and open it.
//        File outputFile = new File("fillingTest.ods");
//        OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));

    }
}
