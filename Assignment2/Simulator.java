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

    //*****************************************************************************GateCount******************************************************************************
    //This method takes in the netlist (ideally as a .txt file) file as input and as output prints the number of gates present in the file.
    //********************************************************************************************************************************************************************
    
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
    
    //*******************************************************************************FanOut*******************************************************************************
    //This method takes in the netlist (ideally as a .txt file) file as input and as output prints the fanout for each gate i.e the number of gates each gate is connected with.
    //********************************************************************************************************************************************************************
    
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
    
    //******************************************************************************Simulate******************************************************************************
    //This method takes in 3 arguments; the netlist file, the input file and the number of gates of type File, File and int respectively. It then uses the input and saves them in each row, and calls the 'Compute' function to work with the simulation it has created. It doesn't return anything
    //********************************************************************************************************************************************************************
    
    public void Simulate (File netlist, File inputlist, int number) throws IOException {
        
        FileReader filereader = new FileReader(inputlist.getAbsolutePath());
        LineNumberReader reader = new LineNumberReader(filereader);
        
        String string;
        int len;
        
        while ((string = reader.readLine()) != null) {
            
            String[] input = string.split ("\\s");
            len = input.length;
            int[] inputs = new int[len];
            
            for (int i = 0; i < len; i++) {
                
                inputs[i] = Integer.parseInt(input[i]);
            }
            
            Compute (inputs, netlist, number);
        }
    }
    
    //******************************************************************************Compute******************************************************************************
    //This method takes in the string from the 'Simulate' method, alongwith the netlist file and the number of gates present. It then computes, based on each line and each type of gate the value to be computed and prints the truth table of 0s and 1s. Though it is a void method and doesn't return anything, it is responsible for all the computation.
    //*******************************************************************************************************************************************************************
    
    public void Compute(int[] inputs, File netlist, int number) throws IOException {
        
        FileReader filereader = new FileReader(netlist.getAbsolutePath());
        LineNumberReader reader = new LineNumberReader (filereader);
        String string;
        
        int[] val = new int[number];
        int arg1, arg2;
        
        while ((string = reader.readLine()) != null) {
            
            String[] netline = string.split ("\\s");
            int gatenumber = Integer.parseInt (netline[0]);
            
            if (netline[2].charAt(0) == 'I') {
                
                char p = netline[2].charAt(1);
                arg1 = Character.getNumericValue(p);
                arg1 = inputs[arg1];
            }
            
            else {
                
                arg1 = val[Integer.parseInt(netline[2])];
            }
            
            if (netline[1].charAt(0) == 'N') {
                
                val[Integer.parseInt(netline[0])] = NOT(arg1);
            }

            if (netline.length == 4) {
                
                if (netline[3].charAt(0) == 'I') {
                    
                    char q = netline[3].charAt(1);
                    arg2 = Character.getNumericValue (q);
                    arg2 = inputs [arg2];
                }
                
                else {
                    
                    arg2 = val [Integer.parseInt (netline[3])];
                }
                
                if (netline[1].charAt(0) == 'A') { //AND
                    
                    val[Integer.parseInt(netline[0])] = AND (arg1, arg2);
                }
                
                else if (netline[1].charAt(0) == 'O') { //OR
                    
                    val[Integer.parseInt(netline[0])] = OR (arg1, arg2);
                }
                
                else if (netline[1].charAt(0) == 'N') { //NOT
                    
                    val [Integer.parseInt (netline[0])] = NOT (arg1);
                }
                
                else {
                    
                    System.out.printf ("\n Sorry, out of scope for this program. It takes in only AND, OR, NOT Gates. Thank you. :)");
                }
            }
        }
        
        for (int i = 0; i < val.length; i++) {
            
            if (i < val.length - 1) {
                
                System.out.printf ("%d ", val[i]);
            }
            
            else {
                
                System.out.printf ("%d \n", val[i]);
            }
        }
    }
    
    //******************************************************************************AND*******************************************************************************
    //This function takes in 2 integers as input and outputs either 0 or 1 depending on the AND gate output.
    //****************************************************************************************************************************************************************
    
    public int AND (int x, int y) {
        
        if ((x == 1) && (y == 1)) {
            
            return 1;
        }
        
        else {
            
            return 0;
        }
    }
    
    //******************************************************************************OR*******************************************************************************
    //This function takes in 2 integers as input and outputs either 0 or 1 depending on the OR gate output.
    //***************************************************************************************************************************************************************
    
    public int OR (int x, int y) {
        
        if ((x == 0) && (y == 0)) {
            
            return 0;
        }
        
        else {
            
            return 1;
        }
    }
    
    //******************************************************************************NOT*******************************************************************************
    //This function takes in 1 integer as input and outputs either 0 or 1 depending on the NOT gate output.
    //****************************************************************************************************************************************************************
    
    public int NOT (int x) {
        
        if (x == 1) {
            
            return 0;
        }
        
        else {
            
            return 1;
        }
    }
    
    //******************************************************************************main******************************************************************************
    //The main function checks the program start time and end time to give an extimate of the total time taken to execute the program. It also checks for the command line arguments and sees whether they are valid or not. Finally, the main function calls all other objects, methods and fuctions and executes them to execute the program correctly
    //****************************************************************************************************************************************************************
    
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
            stats.Simulate (netlistfile, inputsfile, number);
        }

        else {
            System.out.println("Invalid Input");
        }
        
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println ("Program executed in " + String.format("%,d", time) + " milliseconds.");
    }
}
