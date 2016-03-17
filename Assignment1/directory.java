//
//
//Created by Koishore Roy on 10/03/2016.
//
//
//directory.java

import java.io.File;
import java.lang.String;

public class directory {
    
    static String[] TYPES = new String[] {"jpg", "png", "gif", "mp4", "mp3", "exe", "psd", "html", "xml"};
    
    public void listd (String dirPath) {
        File dir = new File (dirPath);
        File[] first = dir.listFiles();
        
        if (first != null && first.length > 0) {
            for (File aFile : first) {
                
                if (aFile.isDirectory()) {
                    int c1 = countFiles (aFile);
                    int c2 = countFilesrec (aFile);
                    System.out.println(c2 + " " + c1 + " " + "d" + " " + aFile.getAbsolutePath());
                    listd (aFile.getAbsolutePath());
                }
                
                else {
                    int c1 = 0;
                    int c2 = 0;
                    System.out.println (c2 + " " + c1 + " " + "f" + " " + aFile.getAbsolutePath());
                }
            }
        }
    }

    public void extension (File cwd) {
        for (String ext: TYPES) {
            int count = 0;
            long size = 0;
            
            for (File file : cwd.listFiles()) {
                if (file.isFile()) {
                    if (file.toString().endsWith(ext)) {
                        count++;
                        size = size + file.length();
                    }
                }
                
                else {
                    count += extrecurse (file, ext);
                    size += extrecursesize (file, ext);
                }
            }
            
            System.out.println (ext + " - " + count + " " + String.format("%,d", size));
        }
    }

    public int extrecurse (File director, String extension) {
        int count = 0;
        
        for (File file: director.listFiles()) {
            if (!file.isDirectory()) {
                if (file.toString().endsWith(extension)) {
                    count++;
                }
            }
            
            else {
                count += extrecurse(file, extension);
            }
        }
        return count;
    }

    public int extrecursesize (File director, String extension) {
        long size = 0;
        
        for (File file : director.listFiles()) {
            if (!file.isDirectory()) {
                if (file.toString().endsWith(extension)) {
                    size += file.length();
                }
            }
            else {
                size += extrecursesize (file, extension);
            }
        }
        return (int) size;
    }
    
    public static int countFiles (File direct) {
        int count = 0;
        for (File file : direct.listFiles()) {
            if (!file.isDirectory()) {
                count++;
            }
        }
        return count;
    }
    
    public static int countFilesrec (File direct) {
        int count = 0;
        for (File file : direct.listFiles()) {
            if (file.isDirectory()) {
                count += countFilesrec(file);
            }
            else
                count++;
        }
        return count;
    }
    
    public static void main (String[] args) {
        long start = System.currentTimeMillis();
        directory prog = new directory();
        String list = System.getProperty("user.home") + File.separator + "Desktop";
        File list1 = new File(list);
        prog.listd(list);
        prog.extension(list1);
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println ("Program executed in " + time + " milliseconds.");
    }
}