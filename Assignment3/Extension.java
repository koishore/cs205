//
//
//Created by Koishore Roy on 12/05/2016
//
//
//Extension.java

import java.lang.String;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;

    //********************************************************************************Specifications********************************************************************************
    //Program to check a directory and shows the total number of files, i.e the number of files within a directory and also within any subsequent sub-directroy. Gives an estimate of the type of files inside the directory and lists the total number of a type of file as well as the combined size of all files of the same type. Lastly, the program prints the time taken for the program to execute, in miliseconds.
    //******************************************************************************************************************************************************************************

public class Extension {

    static String[] TYPES = new String[] {"jpg", "png", "gif", "mp4", "mp3", "exe", "psd", "html", "xml"}; //list of extensions. Add ," .abc" to add new extension.
    static List<List<String>> filecounter = new ArrayList<List<String>>();
    
    //********************************************************************************streamDirectory********************************************************************************
    //Takes in a file path, an extension type and extension number as input. Checks within the given directory and recursively and parallely goes through all files and directories and returns the total size of all the files present of a given extension.
    //*******************************************************************************************************************************************************************************
    
    public static long streamDirectory (String filePath, String ext, int extensionNumber) throws IOException {
        
        long size = 0;
        Path directoryPath = FileSystems.getDefault().getPath(filePath);
        DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath); //Creates a stream for a given directory
        
        for (Path path : stream) {
        
            if (Files.isDirectory(path)) {
                size += streamDirectory (path.toString(), ext, extensionNumber); //Executes recursion to recall itself and check size within itself
            }
            
            else {
                
                if (path.toString().endsWith(ext)) {
                    
                    filecounter.get(extensionNumber).add(path.toString());
                    size += path.toFile().length(); //If file, then it adds it to size
                }
            }
        }
        
        stream.close();
        return size;
    }
    
    //***********************************************************************************MyThread************************************************************************************
    //MyThread is a class which initializes and runs the threads. The class first takes in a string, which is the directory path. It takes in the extension name, which is also a string and an int value, the number of extensions. It saves it locally within itself and then parses the values to the run method.
    //*******************************************************************************************************************************************************************************

    static class MyThread extends java.lang.Thread {
        
        String path;
        String extensionName;
        int extensionNum;

        public MyThread (String path, String extensionName, int extensionNum) {
            
            this.extensionName = extensionName;
            this.path = path;
            this.extensionNum = extensionNum;
        }

        public MyThread() {

        }

        @Override
        public void run() { //Execution to run the thread
            
            try {
                
                long size = streamDirectory (path, extensionName, extensionNum);
                System.out.println (extensionName + " - " + filecounter.get(extensionNum).size() + "  " + String.format("%,d",size));
            }
            
            catch (IOException exc) {
                
                exc.printStackTrace();
            }
        }
    }
    
    //********************************************************************************main********************************************************************************
    //The main method of Extension.java takes in a command line argument, which is a directory path to check for files and directories within it. It starts and executes all the parallel threads or prints out invalid input if there is no command line argument. It also keeps track of the system time in miliseconds and prints out the amount of time it took the program to execute.
    //********************************************************************************************************************************************************************
    
    public static void main(String[] args) throws Exception, IOException, InterruptedException {
        long starttime = System.currentTimeMillis();
        
        if (args.length == 1) {
            
            String filepath = args[0];
            MyThread[] thread = new MyThread[TYPES.length]; //Array of equal length as that of number of extensions

            for (int extNum = 0; extNum < TYPES.length; extNum++ ) {
                
                List<String> list = new ArrayList<>();
                filecounter.add(list);
                thread[extNum] = new MyThread(filepath, TYPES[extNum], extNum); //Creates parallel threads for each extension
                thread[extNum].start(); //Starts each thread
            }

            for (int t = 0; t < TYPES.length; t++)
                thread[t].join(); //Execution of all threads simultaneously
        }
        
        else {
            System.out.println ("Invalid Input!");
        }
        
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println ("Program executed in " + String.format("%,d", time) + " milliseconds.");
    }
}