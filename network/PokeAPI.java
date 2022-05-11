package br.com.unicuritiba.minhapokedex.network;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import br.com.unicuritiba.minhapokedex.model.Pokemon;

public class PokeAPI {


    public void getPokemons(PokemonListener listener) {
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(new ConnectionAsyncTask.ConnectionListener() {
            @Override
            public void onRequestResult(JSONObject object) {

                ArrayList<Pokemon> pokemons = new ArrayList<>();

                JSONArray pokemonResults = null;
                try {

                    pokemonResults = object.getJSONArray("results");
                    for (int i = 0; i < pokemonResults.length(); i++) {
                        JSONObject objectPokemon = pokemonResults.getJSONObject(i);

                        int id = i + 1;
                        String name = objectPokemon.getString("name");
                        String url = objectPokemon.getString("url");
                        //String idUrl = url.replace("https://pokeapi.co/api/v2/pokemon/", "").replace("/", "");
                        String image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";

                        pokemons.add(new Pokemon(id, name, image));
                    }

                } catch (Exception e) {
                }

                listener.onPokemonMapperFinish(pokemons);

            }
        });

        connectionAsyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit=151", "GET");
    }

    public void getPokemonDetail(int id, PokeDetailListener pokeDetailListener) {

        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(new ConnectionAsyncTask.ConnectionListener() {
            @Override
            public void onRequestResult(JSONObject object) {

                Pokemon pokemon = new Pokemon();
                JSONArray pokeDetailsType = null;
                JSONArray pokeDetailMove = null;
                JSONArray pokeDetailAbility = null;
                JSONArray pokeDetailStats = null;
                ArrayList typeResult = new ArrayList();

                try {
                    pokeDetailsType = object.getJSONArray("types");
                    String height = object.get("height").toString();
                    String weight = object.get("weight").toString();
                    for (int i = 0; i < pokeDetailsType.length(); i++) {
                        JSONObject pokemonTypes = pokeDetailsType.getJSONObject(i);
                        JSONObject typeName = pokemonTypes.getJSONObject("type");
                        String nameType = typeName.getString("name");
                        typeResult.add(nameType);
                    }

                    ArrayList moveResult = new ArrayList<>();
                    pokeDetailMove = object.getJSONArray("moves");
                    for (int i = 0; i < pokeDetailMove.length(); i++) {
                        JSONObject pokemonMoves = pokeDetailMove.getJSONObject(i);
                        JSONObject moveName = pokemonMoves.getJSONObject("move");
                        String moveNameString = moveName.getString("name");
                        moveResult.add(moveNameString);
                    }

                    ArrayList abilityResult = new ArrayList();
                    pokeDetailAbility = object.getJSONArray("abilities");
                    for (int i = 0; i < pokeDetailAbility.length(); i++) {
                        JSONObject pokemonAbility = pokeDetailAbility.getJSONObject(i);
                        JSONObject abilityName = pokemonAbility.getJSONObject("ability");
                        String abilityNameString = abilityName.getString("name");
                        abilityResult.add(abilityNameString);
                    }

                    ArrayList statsResult = new ArrayList();
                    ArrayList baseStatsResult = new ArrayList();
                    pokeDetailStats = object.getJSONArray("stats");
                    for (int i = 0; i < pokeDetailStats.length(); i++) {
                        JSONObject pokemonStats = pokeDetailStats.getJSONObject(i);
                        JSONObject statsName = pokemonStats.getJSONObject("stat");
                        JSONObject baseStats = pokeDetailStats.getJSONObject(i);
                        String baseStatsString = baseStats.getString("base_stat");
                        String statsNameString = statsName.getString("name");
                        statsResult.add(statsNameString);
                        baseStatsResult.add(baseStatsString);
                }

                    pokemon.setHeight(height);
                    pokemon.setWeight(weight);
                    pokemon.setTypes(typeResult);
                    pokemon.setMoves(moveResult);
                    pokemon.setAbility(abilityResult);
                    pokemon.setStats(statsResult);
                    pokemon.setStatsBase(baseStatsResult);



                } catch (Exception e) {

                }

                pokeDetailListener.onPokemonMapperDetailFinish(pokemon);

            }
        });
        connectionAsyncTask.execute("https://pokeapi.co/api/v2/pokemon/" + id + "/", "GET");

    }

    public interface PokemonListener {
        void onPokemonMapperFinish(ArrayList<Pokemon> pokemons);
    }

    public interface PokeDetailListener {
        void onPokemonMapperDetailFinish(Pokemon pokemon);
    }

}
