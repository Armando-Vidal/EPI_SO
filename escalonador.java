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
    private tabela_processos tabela = new tabela_processos(); //variável para a tabela de processos ativos

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
                tabela.addBCP(leitor.blocos.get(i)); //ads o bcp na tabela de processos (somente os com estado prontos)
                ultimo_pronto++;
            } else if(leitor.blocos.get(i).estadoProcesso == "Bloqueado"){
                bloquados.add((ultimo_bloqueado), leitor.blocos.get(i)); //add bcp em bloqueados em um novo índice (seguinte ao último)
                ultimo_bloqueado++;
            }
        }
    }

    //main de testes
    public static void main(String args[]){
        escalonador esc = new escalonador();

        System.out.println(esc.prontos);
        System.out.println(esc.quantum);
        System.out.println(esc.tabela.tabela);
    }
}
