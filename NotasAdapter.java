package etec.com.br.victor.basetcc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolderNotas>{

    private List<Notas> notas_list;
    //construtor usado para inicializar o uso do adapter
    public NotasAdapter(List<Notas> notasList){
        this.notas_list = notasList;
    }

    @NonNull
    @Override
    public NotasAdapter.ViewHolderNotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //não posso deixar true porque ja criei um layout para meu exemplo
        View view = layoutInflater.inflate(R.layout.anotacoes_xml,parent,false);
        ViewHolderNotas holderNotas = new ViewHolderNotas(view);
        return holderNotas;

    }

    @Override
    public void onBindViewHolder(@NonNull NotasAdapter.ViewHolderNotas holder, int position) {

        //TÁ PUXANDO A CLASSE "ANOTACOES" COM GET E SET LÁ DENTRO
        if (notas_list!= null && notas_list.size()>0){
            //Anotacoes anotacoes = notas_list.get(position);
            //holder.anotacao_atual.setText(anotacoes.getAnotacao());

        }

    }

    @Override
    public int getItemCount() {
        return notas_list.size();
    }

    public class ViewHolderNotas extends RecyclerView.ViewHolder{

        public TextView anotacao_atual;

        public ViewHolderNotas(@NonNull View itemView) {
            super(itemView);
            anotacao_atual = itemView.findViewById(R.id.tituloNotas);


        }
    }
}
