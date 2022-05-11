package br.com.unicuritiba.minhapokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import br.com.unicuritiba.minhapokedex.adapter.PokeAdapter;
import br.com.unicuritiba.minhapokedex.model.Pokemon;
import br.com.unicuritiba.minhapokedex.network.PokeAPI;

public class PokeListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPokemons = findViewById(R.id.recycler_pokemon);
        recyclerViewPokemons.setLayoutManager(new GridLayoutManager(this, 3));

        new PokeAPI().getPokemons(new PokeAPI.PokemonListener() {
            @Override
            public void onPokemonMapperFinish(ArrayList<Pokemon> pokemons) {
                PokeAdapter pokeAdapter = new PokeAdapter(pokemons, new PokeAdapter.PokeClickListener() {
                    @Override
                    public void onPokemonClick(Pokemon pokemon) {
                    callAdapterDetail(pokemon);


                    }
                });
                recyclerViewPokemons.setAdapter(pokeAdapter);
            }
        });
    }

    private void callAdapterDetail(Pokemon pokemon){
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra("pokemon", pokemon);
        startActivity(intent);
    }
}

