/*
package etec.com.br.victor.basetcc;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Anotacoes extends RecyclerView.Adapter {

    List<Anotacoes> anotacoes;

    public Anotacoes(List<Anotacoes> anotacoes) {
        this.anotacoes = anotacoes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anotacoes_xml,parent,false);
        ViewHolderClass vhclasss = new ViewHolderClass(view);
        return vhclasss;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      ViewHolderClass vhClass = (ViewHolderClass) holder;
      Anotacoes usuario  = anotacoes.get(position);
      //vhClass.txtNotasMostar.setText(anotacoes.get);

    }

    @Override
    public int getItemCount() {
        return anotacoes.size();
    }

    public class  ViewHolderClass extends RecyclerView.ViewHolder{

        TextView txtNotasMostar;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            txtNotasMostar = itemView.findViewById(R.id.anotacao_agora);
        }
    }
}
*/