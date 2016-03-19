//
//
//Created by Koishore Roy on 10/03/2016.
//
//
//Directory.java

import java.io.File;
import java.lang.String;

    //********************************************************************************Specifications********************************************************************************
    //Program to check a directory and list all items under that directory. Tells whether the item is a file or a directory and display 'f' or 'd' accordingly. Incase of a directory, the program lists the number of files immediately under the directroy. It also shows the total number of files, i.e the number of files within a directory and also within any subsequent sub-directroy. Gives an estimate of the type of files inside the directory and lists the total number of a type of file as well as the combined size of all files of the same type. Lastly, the program prints the time taken for the program to execute, in miliseconds.
    //******************************************************************************************************************************************************************************

public class Directory {
    
    static String[] TYPES = new String[] {"jpg", "png", "gif", "mp4", "mp3", "exe", "psd", "html", "xml"}; //list of extension types. Add [, "abc"] at the end for new extension in search

    //********************************************************************************directorylisting********************************************************************************
    //Takes in a string name as input, the string name being the address of the directory in consideration. The program then prints all the directories and subdirectories under the given directory and also displays whether they are a file or a directory. It also shows the files immediately under a directroy as well as the total number of files within a directory and all subsequent subdirectories.
    //********************************************************************************************************************************************************************************
    
    public void directorylisting (String dirPath) {
        File dir = new File (dirPath);
        File[] initial = dir.listFiles();
        
        if (initial != null && initial.length > 0) {
            for (File filetocheck : initial) {
                
                if (filetocheck.isDirectory()) {
                    int filecounter = countFiles (filetocheck);
                    int recursivefilecounter = countFilesrecursive (filetocheck);
                    System.out.println(recursivefilecounter + " " + filecounter + " " + "d" + " " + filetocheck.getAbsolutePath());
                    directorylisting (filetocheck.getAbsolutePath());
                }
                
                else {
                    int filecounter = 0;
                    int recursivefilecounter = 0;
                    System.out.println (recursivefilecounter + " " + filecounter + " " + "f" + " " + filetocheck.getAbsolutePath());
                }
            }
        }
    }
    
    //********************************************************************************extension********************************************************************************
    //Takes in a file as input, which is basically a file address. As output, it prints out the name of the extensions in the static list declared above and shows how many of each extension is there, along with the total size of all the files combined.
    //*************************************************************************************************************************************************************************
    
    public void extension (File filename) {
        for (String extensionname : TYPES) {
            int count = 0;
            long size = 0;
            
            for (File file : filename.listFiles()) {
                if (file.isFile()) {
                    if (file.toString().endsWith(extensionname)) {
                        count++;
                        size = size + file.length();
                    }
                }
                
                else {
                    count += numberoffiles (file, extensionname);
                    size += sizeoffiles (file, extensionname);
                }
            }
            
            System.out.println (extensionname + " - " + count + " " + String.format("%,d", size));
        }
    }

    //********************************************************************************numberoffiles********************************************************************************
    //Function which takes in a file address and a string as input. the file address in a certain file location whereas the string is an extension type. As output, the function returns the total number of files of the particular extension present directly under the directory provided as input. It also recursively checks for any other file of the same extension which may be present in any subsequent subdirectory and adds the number of files to the files under the main given directory.
    //*****************************************************************************************************************************************************************************
    
    public int numberoffiles (File address, String extension) {
        int count = 0;
        
        for (File file : address.listFiles()) {
            if (!file.isDirectory()) {
                if (file.toString().endsWith(extension)) {
                    count++;
                }
            }
            
            else {
                count += numberoffiles(file, extension);
            }
        }
        return count;
    }
    
    //********************************************************************************sizeoffiles********************************************************************************
    //Function which takes in a file address and a string as input. the file address in a certain file location whereas the string is an extension type. As output, the function returns the total size of files of the particular extension present directly under the directory provided as input. It also recursively checks for any other file of the same extension which may be present in any subsequent subdirectory and adds its size to the size of the files in that directory.
    //***************************************************************************************************************************************************************************

    public int sizeoffiles (File address, String extension) {
        long size = 0;
        
        for (File file : address.listFiles()) {
            if (!file.isDirectory()) {
                if (file.toString().endsWith(extension)) {
                    size += file.length();
                }
            }
            else {
                size += sizeoffiles (file, extension);
            }
        }
        return (int) size;
    }
    
    //********************************************************************************countFiles********************************************************************************
    //Takes in a file address as input and returns the number of files directly present under that directory. Counts the number of files directly under a directory and returns the value.
    //**************************************************************************************************************************************************************************
    
    public static int countFiles (File listingdirectory) {
        int count = 0;
        for (File file : listingdirectory.listFiles()) {
            if (!file.isDirectory()) {
                count++;
            }
        }
        return count;
    }
    
    //********************************************************************************countFilesrecursive********************************************************************************
    //Takes in a file address as input and returns the number of files recursively present under that directory. Counts the number of files directly under a directory as well as the files under subdirectories of the main directory and returns the value.
    //***********************************************************************************************************************************************************************************
    
    public static int countFilesrecursive (File listingdirectory) {
        int count = 0;
        for (File file : listingdirectory.listFiles()) {
            if (file.isDirectory()) {
                count += countFilesrecursive(file);
            }
            else
                count++;
        }
        return count;
    }
    
    //********************************************************************************main********************************************************************************
    //The main function checks the program start time and end time to give an extimate of the total time taken to execute the program. It also is responsible for declaring the directory for which all files are to be listed.
    //********************************************************************************************************************************************************************
    
    public static void main (String[] args) {
        long starttime = System.currentTimeMillis();
        Directory program = new Directory();
        String list = System.getProperty("user.home") + File.separator + "Documents"; //Main directory to list files under goes here.
        File filelist = new File (list);
        program.directorylisting (list);
        program.extension (filelist);
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println ("Program executed in " + String.format("%,d", time) + " milliseconds.");
    }
}