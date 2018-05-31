package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView also_known_tv_label,also_known_tv,ingredients_tv,origin_tv,description_tv;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //alsoKnownAs

        also_known_tv_label = (TextView) findViewById( R.id.also_known_tv);
        also_known_tv = (TextView) findViewById( R.id.also_known_tv);

        if (sandwich.getAlsoKnownAs() !=null && !sandwich.getAlsoKnownAs().isEmpty()){
            for (int i =0; i<sandwich.getAlsoKnownAs().size(); i++) {
                also_known_tv.append(sandwich.getAlsoKnownAs().get(i));
            }
        } else {
            also_known_tv.setVisibility( View.GONE);
            also_known_tv_label.setVisibility(View.GONE);
        }

        // get ingredients

        ingredients_tv = (TextView) findViewById(R.id.ingredients_tv);
        if (sandwich.getIngredients() !=null && !sandwich.getIngredients().isEmpty()){
            for ( int i =0; i<sandwich.getIngredients().size(); i++) {
                ingredients_tv.append(sandwich.getIngredients().get(i) + "\n");
            }
        } else {
            ingredients_tv.setVisibility( View.GONE);

        }

        //description

        description_tv = (TextView) findViewById( R.id.description_tv);
        description_tv.setText(sandwich.getDescription());

        //placeOfOrigin

        origin_tv = (TextView) findViewById(R.id.origin_tv);

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            origin_tv.setText( sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setText( "nothing");
        }



    }
}
