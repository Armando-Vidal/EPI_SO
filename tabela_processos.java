import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class tabela_processos {
    List<bcp> tabela = new ArrayList<>();;
    int index_com;

    public void addBCP(bcp bloco){
        this.tabela.add(bloco);
    }
}
