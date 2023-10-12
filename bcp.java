import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class bcp
{
    private int contadorPrograma, nomeArquivo, tempodeEspera;
    private int X, Y;
	private String estadoProcesso;
    int nomePrograma;
    List<String> comandos = new ArrayList<>(); 
    
    // Tabela de processos;
    // Lista de processos prontos;
    // Lista de processos bloqueados;

	public bcp(List<String> comandos, String nome)
    {
		this.comandos = comandos;
        this.nomePrograma = Integer.parseInt(nome.replace(".txt", ""));
		this.estadoProcesso = "Pronto";
	}

    /*
    //sobrescrevendo a lista de processos
    public String toString()
    {
		return nomePrograma + " - PC: [" + contadorPrograma + "/" + textodoPrograma.size() + "] - Tempo Espera: " + tempodeEspera + " - Estado: " + estadoProcesso + "\n";
	}
    
    //setando os registradores
    public int getRegistradorX()
    {
		return this.registradores[0];
	}

	public int getRegistradorY()
    {
		return this.registradores[1];
	}

	public void setRegistradorX(int x)
    {
		this.registradores[0] = x;
	}

	public void setRegistradorY(int y)
    {
		this.registradores[1] = y;
	}

    //setando o contador
    public int getContador()
    {
		return this.contadorPrograma;
	}

	public void setContador(int valor)
    { 
		this.contadorPrograma = valor;
	}
    
    public void aumentaContador()
    { 
		this.contadorPrograma++;
	}

    //setando o tempo de espera
    public int gettempodeEspera()
    {
        return this.tempodeEspera;
    }
    
    public void settempodeEspera(int tempo)
    { 
        this.tempodeEspera = tempo++;
    }
    
    public void diminuitempodeEspera()
    {
        this.tempodeEspera--;
    }

    //setando o estado do processo
	public void setestadoProcesso(String novoEstado)
    {
		estadoProcesso = novoEstado;
	}

    //pegando nome do programa e do arquivo
	public String getnomePrograma()
    {
		return this.nomePrograma;
	}

	public int getnomeArquivo()
    {
		return nomeArquivo;
	}
     */

}