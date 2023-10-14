import java.util.ArrayList;
import java.util.List;

public class escalonador { //cordena o processo de escalonamento
    int quantum;
	private String log;

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

    public void rodaProgramas(){
        int i = 0;
        while(0 < tp.tabela.size()){
            tp.getBCP(i).fazComando();
            if(tp.getBCP(i).contadorPrograma == quantum){
                System.out.println("Limite de quantum");
                System.out.println("Prontos antes: " + prontos);
                prontos.remove(0);
                prontos.add(prontos.size(), tp.getBCP(i));
                System.out.println("Prontos depois de add: " + prontos);
                i++;
            }
            if(tp.getBCP(i).estadoProcesso == "Bloqueado"){
                System.out.println("Bloqueio");
                System.out.println("Bloquados antes: " + bloquados);
                bloquados.add(bloquados.size(), tp.getBCP(i));
                tp.removeBCP(i);
                prontos.remove(0);
                System.out.println("Bloqueados depois: " + bloquados);

                //log de interrupção
                System.out.println(bloquados.get(bloquados.size() - 1).nomeTeste + " interrompido");
            } else if(tp.getBCP(i).finalizado){
                tp.removeBCP(i);

                //log de finalização
                System.out.println(tp.getBCP(i).nomeTeste + " finalizado");
            }
            System.out.println("Primeiro elemento de prontos: " + prontos.get(0).nomeTeste);
        }
    }

    //main de testes
    public static void main(String args[]){
        escalonador esc = new escalonador();
        esc.rodaProgramas();

        System.out.println(esc.prontos);
        System.out.println(esc.quantum);
        System.out.println(esc.tp.tabela);
    }
}
