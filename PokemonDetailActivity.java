package br.com.unicuritiba.minhapokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.unicuritiba.minhapokedex.model.Pokemon;
import br.com.unicuritiba.minhapokedex.network.PokeAPI;

public class PokemonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        ImageView imageViewPokeDetail = findViewById(R.id.image_view_pokemon_detail);
        TextView textViewPokeName = findViewById(R.id.text_view_poke_detail);
        TextView textViewPokeAltura = findViewById(R.id.altura_poke_detail);
        TextView textViewPokePeso = findViewById(R.id.peso_poke_detail);
        TextView textViewPokeTipo = findViewById(R.id.tipo_poke_detail);
        TextView textViewPokeMove = findViewById(R.id.move_poke_detail);
        TextView TextViewPokeAbility = findViewById(R.id.ability_poke_detail);
        TextView textViewPokeStats = findViewById(R.id.stats_poke_detail);

        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");
        textViewPokeName.setText(pokemon.getName());
        Picasso.get()
                .load(pokemon.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageViewPokeDetail);

        new PokeAPI().getPokemonDetail(pokemon.getId(), new PokeAPI.PokeDetailListener() {
            @Override
            public void onPokemonMapperDetailFinish(Pokemon pokemon) {
                textViewPokeAltura.setText(pokemon.getHeight());
                textViewPokePeso.setText(pokemon.getWeight());
                String pokeType = "";
                for (int i = 0; i < pokemon.getTypes().size();i++){
                    pokeType = pokeType + pokemon.getTypes().get(i) + " | ";
                }
                textViewPokeTipo.setText(pokeType);
                String pokeMoves = "";
                for (int i = 0; i < pokemon.getMoves().size();i++){
                    pokeMoves = pokeMoves + (i + 1) + " " + pokemon.getMoves().get(i) + "\n";
                }
                textViewPokeMove.setText("moves:\n" + pokeMoves);

                String pokeAbility = "";
                for (int i = 0; i < pokemon.getAbility().size(); i++){
                    pokeAbility = pokeAbility + pokemon.getAbility().get(i) + "\n";
                }
                TextViewPokeAbility.setText(pokeAbility);

                String pokeStats = "";

                for (int i = 0; i < pokemon.getStats().size(); i++){
                    pokeStats = pokeStats + pokemon.getStats().get(i) + " " +pokemon.getStatsBase().get(i) + "\n";
                }
                textViewPokeStats.setText(pokeStats);





            }
        });

    }




}