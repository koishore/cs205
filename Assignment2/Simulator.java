//
//
//Created by Koishore Roy on 24/03/2016.
//
//
//Simulator.java

import java.io.File;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;

public class Simulator {

    //***************************************************************GateCount***************************************************************
    //This function takes in the netlist (ideally as a .txt file) file as input and as output prints the number of gates present in the file.
    //***************************************************************************************************************************************
    
    public void GateCount (File netlist) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(netlist.getAbsolutePath()));
        int count = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {}
        
        count = reader.getLineNumber();
        reader.close();
        System.out.println("The number of gates : " + count);
    }
    
    //********************************************************************************main********************************************************************************
    //The main function checks the program start time and end time to give an extimate of the total time taken to execute the program. It also checks for the command line arguments and sees whether they are valid or not.
    //********************************************************************************************************************************************************************
    public static void main (String args[]) throws Exception {
        
        if (args.length == 2) {

            File netlistfile = new File(args[0]);
            File inputsfile = new File(args[1]);
            Simulator stats = new Simulator();
            stats.GateCount (netlistfile);
        }

        else {
            System.out.println("Invalid Input");
        }
    }
}
