package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject sandwich = new JSONObject(json);

            // fetch name, mainName

            JSONObject name = sandwich.getJSONObject( "name");
            String mainName = name.getString( "mainName");

            JSONArray alsoKnownAs = name.getJSONArray( "alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();

            for (int i=0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }


            String placeOfOrigin = sandwich.getString("placeOfOrigin");
            String description = sandwich.getString("description");
            String image = sandwich.getString("image");


            JSONArray ingredients = sandwich.getJSONArray( "ingredients");
            List<String> ingredientsList = new ArrayList<>();

            for (int i = 0; i<ingredients.length(); i++ ) {
                ingredientsList.add( ingredients.getString(i));
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;

        }
    }
}
