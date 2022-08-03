package sg.edu.rp.c346.id21024611.myndp_songs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ToggleButton btnStars;
    Spinner spnYear;
    ListView lv;
    ArrayList <Song> al, alFilter, alStarFilter;
    CustomAdapter aa;
    //    ArrayAdapter <Song> aa;
    ArrayList <String> alYears;
    ArrayAdapter <String> aaYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnStars = findViewById(R.id.toggleButtonStars);
        spnYear = findViewById(R.id.spinnerYear);
        lv = findViewById(R.id.listView);

        al = new ArrayList<Song>();
        alFilter = new ArrayList<Song>();
        alStarFilter = new ArrayList<Song>();

        aa = new CustomAdapter(this,
                R.layout.row, al);
        lv.setAdapter(aa);

        DBHelper dbh = new DBHelper(ListActivity.this);
        al.addAll(dbh.getAllSongs());

        // Year Filter
        alYears = dbh.getAllYears();
        alYears.add(0,"Filter by year");

        aaYears = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alYears);

        spnYear.setAdapter(aaYears);


        btnStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnStars.isChecked()) {
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getStar() != 5) {
                            alStarFilter.add(al.get(i));
                        }
                    }
                    al.removeAll(alStarFilter);
                    if(spnYear.getSelectedItemPosition()!=0){
                        for (int j = 0; j < al.size(); j++) {
                            if (al.get(j).getYear() != Integer.parseInt(spnYear.getSelectedItem().toString())) {
                                alFilter.add(al.get(j));

                            }
                        } al.removeAll(alFilter);
                    }
                } else {
                    al.clear();
                    alStarFilter.clear();
                    al.addAll(dbh.getAllSongs());
                    if(spnYear.getSelectedItemPosition()!=0) {
                        for (int j = 0; j < al.size(); j++) {
                            if (al.get(j).getYear() != Integer.parseInt(spnYear.getSelectedItem().toString())) {
                                alFilter.add(al.get(j));

                            }
                        }
                        al.removeAll(alFilter);
                    }
                }
                aa.notifyDataSetChanged();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song data = al.get(i);
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                intent.putExtra("songData", data);
                startActivity(intent);

            }
        });

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alFilter.clear();
                al.clear();
                al.addAll(dbh.getAllSongs());
                if (i==0){
                    alYears.set(0, "Filter by year");
                    aaYears.notifyDataSetChanged();
                    al.clear();
                    al.addAll(dbh.getAllSongs());

                } else {
                    alYears.set(0, "View all songs");
                    for (int j = 0; j < al.size(); j++) {
                        if (al.get(j).getYear() != Integer.parseInt(adapterView.getItemAtPosition(i).toString())) {
                            alFilter.add(al.get(j));

                        }
                    }
                    al.removeAll(alFilter);
                    ArrayList<Song> temp = al;
                    if(btnStars.isChecked()){
                        for (int s = 0; s < al.size(); s++) {
                            if (al.get(s).getStar() != 5) {
                                alStarFilter.add(al.get(s));
                            }
                        }
                        al.removeAll(alStarFilter);
                    } else{
                        al = temp;
                    }
                } aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();

        alYears = dbh.getAllYears();
        alYears.add(0,"Filter by year");

        aaYears = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alYears);

        spnYear.setAdapter(aaYears);
        btnStars.setChecked(false);


    }

}