package com.example.bestmoviescenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listViewMovieTitles;
    int[] backgroundImageId = {R.drawable.shrek, R.drawable.shrek2, R.drawable.shrek3, R.drawable.shrek4, R.drawable.asterixobelix};
    String movieTitles[] = {"Shrek", "Shrek 2", "Shrek 3 Trzeci", "Shrek 4 forever", "Asterix i Obelix Misja Kleopatra" };
    List<Movie> listItems = new ArrayList<>();
    CustomAdapter customAdapter;
    EditText  editTextSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        listViewMovieTitles = findViewById(R.id.listview);
        editTextSearchBar = findViewById(R.id.search_bar);


        for(int i = 0; i<movieTitles.length; i++){
            Movie movie = new Movie(backgroundImageId[i],movieTitles[i]);
            listItems.add(movie);
        }

        customAdapter = new CustomAdapter(listItems,this);
        listViewMovieTitles.setAdapter(customAdapter);


        editTextSearchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            customAdapter.getFilter().filter(charSequence);

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


public class CustomAdapter extends BaseAdapter implements Filterable {

        private  List<Movie> itemsModelList;
        private  List<Movie> itemsModelListFiltered;
        private Context context;

    public CustomAdapter(List<Movie> itemsModelList, Context context) {
        this.itemsModelList = itemsModelList;
        this.itemsModelListFiltered = itemsModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsModelListFiltered.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertedView = getLayoutInflater().inflate(R.layout.list_item,null);

        ImageView imageView = convertedView.findViewById(R.id.imageViewBackground);
        imageView.setBackgroundResource(itemsModelListFiltered.get(i).getBackgroundImage());

       final String title =  itemsModelListFiltered.get(i).getTitle();
        convertedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("MovieTitle",title );
                startActivity(intent);

            }
        });

        return convertedView;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();

                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = itemsModelList.size();
                    filterResults.values = itemsModelList;
                }else{
                    String searchStr = charSequence.toString().toLowerCase();
                    List<Movie> resultsData = new ArrayList<>();

                    for(Movie movie:itemsModelList){
                        if(movie.getTitle().toLowerCase().contains(searchStr)){
                            resultsData.add(movie);
                        }

                        filterResults.count = resultsData.size();
                        filterResults.values = resultsData;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                itemsModelListFiltered = (List<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

public void favaouriteView(View view){
    Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
    intent.putExtra("Favourite", true);
    startActivity(intent);
    editTextSearchBar.setText("");
}


}


