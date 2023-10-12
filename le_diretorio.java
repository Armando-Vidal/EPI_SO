import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class le_diretorio{ //leitura do programa
    static List<bcp> blocos = new ArrayList<>(); 

    public static List<bcp> leDiretorio() {
        File dir_programas = new File("programas");

        for(File file: dir_programas.listFiles()){
            try{
                if(!file.getName().equals("quantum.txt")){
                    bcp bloco_ind = new bcp(leArquivo(file), file.getName()); //tirei replace
                    blocos.add(bloco_ind);
                }
            } catch(IOException erroLeitura) {
                System.out.println("Erro ao abrir arquivo");
                System.exit(1);
            }
        }

        return blocos;
    }  

    public static List<String> leArquivo(File arq) throws IOException{
        Path path = Paths.get("./programas/" + arq.getName());
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        return lines;
    }

    public static void main(String args[]){
        System.out.println("teste");
        leDiretorio();
        System.out.println(blocos);
        for(int i = 0; i < blocos.size(); i++){
            System.out.println(blocos.get(i).comandos);
        }
    }
}