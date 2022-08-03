package sg.edu.rp.c346.id21024611.myndp_songs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {
    EditText etId;
    EditText etTitle;
    EditText etSinger;
    EditText etYear;
    RadioGroup rgStars;
    Button btnUpdate;
    Button btnDelete;
    Button btnCancel;
    Song data;
    RadioButton rbSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etId = findViewById(R.id.editTextId);
        etTitle = findViewById(R.id.editTextTitle);
        etSinger = findViewById(R.id.editTextSingers);
        etYear = findViewById(R.id.editTextYear);
        rgStars = findViewById(R.id.rgStars);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("songData");

        Intent iBack = new Intent(EditActivity.this,
                ListActivity.class);

        etId.setText(String.valueOf(data.get_id()));
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(String.valueOf(data.getYear()));

        for (int j = 0; j < rgStars.getChildCount(); j++){
            RadioButton btnSelected = (RadioButton)rgStars.getChildAt(j);
            if (Integer.parseInt(btnSelected.getText().toString()) == data.getStar()){
                rgStars.check(btnSelected.getId());
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                rbSelected = findViewById(rgStars.getCheckedRadioButtonId());
                int stars = Integer.parseInt(rbSelected.getText().toString());
                data.setStars(stars);

                dbh.updateSong(data);
                dbh.close();
                startActivity(iBack);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.get_id());
                startActivity(iBack);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iBack);
            }
        });

    }
}
