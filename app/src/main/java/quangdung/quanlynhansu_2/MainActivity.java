package quangdung.quanlynhansu_2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvData;
    ArrayList<ContactEntity> listData;
    ContactAdapter adapter;
    ImageView btnAddNew;
    private static final int RESULT_LOAD_IMAGE = 1;
    ContactEntity contactEntity;
    private String picturePath;
    mySqlDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Danh sách nhân sự");

        createDatabase();

        getData();

        if (listData.size()==0)
        {
            Toast.makeText(MainActivity.this, "Click (+) de them nhan vien", Toast.LENGTH_SHORT).show();
        }


        lvData = (ListView) findViewById(R.id.lvData);
        btnAddNew = (ImageView) findViewById(R.id.btnAddNew);

        adapter = new ContactAdapter(listData, MainActivity.this);
        lvData.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnAddNew.setOnClickListener(new_contact);

        lvData.setOnItemClickListener(onclick_edit);
    }

    private View.OnClickListener new_contact = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, addContact.class);

            startActivityForResult(intent, 1);
        }
    };

    private AdapterView.OnItemClickListener onclick_edit = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ContactEntity currentContact = adapter.getItem(position);

            Intent editContactIntent = new Intent(MainActivity.this,
                    editContact.class);
            editContactIntent.putExtra("CONTACT", currentContact);

            startActivityForResult(editContactIntent, 2);


        }
    };

    public void createDatabase() {
        db = new mySqlDatabase(this);

        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        listData = db.getAllContact();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null)
        {
            listData = db.getAllContact();

            adapter.setListData(listData);

            adapter.notifyDataSetChanged();
        }



    }
}
