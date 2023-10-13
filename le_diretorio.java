import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//leitura do programa
public class le_diretorio{
    static List<bcp> blocos = new ArrayList<>(); //lista com todos os blocos de comando de processos
    static List<String> arq_quantum; //variável auxiliar para tirar o valor do quantum do arquivo
    static int quantum; //valor do quantum em inteiro que será usado

    public static List<bcp> leDiretorio() {
        File dir_programas = new File("programas"); //diretório que será usado

        //laço para separar os arquivos de comando e o quantum
        for(File file: dir_programas.listFiles()){
            try{
                if(!file.getName().equals("quantum.txt")){
                    bcp bloco_ind = new bcp(leArquivo(file), file.getName()); //atribui um arquivo para um bcp individual
                    blocos.add(bloco_ind); //adiciona o bcp individual na lista blocos
                } else {
                    //extração da string contendo o quantum no arquivo e conversão para inteiro
                    Path path = Paths.get("./programas/quantum.txt");
                    arq_quantum = Files.readAllLines(path, StandardCharsets.UTF_8);

                    quantum = Integer.parseInt(arq_quantum.get(0).replaceAll("[^0-9]", ""));
                }
            } catch(IOException erroLeitura) {
                System.out.println("Erro ao abrir arquivo");
                System.exit(1);
            }
        }

        return blocos; //retorna a lista de bcps
    }  

    //função que lê individualmente cada arquivo (é usada no construtor de cada bcp) para separar os comandos
    public static List<String> leArquivo(File arq) throws IOException{
        Path path = Paths.get("./programas/" + arq.getName());
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        return lines; //retorna uma lista de strings onde cada item é uma linha do arquivo (ou um comando)
    }

    //main para testes
    //public static void main(String args[]){
    //    System.out.println("teste");
    //    leDiretorio();
    //    System.out.println(blocos.size());
    //    System.out.println(blocos);
    //    for(int i = 0; i < blocos.size(); i++){
    //        System.out.println(blocos.get(i).comandos);
    //        System.out.println(blocos.get(i).estadoProcesso);
    //    }
    //    System.out.println(quantum);
    //}
}