import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter; 
import java.io.IOException;

public class escalonador { //cordena o processo de escalonamento
    int quantum;
	private String nome_log;
    FileWriter log;

    private List<bcp> prontos = new ArrayList<>(); //lista de bcps prontos
    private List<bcp> bloqueados = new ArrayList<>(); //lista de bcps bloqueados
    private int ultimo_pronto = 0; //variável que vai salvar o index disponível de prontos
    private int ultimo_bloqueado = 0; //variável que vai salvar o index disponível de bloqueados
    private le_diretorio leitor = new le_diretorio(); //variável que fará a leitura de diretórios
    private tabela_processos tp = new tabela_processos(); //variável para a tabela de processos ativos
    private List<bcp> finalizados = new ArrayList<>(); //lista com todos os processos finalizados para calcular media de interrupções
    private List<Integer> instucoes_quantum = new ArrayList<>();

    //construtor
    public escalonador(){
        this.leitor.leDiretorio(); //função que já lê o diretório programas na inicialização do objeto
        this.quantum = leitor.quantum; //atualização do quantum
        criaArquivoLog(); //criação do arquivo txt para log
        criaProntosBloqueados(); //criação de uma lista de prontos e bloqueados
    }

    //função para criar a lista de prontos e bloquados
    public void criaProntosBloqueados(){
        //passa pela lista "blocos" e divide entre prontos e bloqueados
        for(int i = 0; i < leitor.blocos.size(); i++){
            if(leitor.blocos.get(i).estadoProcesso == "Pronto"){
                prontos.add((ultimo_pronto), leitor.blocos.get(i)); //add bcp em prontos em um novo índice (seguinte ao último)
                try {
                    log.write("Carregando " + leitor.blocos.get(i).nomeTeste + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                tp.addBCP(leitor.blocos.get(i)); //ads o bcp na tabela de processos (somente os com estado prontos)
                ultimo_pronto++;
            } else if(leitor.blocos.get(i).estadoProcesso == "Bloqueado"){
                bloqueados.add((ultimo_bloqueado), leitor.blocos.get(i)); //add bcp em bloqueados em um novo índice (seguinte ao último)
                ultimo_bloqueado++;
            }
        }
    }

    public void criaArquivoLog(){
        if(quantum < 10){
            nome_log = "0" + quantum;
        } else{
            nome_log = String.valueOf(quantum);
        }
        try {
            log = new FileWriter("log" + nome_log + ".txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void diminuitempodeEspera(){
        int i = 0;
        while(i < bloqueados.size()){
            //System.out.println("Tempo de espera de " + bloquados.get(i) + ": " + bloquados.get(i).tempodeEspera);
            bloqueados.get(i).tempodeEspera--;
            if(bloqueados.get(i).tempodeEspera == 0){
                prontos.add(prontos.size(), bloqueados.get(i));
                //tp.addBCP(bloqueados.get(i));
                bloqueados.remove(i);
                prontos.get(prontos.size() - 1).tempodeEspera = 2;
                prontos.get(prontos.size() - 1).estadoProcesso = "Pronto";
            } else{
                i++;
            }
        }
    }

    public void rodaProgramas(){
        //while que esvazia tabela de bcps
        while(0 < tp.tabela.size()){
            prontos.get(0).fazComando();
            //caso de quantum 3 -> passa arquivo para o final da lista de prontos
            if(prontos.get(0).contadorQuantum == quantum && prontos.get(0).estadoProcesso != "Bloqueado" && !prontos.get(0).finalizado){
                prontos.get(0).interrupcoes++;
                instucoes_quantum.add(instucoes_quantum.size(), prontos.get(0).contadorQuantum);

                try {
                    log.write("Interrompendo " + prontos.get(0).nomeTeste + " após " + prontos.get(0).contadorQuantum + " instruções" + "\n");;
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                prontos.get(0).contadorQuantum = 0; //reseta quantum para quando esse arquivo rodar de novo
                prontos.add(prontos.size(), prontos.get(0));
                prontos.remove(0);
                
                diminuitempodeEspera();

                try {
                    log.write("Executando " + prontos.get(0).nomeTeste + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            //caso de E/S -> (por enquanto) remove da lista de prontos e tabela e coloca na lista de bloqueados (mas não sai de lá nunca)
            } else if(prontos.get(0).estadoProcesso == "Bloqueado"){
                diminuitempodeEspera();
                prontos.get(0).interrupcoes++;
                instucoes_quantum.add(instucoes_quantum.size(), prontos.get(0).contadorQuantum);

                try {
                    log.write("E/S iniciada em " + prontos.get(0).nomeTeste + "\n");
                    log.write("Interrompendo " + prontos.get(0).nomeTeste + " após " + prontos.get(0).contadorQuantum + " instruções" + "\n");;
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                bloqueados.add(bloqueados.size(), prontos.get(0));
                prontos.remove(0);

                bloqueados.get(bloqueados.size() - 1).contadorQuantum = 0;

                try {
                    log.write("Executando " + prontos.get(0).nomeTeste + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            //caso de SAIDA -> remove o arquivo da tabela e de prontos
            } else if(prontos.get(0).finalizado){
                tp.removeBCP(prontos.get(0));
                instucoes_quantum.add(instucoes_quantum.size(), prontos.get(0).contadorQuantum);

                diminuitempodeEspera();
                
                if(prontos.size() > 1){
                    try {
                        log.write(prontos.get(0).nomeTeste + " terminado. X=" + prontos.get(0).X + ". Y=" + prontos.get(0).Y + "\n");
                        log.write("Executando " + prontos.get(1).nomeTeste + "\n");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else{
                    try {
                        log.write(prontos.get(0).nomeTeste + " terminado. X=" + prontos.get(0).X + ". Y=" + prontos.get(0).Y + "\n");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }

                finalizados.add(finalizados.size(), prontos.get(0));
                prontos.remove(0);
            }
            
            //System.out.println("Prontos: " + prontos);
            //System.out.println("Tabela: " + tp.tabela);
            //System.out.println("Bloqueados: " + bloqueados);
        }

        float soma_inter = 0;
        float media_inter;
        for(int i = 0; i < finalizados.size(); i++){
            soma_inter += finalizados.get(i).interrupcoes;
        }
        media_inter = soma_inter / finalizados.size();

        float soma_quantum = 0;
        float media_quantum;
        for(int i = 0; i < instucoes_quantum.size(); i++){
            soma_quantum += instucoes_quantum.get(i);
        }
        media_quantum = soma_quantum / instucoes_quantum.size();

        try {
            log.write("MEDIA DE TROCAS: " + media_inter + "\n");
            log.write("MEDIA DE INSTRUCOES: " + media_quantum + "\n");;
            log.write("QUANTUM: " + quantum);
            log.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
