import  javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 04/11/2014
 @assignment.number A190-09
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>

 */
public class A19009 extends JDialog
{
    private JPanel contentPane;
    private JButton buttonReport;
    private JComboBox cboLocation;
    private JComboBox cboAltitude;
    private JLabel lblWindDir;
    private JLabel lblWindSpeed;
    private JLabel lblWindTemp;
    private JLabel lblLatitude;
    private JLabel lblLongitude;
    private JLabel lblAltitude;
    private static WorldStations db;
    private static INET net;
    private static NWSFB fb;
    String[] aryAltitudes = {"03000","06000","09000","12000","18000","24000","30000","34000","36000"};

    public A19009()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonReport);

        buttonReport.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               // onOK();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }
    /**
     * this method when called will populate the altitude combobox     */
    private void populateAltitudes()
    {
        for(int i=0; i<9; i++)
        {
            cboAltitude.addItem(aryAltitudes[i]);
        }
    }

    public static void main(String[] args)throws Exception
    {       String strFBINFileIn = "data\\FBIN.txt";
        String strFBINURL = "http://www.aviationweather.gov/products/nws/all";
        getFile(strFBINFileIn, strFBINURL, true);
        INET net = new INET();
        A19009 dialog = new A19009();
        //resize form
        final int WINDOW_WIDTH = 550;
        final int WINDOW_HEIGHT = 250;
        dialog.contentPane.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        dialog.populateAltitudes();
        dialog.populateLocations();
        String strURL= "http://weather.noaa.gov/data/nsd_bbsss.txt";
        getFile("C:\\Users\\Chelsea\\A19009\\Data\\Worlds.txt",strURL,net.fileExists("C:\\Users\\Chelsea\\A19009\\Data\\Worlds.txt"));
        WorldStations db = new WorldStations("C:\\Users\\Chelsea\\A19009\\Data\\FBIN.txt","C:\\Users\\Chelsea\\A19009\\Data\\Worlds.txt");
        dialog.setTitle("A19009 -Chelsea Dorich");
        String staID = cboLocation.getSelectedItem().toString().substring(0, 3);
        String altitudeSelection = (String) cboAltitude.getSelectedItem();

        // determine weather from selections
        if (staID != null &&
                altitudeSelection != null)
        {
            // get the weather string from the database
            String weather = db.getStaWea(staID);

            // Instantiate a  object (from last assignment with all of the exceptions handled) called fb using
            // the station weather that you got from the stations object
            fb = new NWSFB(weather);

            // get data for labels to the right
            String windDir = fb.getWindDir(altitudeSelection);
            String windSpeed = fb.getWindSpeed(altitudeSelection);
            String windTemp = fb.getWindTemp(altitudeSelection);

            // set for winds aloft
            lblWindDir.setText(windDir);
            lblWindSpeed.setText(windSpeed);
            lblWindTemp.setText(windTemp);


            // set longitude, latitude, altitude
            String staLatitude = db.getStaLatitude(staID);
            String staLongitude = db.getStaLongitude(staID);
            String staAltitude = db.getStaAltitude(staID);
            lblLatitude.setText(staLatitude);
            lblLongitude.setText(staLongitude);
            lblAltitude.setText(staAltitude);
        }
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    private void populateLocations() throws Exception
    {
        WorldStations db = new WorldStations("C:\\Users\\Chelsea\\A19009\\Data\\FBIN.txt","C:\\Users\\Chelsea\\A19009\\Data\\Worlds.txt");

        int intLength = db.Length();
        for(int i=0; i< intLength; i++)
        {
            try{
            cboLocation.addItem(db.getStaID(i)+" - "+db.getName(db.getStaData(db.getStaID(i))));
            }
                catch(NullPointerException e)
                {
                    cboLocation.addItem(db.getStaID(i));
                }

        }
    }
   // action listener
    btnReport.addActionListener(new ActionListener()
{
    public void actionPerformed (ActionEvent e)
    {

        try
        {
            // Open an output file called FBOUT.txt
            PrintWriter outputFile = new PrintWriter("data\\FBOUT.txt");

            // header
            outputFile.println("A19009 - by Chelsea Dorich");
            outputFile.println();

            // write number of stations
            outputFile.println("The number of stations in the file: " + db.Length());

            for (int i = 0; i < db.Length(); i++)
            {
                // get location from array list
                String strStaID = db.getStationID(i);

                // Get the World station ID if available - if empty then blank out
                String strWorldStaID = db.getStaID(strStaID);
                boolean blnPrintWorldData = true;
                if (strWorldStaID.isEmpty())
                    blnPrintWorldData = false;

                // print World station ID
                if (blnPrintWorldData)
                {
                    outputFile.println(strWorldStaID + " - " + net.properCase(db.getStaName(strStaID)));

                    // print Latitude, Longitude, and elevation
                    outputFile.println("   Latitude:\t" + db.getStaLatitude(strStaID));
                    outputFile.println("   Longitude:\t" + db.getStaLongitude(strStaID));
                    outputFile.println("   Elevation:\t" + db.getStaAltitude(strStaID));
                }

                // Write the station weather raw data to the out file.
                outputFile.println("   Station Weather for " + db.getStaWea(strStaID));

                // parse through all altitudes
                for (int i = 0; i < aryAltitudes.length; i++)
                {
                    try
                    {
                        // print formatted weather
                        outputFile.println("   " + fb.fmtWeatherReport(aryAltitudes[i]));
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }

                // blank line for in between each weather station
                outputFile.println();
            }

            // close output file
            outputFile.close();
        }
        catch (FileNotFoundException e1)
        {
            // catch file creation error
            e1.printStackTrace();
        }
    }}

    /**
     * Check to see if file exists. If not, create.
     *
     * @param strFileName a full file path and name.
     * @param strURL      URL as a string
     * @param blnExtract  Extract using PRE tags
     */

    private static void getFile(String strFileName, String strURL, Boolean blnExtract)
    {
        String webPageContentsRaw = "";
        String webPageContentsCleaned = "";
        String webPageURL = strURL;
        INET net = new INET();



        if(!blnExtract)
        {
            System.out.println(strFileName);
            try {
                webPageContentsRaw = net.getURLRaw(webPageURL);
            } catch (Exception e) {
                e.printStackTrace();
            }

            webPageContentsCleaned = webPageContentsRaw.trim();

            if (strFileName.equals(strFileName.endsWith("data/FBIN.txt")||strFileName.endsWith("data/World.txt"))) {
                webPageContentsCleaned = net.getPREData(webPageContentsRaw);
            }

            webPageContentsCleaned = webPageContentsCleaned.trim();

            try {
                net.saveToFile(strFileName, webPageContentsCleaned);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
