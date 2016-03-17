//
//
//Created by Koishore Roy on 10/03/2016.

import java.io.File;

public class directory {
    public void listd (String dirPath) {
        File dir = new File(dirPath);
        File[] first = dir.listFiles();
        
        if (first != null && first.length > 0) {
            for (File aFile : first) {
                
                if (aFile.isDirectory()) {
                    int c1 = countFiles (aFile);
                    int c2 = countFilesrec (aFile);
                    System.out.println(c2 + " " + c1 + " " + "d" + " " + aFile.getAbsolutePath());
                    listd(aFile.getAbsolutePath());
                }
                
                else {
                    int c1 = 0;
                    int c2 = 0;
                    System.out.println(c2 + " " + c1 + " " + "f" + " " + aFile.getAbsolutePath());
                }
            }
        }
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
        directory prog = new directory();
        String list = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "travicin";
        prog.listd(list);
    }
}