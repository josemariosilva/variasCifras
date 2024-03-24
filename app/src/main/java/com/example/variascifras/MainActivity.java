package com.example.variascifras;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> novoArray  = new ArrayList<>();
    String palavra;
    int valor;
    String guardaHino;

    Cifras cifra = new Cifras();
    ArrayList<String> listaGuardaTexto = new ArrayList<>();

    SearchView searchView;

    String [] listviewitems = new String []{
            " ","A tua palavra escondi","Ao Unico","Alto Preço","Arvore Cortada","Adorador Por Excelência","Bondade de Deus","Caminho no Deserto","Corpo e Familia","Deserto","Ele Exaltado","Deus esta Aqui","Emaus","Nao ha Deus Maior","Estou Contigo", "Fala Comigo","Feliz serás","Porque Ele Vive",
            "Rompendo em Fe","Vaso Novo","Os que confiam no Senhor","Bom Estarmos Aqui","Em espirito em verdade",
            "Venho Senhor minha vida Oferecer","Restituicao","Tua Presenca","Jamais deixarei voce","Um Dia Tudo Irá Findar","Nada Esta Perdido","Vem, Esta É a Hora","Abraça-me","Me Derramar","Me Refaz","456 - O Estandarte","Pra Sempre","Sonda-me","Tua casa","Ousado amor","Ruja O leão","Águas Purificadoras","Tesouro","Eu Te Busco","Atos 2","Me Atraiu","Jesus Filho de Deus","Tudo haver com Ele", "Transparencia","Nasci para te Adorar","A Mão de Deus","Pai do Ceu","Essencia da Adoração","Deus Forte","Primeira Essencia","What A Beautiful Name","Eu escolho Deus","Mesmo sem Entender","Se eu me humilhar","Alfa e Omega","É tua Graça","Grande é o Senhor",
            "Quão Formoso és", "É tua Graça","Primeira Essência","Eu so quero adorar Dm", "Alem da Medicina em D","Santidade","Tua Graça me Basta","Te Louvarei","Se o Sol se Por","Tesouro C9","Pequeninos","Vendavais","Santidade", "Rompendo em Fé (F)","Quebrantado","Pra sempre","Nada além do Sangue","Jesus em tua Presença","Imagine - Cassiane","Ruja o Leão","Visitante seja Bem Vindo","Deus de Promesas","Sobre as Águas","Vem Andar Comigo","Entrega","Primeiro amor", "Bondade de Deus","A casa é sua","Bonda de Deus (A)","O Escudo","Em teus braços","Visitantes Sejam Bem Vindos","Ousado Amor","Mil Graus","Vou te Alegrar","Vitoria no Deserto","Alto Preço","O quão lindo esse nome é","Tremenda Graça G","Tremenda Graça A","Es Tú Unica Razão"};
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> selectedItems = new ArrayList<>();
    private final ArrayList<String> listviewitem  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Button confirmButton = findViewById(R.id.confirmButton);
        Button limparSelecaoButton = findViewById(R.id.limparSelecaoButton);
        searchView= findViewById(R.id.search_bar);

        // Configure a lista com os itens

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listviewitems);
        listView.setAdapter(adapter);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectSelectedItems();
            }
        });

        popular();
        popularHinos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtenha o nome do item selecionado
                String selectedItem = listviewitems[position];
                // Exiba um Toast apenas quando o usuário selecionar um item
                String toastMessage = "Posição: " + position + ", Nome: " + selectedItem;

                juntaTexto(cifra.cifrasLouvores[position]+'\n');

                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();

                //Toast.makeText(getApplicationContext(), "Carregando: "+novoArray.get(position).toString(), Toast.LENGTH_SHORT).show();
                String Templistview = listviewitem.get(position).toString()+position+"";
                System.out.println("Palavra: "+palavra);
                System.out.println("Posicao: "+position+" "+novoArray.get(position));
                //System.out.println("NO array nomes: "+listviewitem.get(Integer.parseInt(novoArray.get(position))));
                System.out.println("valor arrayprincipal:"+listviewitem.get(position));
                System.out.println("valor novo array:"+novoArray.get(position));
                guardaHino = novoArray.get(position);
                System.out.println("Louvor guardado: "+guardaHino);
                System.out.println("Qual a posicao no arrayPrincipal: "+listviewitem.indexOf(guardaHino));
                //metodo abaixo pega o valor filtrado e junta ao valores já existentes
                juntaTexto(cifra.cifrasLouvores[listviewitem.indexOf(guardaHino)]+'\n');

                 //para mostrar o que tem la na cifra
                //System.out.println("Tem isso aqui: "+cifra.cifrasLouvores[listviewitem.indexOf(guardaHino)]);

                valor = listviewitem.indexOf(guardaHino);
                if(listviewitem.indexOf(guardaHino) < 0 ){

                    for(int t=0; listviewitems.length > t; t++){
                        if(listviewitems[t].equals(guardaHino)){
                            valor = t;
                            System.out.println("Achou a posicao: "+t);

                            break;
                        }else{
                            System.out.println("nao encontrou");
                        }

                    }
                }

                if (!listView.isItemChecked(position)) {
                    //Eu quero q ele pegue o texto que esta na cifra e REMOVA da lista GuardarTexto
                    listaGuardaTexto.remove(cifra.cifrasLouvores[listviewitem.indexOf(guardaHino)]+'\n');
                    System.out.println(listaGuardaTexto.toString());

                }

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                MainActivity.this.adapter.getFilter().filter(s);
                capturarValores(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String b) {
                MainActivity.this.adapter.getFilter().filter(b);
                palavra = b;
                capturarValores(b);
                return false;
            }
        });

        limparSelecaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Desmarcar todos os itens no ListView
                listView.clearChoices();
                listView.requestLayout();

                // Limpar a lista de itens selecionados
                selectedItems.clear();
                // Limpe a lista de itens guardados antes de coletar novamente
                listaGuardaTexto.clear();
            }
        });


    }





    private void collectSelectedItems() {

        // Recolhe os itens selecionados
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < listView.getCount(); i++) {
            if (checked.get(i)) {
                selectedItems.add(listView.getItemAtPosition(i).toString());
            }
        }

        // Inicie a próxima atividade e passe os itens selecionados
        Intent intent = new Intent(this, DisplaySelectedItemsActivity.class);
        // intent.putStringArrayListExtra("selectedItems", selectedItems+'\n'+guardaTexto);
        intent.putStringArrayListExtra("selectedItems", listaGuardaTexto);
        startActivity(intent);


    }

    private void juntaTexto(String novo){
        // Verifica se o valor já existe na lista antes de adicionar
        if (!listaGuardaTexto.contains(novo)) {
            listaGuardaTexto.add(novo);
        }
    }

    public void capturarValores(String texto){
        novoArray.clear();
        novoArray = new ArrayList<>();
        for(int i=0; listviewitems.length > i;i++){
            if(listviewitems[i].toLowerCase().contains(texto)){
                novoArray.add(listviewitems[i]);
            }
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, novoArray);
        listView.setAdapter(adapter);

    }


    private void popular(){
        for(int i=0; listviewitems.length > i;i++)
        {
            novoArray.add(listviewitems[i]);
        }
    }
    void popularHinos() {
        for(int a = 0; listviewitems.length > a; a++ ){
            listviewitem.add(listviewitems[a]);
        }
    }
}
