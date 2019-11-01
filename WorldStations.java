
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 03/19/2014
 @assignment.number A190-09
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>

 */
public class WorldStations extends Stations
{INET net = new INET();
enum sta {X, Y, ID, Name, State, Country, A, Longitude, Latitude,B, C, Elevation,  Elevation2, D}
  String[] aryWorld= new String[Length()];
   // String[] aryNames= new String[Length()];
    //spec 7a
public WorldStations(String strFBIN, String strWorld)throws Exception
{    //spec 7b
    staIndex();

    System.out.println(aryWorld.length);
    BufferedReader inputFile;
    inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(strWorld)));
    String strFileLine = "";
    String strStationID = "";
    int arrayPos = 0;

        String strURL= "http://weather.noaa.gov/data/nsd_bbsss.txt";
        String strFileData = net.getFromFile(strWorld);
        //save data to file
     //getFile(strFBIN, strURL, net.fileExists(strFBIN));
     //getFile(strWorld,strURL,net.fileExists(strWorld));
    //when file exists, get data from file and add to this string
    // create new strtokeniser using return carriages as deliminator
    StringTokenizer strTokenizer = new StringTokenizer(strFileData, "\r\n", true);
    // create new string bbuilder
    StringBuilder stbContent = new StringBuilder("");
    //declare empty string

    //while there are more tokens
    while(strTokenizer.hasMoreTokens())

    {// put next token into the string that was just created


        strFileLine = strTokenizer.nextToken();
        //create a second tokeniser using semicolens as the deliminators, also returining those to the origonal text
        StringTokenizer strTokenizer2 = new StringTokenizer(strFileLine, ";", true);
        //create a new array list to hole the peices being tokenised
        ArrayList<String> list = new ArrayList<String>();
        //while there are ore tokens in the second tokeniser
        while(strTokenizer2.hasMoreTokens())

        {//create a string to hold the next token


            String strSemiSorter = strTokenizer2.nextToken();
            //add each token to the arylist
            list.add(strSemiSorter);

        }
        //use an if statement to determine if the ary is fully populated

            if(list.size()> 11)
           {
            //second if to sore records: if the record does not have dashes in the fourth index and united states int the
            //tenth index, do nothing; otherwise,

            if(!list.get(4).startsWith("----")&&list.get(10).startsWith("United States"))
            {

             // create a string with select peices of the array and append it to the string builder
              strStationID= strFileLine.substring(8,11);
                arrayPos = staIndex.indexOf(strStationID);
               if(exists(strStationID))
                    {System.out.println(strFileLine);


                        aryWorld[arrayPos] = strFileLine;
                        //aryNames[arrayPos] = list.get(6);
                        System.out.println(aryWorld[arrayPos]);
                        while (arrayPos < aryWorld.length)
                        {
                            System.out.println(aryWorld[arrayPos]+"*****************");
                            arrayPos++;
                        }
                    }
                else
               {

               }
            }

            }
    }

}
//spec7f
    /**
     * Return the data from the World file
     *
     * @return data of World file
     */
public  String getStaData(String strStationID)
{
    int intAryPos = 0;
    intAryPos = staIndex.indexOf(strStationID);
    if(aryWorld[intAryPos].length()==0)
    {aryWorld[intAryPos]= "Record not found";}
    System.out.println(aryWorld[intAryPos]+"999999999999");
    return  aryWorld[intAryPos];
}

    @Override
    public int Length() throws Exception
    {
        return super.Length();
    }

    /**return name
     *
     * @param strAryData from entire data string
     * @return station Name
     */
public String getName(String strAryData)
{
    String B ="" ;
   StringTokenizer strTokenizer2 = new StringTokenizer(strAryData, ";", false);

    String A = strTokenizer2.nextToken()+strTokenizer2.nextToken()+strTokenizer2.nextToken();
    B = net.properCase(strTokenizer2.nextToken());

    return B;
}
    /**
     * Return the specific field data from the World file
     *
     * @return field data of World filoe
     */
private String getStaField(String strStationID, sta intField)
{
    String strStaData = getStaData(strStationID);
    String strStaField = "";
    String[] strFieldsAry = strStaData.split(";");
    try
    {
        strStaField= strFieldsAry[intField.ordinal()];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
        strStaField = "";
    }




    return strStaField;
    }

    public String getStaID (String strStationID)
    { String strRet = getStaField(strStationID, sta.ID);
        // return
        return strRet;
    }
    public String getStaName (String strStationID)
    {String strRet = getStaField(strStationID, sta.Name);
        // return
        return strRet;
    }

    public String getStaLatitude (String strStationID)
    {String strRet = getStaField(strStationID, sta.Latitude);
        // return
        return strRet;
    }
    public String getStaLongitude (String strStationID)
    {String strRet = getStaField(strStationID, sta.Longitude);
        // return
        return strRet;
    }
    public String getStaAltitude (String strStationID)
    {String strRet = getStaField(strStationID, sta.Elevation);
        // return
        return strRet;
    }
    public String getStaState (String strStationID)
    {String strRet = getStaField(strStationID, sta.State);
        // return
        return strRet;
    }
    public String getStaCountry (String strStationID)
    {String strRet = getStaField(strStationID, sta.Country);
        // return
        return strRet;
    }
}




