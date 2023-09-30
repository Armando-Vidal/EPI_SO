import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ep1{

    private static int count_COM = 0;
    private static int count_ln = 0;
    private static int act = 0;
    public static void main(String args[]) throws IOException{

        Path path = Paths.get("./programas/01.txt");

        List <String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        while(act == 0){
            if(lines.get(count_ln).equals("COM")){
                count_COM++;
            } else if(lines.get(count_ln).equals("SAIDA")){
                act = 1;
            }
            count_ln++;
        }
        
        System.out.println( ((Object)lines.get(0)).getClass().getSimpleName() );
        System.out.println(lines);
        System.out.println(count_COM);
    }
}