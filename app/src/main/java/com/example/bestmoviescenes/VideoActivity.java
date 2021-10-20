package com.example.bestmoviescenes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoActivity extends AppCompatActivity {

    private String[] movieTitle;
    private int[] videoIcon;
    private String[] videoText;
    private String[] videoMainWords;
    private boolean isFavourite;
    private  boolean isDataBaseFilled = false;
    SharedPreferences sharedPref;

    ListView listViewMoviesQuotes;
    List<MovieDb> listItemsMovieQuotes = new ArrayList<>();
    CustomAdapterMovieQuotes customAdapterMovieQuotes;
    EditText editTextSearchBarViedo;
    VideoView videoView;
    MediaPlayer mediaPlayer;
    MovieDBHandler movieDBHandler;
    MovieDb movieDb;
    Boolean favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editTextSearchBarViedo = findViewById(R.id.search_bar_viedo);
        movieDBHandler = new MovieDBHandler(this);
        listViewMoviesQuotes = findViewById(R.id.listviewAudio);
        videoView = (VideoView) findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        movieTitle = new String[]{"Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra","Asterix i Obelix Misja Kleopatra",
                "Shrek"};
        videoIcon = new int[]{R.drawable.numernabis2,R.drawable.numernabis, R.drawable.obelix, R.drawable.rudobrody,R.drawable.numernabis, R.drawable.skryba, R.drawable.idea, R.drawable.harimatis, R.drawable.numernabis3,
                R.drawable.osiol};
        videoText = new String[]{"Gdzie ten mały?","Normalnie ulga, że weź", "Miałem nic nie mówić to szczekam sobie","Ostatnim razem mieliśmy pecha, trafili się nam galowie...","Nie to nie są niewolnicy...","Jak to jest być skrybą?","Będzie tego! Będzie tego!", "Widać mnie, nie widać..", "Chyba oberwałem",
                "to mój ogon jest, urwiesz mi i co"};
        videoMainWords = new String[]{" numernabis zeżarły go", "o w morde nie wiedziałem ze bedzie to takie proste Normalnie ulga że weź ze wez numernabis", "obelix miałem nic nie mówić to szczekam sobie", "rudobrody ostatnim razem mielismy pecha trafili nam sie galowie jak zwykle na dopingu dwóch ich było więc mieli zdecydowaną przewagę opóścilismy statek w zorganizowanym pośpiechu pospiechu oposcilismy", "numernabis to wysokiej klasy fachowcy zatrudnieni o umowę na dzieło", "skryba moim zdaniem to nie ma tak czy dobrze czy niedobrze gdybym miał powiedzieć co cenię najbardziej powiedziałbym że ludzi", "idea to nie są warunki dla ludzi pracy","harimatis jak patrze tak to mnie widac ale jak tak to nie  ", "numernabis a nie to nie ja"
        ,"osiol osioł"};



        if(!isDataBaseFilled && !preferenceFileExist("VideoActivity")){
            fillDatabase();
        }


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String title = intent.getStringExtra("MovieTitle");
            favourite = intent.getBooleanExtra("Favourite",false);
            if(favourite){
                listItemsMovieQuotes = movieDBHandler.getFavouriteMovies();
                customAdapterMovieQuotes = new CustomAdapterMovieQuotes(listItemsMovieQuotes,this);
                listViewMoviesQuotes.setAdapter(customAdapterMovieQuotes);

            }else {
                listItemsMovieQuotes = movieDBHandler.getMovies(title);
                customAdapterMovieQuotes = new CustomAdapterMovieQuotes(listItemsMovieQuotes, this);
                listViewMoviesQuotes.setAdapter(customAdapterMovieQuotes);
            }
        }
        editTextSearchBarViedo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customAdapterMovieQuotes.getFilter().filter(charSequence);

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {mediaPlayer.stop();}

    }
    @Override
    public void onBackPressed() {
        if(videoView.isPlaying()){
            ClearSearchBar();
            changeToolBarVisibility();
            videoView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else super.onBackPressed();

    }

    private void playMp4(String nameOfFile){
        int resourceId = this.getResources().getIdentifier(nameOfFile, "raw", this.getPackageName());
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + resourceId);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView.setVisibility(View.VISIBLE);
        changeToolBarVisibility();
        videoView.start();
    }


    public class CustomAdapterMovieQuotes extends BaseAdapter implements Filterable {

        private  List<MovieDb> itemsModelList;
        private  List<MovieDb> itemsModelListFiltered;
        private Context context;

        public CustomAdapterMovieQuotes(List<MovieDb> itemsModelList, Context context) {
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final View convertedView = getLayoutInflater().inflate(R.layout.list_audio,null);

            CircleImageView circleImageView = convertedView.findViewById(R.id.characterImg);
            final TextView textView = convertedView.findViewById(R.id.audioTitle);
            final ToggleButton toggleButton = convertedView.findViewById(R.id.favouriteButton);

            circleImageView.setImageResource(itemsModelListFiltered.get(i).getVideoIcon());
            textView.setText(itemsModelListFiltered.get(i).getVideoText());
            toggleButton.setBackgroundResource(itemsModelListFiltered.get(i).getFavaouriteIcon());

                    convertedView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                    String text = textView.getText().toString();
                    String audioTitle = text.replaceAll("\\s+", "").replaceAll(",+", "").replaceAll("\\?+", "").replaceAll("\\.+", "").replaceAll("!+", "")
                            .replaceAll("ó+", "o").replaceAll("ł+", "l").replaceAll("ą+", "a").replaceAll("ć+", "c")
                            .replaceAll("ń+", "n").replaceAll("ś+", "s").replaceAll("ź+", "z").replaceAll("ż+", "z")
                            .replaceAll("ę+", "e").toLowerCase();
                    videoView.requestFocus();
                    closeKeyboard();
                    playMp4(audioTitle);
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mp)
                        {
                            changeToolBarVisibility();
                            videoView.setVisibility(View.GONE);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                    });
                }

            });

            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = itemsModelListFiltered.get(i).getVideoText();
                    Boolean isFavourite = movieDBHandler.checkIfFavourite(itemsModelListFiltered.get(i).getVideoText());
                if(isFavourite){
                    movieDBHandler.deleteFromFavaourite(text);
                    toggleButton.setBackgroundResource(R.drawable.ic_baseline_shadow_24);
                    if(favourite)   refreshActivity();
                }
                else{
                    movieDBHandler.addToFavaourite(text);
                    toggleButton.setBackgroundResource(R.drawable.ic_icon_heart_foreground);

                }
                }
            });
            toggleButton.setBackgroundResource(movieDBHandler.getToggleButtonImage(itemsModelListFiltered.get(i).getVideoText()));
           // customAdapterMovieQuotes.notifyDataSetChanged();
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
                        List<MovieDb> resultsData = new ArrayList<>();

                        for(MovieDb moviedb:itemsModelList){
                            if(moviedb.getVideoText().toLowerCase().contains(searchStr) ||moviedb.getVideoMainWords().toLowerCase().contains(searchStr) ){
                                resultsData.add(moviedb);
                            }

                            filterResults.count = resultsData.size();
                            filterResults.values = resultsData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {

                    itemsModelListFiltered = (List<MovieDb>) results.values;
                    customAdapterMovieQuotes.notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

    private void ClearSearchBar(){
        editTextSearchBarViedo.setText("");
    }

    private void closeKeyboard(){
        View view = getCurrentFocus();
        if(view!= null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void changeToolBarVisibility(){
        Toolbar toolbar = findViewById(R.id.my_toolbar_viedo);
        ListView listView = findViewById(R.id.listviewAudio);
        if(toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }
        else{
            toolbar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void fillDatabase(){
        for(int i = 0; i<movieTitle.length; i++){
            movieDb = new MovieDb(movieTitle[i],videoIcon[i],videoText[i],videoMainWords[i],false, R.drawable.ic_toggle_bg);
            movieDBHandler.addHandler(movieDb);
        }
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        isDataBaseFilled = true;
        editor.putBoolean("isDataBaseFilled",isDataBaseFilled).apply();
        Log.i("DataBase", "uzupełniona");
    }

    public boolean preferenceFileExist(String fileName) {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists();
    }

    private void refreshActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}


