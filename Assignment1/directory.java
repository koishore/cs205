//
//
//Created by Koishore Roy on 10/03/2016.
//
//
//Directory.java

import java.io.File;
import java.lang.String;

public class Directory {
    
    static String[] TYPES = new String[] {"jpg", "png", "gif", "mp4", "mp3", "exe", "psd", "html", "xml"};
    
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
    
    public static int countFiles (File listingdirectory) {
        int count = 0;
        for (File file : listingdirectory.listFiles()) {
            if (!file.isDirectory()) {
                count++;
            }
        }
        return count;
    }
    
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
    
    public static void main (String[] args) {
        long starttime = System.currentTimeMillis();
        Directory program = new Directory();
        String list = System.getProperty("user.home") + File.separator + "Desktop";
        File filelist = new File (list);
        program.directorylisting (list);
        program.extension (filelist);
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println ("Program executed in " + String.format("%,d", time) + " milliseconds.");
    }
}