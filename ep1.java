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
    private static int x = 0;
    private static int y = 0;
    private static int value_x = 0;
    private static int value_y = 0;
    public static void main(String args[]) throws IOException{

        Path path = Paths.get("./programas/01.txt");

        List <String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        while(act == 0){
            System.out.println(count_ln);
            //Conta o nº de comandos COM
            if(lines.get(count_ln).equals("COM")){
                System.out.println("Linha COM");
                count_COM++;
            }
            //Interrompe o while na última linha
            if(lines.get(count_ln).equals("SAIDA")){
                System.out.println("Linha SAIDA");
                act = 1;
            }
            //Identifica uma linha que se inicia com 'X' ou 'Y'
            if(Character.compare(lines.get(count_ln).charAt(0), 'X') == 0){
                System.out.println("Linha X");
                value_x = Integer.parseInt(lines.get(count_ln).replaceAll("[^0-9]", ""));
                System.out.println("value_x = "+ value_x);
                x = value_x;
                System.out.println("valor de X = " + x);
            } else if(Character.compare(lines.get(count_ln).charAt(0), 'Y') == 0){
                System.out.println("Linha Y");
                value_y = Integer.parseInt(lines.get(count_ln).replaceAll("[^0-9]", ""));
                System.out.println("value_y = " + value_y);
                y = value_y;
                System.out.println("valor de Y = " + y);
            }
            count_ln++;
        }
        
        //System.out.println( ((Object)lines.get(0)).getClass().getSimpleName() );
        //System.out.println(lines);
        //System.out.println(count_COM);
        System.out.println(x + " " +  y);
    }
}