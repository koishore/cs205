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
    //This method takes in the netlist (ideally as a .txt file) file as input and as output prints the number of gates present in the file.
    //***************************************************************************************************************************************
    
    public int GateCount (File netlist) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(netlist.getAbsolutePath()));
        int count = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {}
        
        count = reader.getLineNumber();
        reader.close();
        System.out.println(count);
        return count;
    }
    
    //*****************************************************************FanOut****************************************************************
    //This method takes in the netlist (ideally as a .txt file) file as input and as output prints the fanout for each gate i.e the number of gates each gate is connected with.
    //***************************************************************************************************************************************
    
    public void FanOut (File netfile, int number) throws IOException {
        
        FileReader filereader = new FileReader (netfile.getAbsolutePath());
        LineNumberReader reader = new LineNumberReader (filereader);
        String string;
        
        int gatenumber = number, arg1, arg2;
        int count = 0;
        
        while ((string = reader.readLine()) != null ) {
            
            String[] element = string.split("\\s");
            
            if (element[2].charAt(0) == 'I') {
                
                arg1 = -1;
            }
            
            else {
                
                arg1 = Integer.parseInt(element[2]);
            }
            
            if (gatenumber == arg1) {
                
                count++;
            }
            
            if (element.length == 4){
                
                if (element[3].charAt(0) == 'I') {
                    
                    arg2 = -1;
                }
                
                else {
                    
                    arg2 = Integer.parseInt(element[3]);
                }
                
                if (gatenumber == arg2) {
                    
                    count++;
                }
            }
        }
        
        System.out.println (gatenumber + " " + count);
    }
    
    //********************************************************************************main********************************************************************************
    //The main function checks the program start time and end time to give an extimate of the total time taken to execute the program. It also checks for the command line arguments and sees whether they are valid or not. Finally, the main function calls all other objects, methods and fuctions and executes them to execute the program correctly
    //********************************************************************************************************************************************************************
    
    public static void main (String args[]) throws Exception {
        
        long starttime = System.currentTimeMillis();
        if (args.length == 2) {

            File netlistfile = new File(args[0]);
            File inputsfile = new File(args[1]);
            Simulator stats = new Simulator();
            
            int number = stats.GateCount (netlistfile);
            
            for (int i = 0; i < number; i++) {
                
                stats.FanOut (netlistfile, i);
            }
        }

        else {
            System.out.println("Invalid Input");
        }
        
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println ("Program executed in " + String.format("%,d", time) + " milliseconds.");
    }
}
