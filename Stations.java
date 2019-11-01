import java.io.*;
import java.util.ArrayList;

/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 03/19/2014
 @assignment.number A190-09
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>

 */
public class Stations
{
    ArrayList<String> staList = new ArrayList<String>();
    ArrayList<String> staIndex = new ArrayList<String>();

    /**
     *Reads input file and populates file with correct information
     * @throws Exception
     */
    public void staIndex()throws Exception{
        int intCntrLst = 0;
        //String[] staList = new String[176];
        String strFilePath = "C:\\Users\\Chelsea\\A19009\\Data\\FBIN.txt";
        String strRecord = "";
        //have a constructor that will accept the full fbin addresse
        File myFile = new File(strFilePath);
        if(myFile.exists())
        {
            BufferedReader inputFile;
            inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath)));
            for (int intLnCount = 0; intLnCount <= 6; intLnCount++)
            {
                strRecord = inputFile.readLine();
            }
            while(inputFile.ready())
            {
                //read line + hand it to array staList
                strRecord = inputFile.readLine();
                staList.add(strRecord);
                staIndex.add(strRecord.substring(0,3).trim());
            }

            inputFile.close();
        }
    }

    /**
     *gets length of array and returns it
     * @return intLength the length of either array, in this case staList
     * @throws Exception
     */
    public int Length()throws Exception
    {
        staIndex();
        int intLength = staList.size();
        return intLength;
    }


    /**
     * get the station weather from the arrays
     * @param strStationID hands the method stationID
     * @return strWeather containing the entire weather string
     */
    public String getStaWea(String strStationID)
    { if(exists(strStationID)){
        int intBully = staIndex.indexOf(strStationID);
        String strStaWeather = staList.get(intBully);
        return  strStaWeather;
    }
    else
        return "not existant";
    }


    /**
     * checks for the existance of the entered stationID
     * @param strStationID comes from input dialogue pane
     * @return blnStationExists indicates whether station name is contained in staIndex
     */
    public boolean exists(String strStationID)
    {
        Boolean blnStationExists = false;
        if(staIndex.contains(strStationID))
        {blnStationExists = true;}
        return blnStationExists;
    }

    /**
     * Gets station Id using specified index
     * @param intPos signifies index position in array staIndex
     * @return strID containing ID for chosen station.
     */
    public String getStaID(int intPos)
    {
        String strID = "";
        strID = staIndex.get(intPos);

        return strID;
    }
}
