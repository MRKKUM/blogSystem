package info16.CreateFile;

import java.io.*;
import java.util.UUID;

public class CreateFile {

    public static String newFile(String content) throws IOException {
        String name = num()+".txt";
        String path = "/OurHomework/web/CardTxt/"+name;
        File f = new File(path);
        Writer filewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
        filewriter.write(content);
        filewriter.flush();
        filewriter.close();
        return path;
    }

    private static String num(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
