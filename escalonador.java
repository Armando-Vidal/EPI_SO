import java.util.ArrayList;
import java.util.List;

public class escalonador { //cordena o processo de escalonamento
    int quantum;
	//private String log;

    private List<bcp> prontos = new ArrayList<>(); //lista de bcps prontos
    private List<bcp> bloquados = new ArrayList<>(); //lista de bcps bloqueados
    private int ultimo_pronto = 0; //index que vai salvar o index disponível de prontos
    private int ultimo_bloqueado = 0; //index que vai salvar o index disponível de bloqueados
    private le_diretorio leitor = new le_diretorio(); //variável que fará a leitura de diretórios
    private tabela_processos tp = new tabela_processos(); //variável para a tabela de processos ativos

    //construtor
    public escalonador(){
        this.leitor.leDiretorio(); //função que já lê o diretório programas na inicialização do objeto
        this.quantum = leitor.quantum; //atualização do quantum
        atualizaProntosBloqueados(); //criação de uma lista de prontos e bloqueados
    }

    //função para atualizar a lista de prontos e bloquados (a partir de um parâmetro do bcp)
    public void atualizaProntosBloqueados(){
        //passa pela lista "blocos" e divide entre prontos e bloqueados
        for(int i = 0; i < leitor.blocos.size(); i++){
            if(leitor.blocos.get(i).estadoProcesso == "Pronto"){
                prontos.add((ultimo_pronto), leitor.blocos.get(i)); //add bcp em prontos em um novo índice (seguinte ao último)
                tp.addBCP(leitor.blocos.get(i)); //ads o bcp na tabela de processos (somente os com estado prontos)
                ultimo_pronto++;
            } else if(leitor.blocos.get(i).estadoProcesso == "Bloqueado"){
                bloquados.add((ultimo_bloqueado), leitor.blocos.get(i)); //add bcp em bloqueados em um novo índice (seguinte ao último)
                ultimo_bloqueado++;
            }
        }
    }

    public void diminuitempodeEspera(){
        int i = 0;
        while(i < bloquados.size()){
            //System.out.println("Tempo de espera de " + bloquados.get(i) + ": " + bloquados.get(i).tempodeEspera);
            bloquados.get(i).tempodeEspera--;
            if(bloquados.get(i).tempodeEspera == 0){
                prontos.add(prontos.size(), bloquados.get(i));
                tp.addBCP(bloquados.get(i));
                bloquados.remove(i);
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
                prontos.get(0).contadorQuantum = 0; //reseta quantum para quando esse arquivo rodar de novo
                prontos.add(prontos.size(), prontos.get(0));
                prontos.remove(0);

                diminuitempodeEspera();
            //caso de E/S -> (por enquanto) remove da lista de prontos e tabela e coloca na lista de bloqueados (mas não sai de lá nunca)
            } else if(prontos.get(0).estadoProcesso == "Bloqueado"){
                diminuitempodeEspera();
                bloquados.add(bloquados.size(), prontos.get(0));
                tp.removeBCP(0);
                prontos.remove(0);
                bloquados.get(bloquados.size() - 1).contadorQuantum = 0;

                //log de interrupção
            //caso de SAIDA -> remove o arquivo da tabela e de prontos
            } else if(prontos.get(0).finalizado){
                tp.removeBCP(0);
                //log de finalização

                prontos.remove(0);
                diminuitempodeEspera();
            }
            System.out.println("Prontos: " + prontos);
            System.out.println("Tabela: " + tp.tabela);
            System.out.println("Bloqueados: " + bloquados);
        }
    }

    //main de testes
    public static void main(String args[]){
        escalonador esc = new escalonador();
        esc.rodaProgramas();

        System.out.println(esc.prontos);
        System.out.println(esc.bloquados);
    }
}
