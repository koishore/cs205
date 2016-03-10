import java.io.File;

public class directory {
    public void listd (String dirPath, int level) {
        File dir = new File(dirPath);
        File[] first = dir.listFiles();
        
        if (first != null && first.length > 0) {
            for (File aFile : first) {
                
                if (aFile.isDirectory()) {
                    System.out.println("d" + " " + aFile.getAbsolutePath());
                    listd(aFile.getAbsolutePath(), level + 1);
                }
                
                else {
                    System.out.println("f" + " " + aFile.getAbsolutePath());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        directory prog = new directory();
        String list = System.getProperty("user.home") + File.separator + "Documents";
        prog.listd(list, 0);
    }
}