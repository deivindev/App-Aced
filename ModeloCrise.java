package etec.com.br.victor.basetcc;

public class ModeloCrise {


    public String getdataComeco() {
        return dataComeco;
    }

    public void setdataComeco(String dataComeco) {
        this.dataComeco = dataComeco;
    }
    private ModeloCrise(){}

    private ModeloCrise(String dataComeco){

        this.dataComeco = dataComeco;

    }

    private String dataComeco;
}
