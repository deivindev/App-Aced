package etec.com.br.victor.basetcc;

public class Modelo {
    private String anotacao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private String titulo;

    private Modelo(){}

    private Modelo(String anotacao){

        this.anotacao = anotacao;
        this.titulo = titulo;
    }
    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }


}
