package quangdung.quanlynhansu_2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ImageReader;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class editContact extends AppCompatActivity {

    Button btnSubmit, btnDelete;
    ImageView imgChange;
    EditText edtEditName, edtEditAge, edtEditAddress, edtEditPhone, edtEditEmail;
    ContactEntity contactEntity;
    private static final int SELECTED_PICTURE = 1;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnDelete = (Button) findViewById(R.id.btnDelete);

        edtEditName = (EditText) findViewById(R.id.edtEditName);

        edtEditAge = (EditText) findViewById(R.id.edtEditAge);

        edtEditAddress = (EditText) findViewById(R.id.edtEditAddress);

        edtEditEmail = (EditText) findViewById(R.id.edtEditEmail);

        edtEditPhone = (EditText) findViewById(R.id.edtEditPhone);

        imgChange = (ImageView) findViewById(R.id.imgChange);

        imgChange.setOnClickListener(onclick_change);

        btnSubmit.setOnClickListener(onclick_submit);

        btnDelete.setOnClickListener(onclick_delete);

        contactEntity = (ContactEntity) getIntent().getExtras().getSerializable("CONTACT");

        getSupportActionBar().setTitle(contactEntity.getName());
        edtEditName.setText(contactEntity.getName());
        edtEditPhone.setText(contactEntity.getPhone());
        edtEditEmail.setText(contactEntity.getEmail());
        edtEditAddress.setText(contactEntity.getAddress());
        edtEditAge.setText(contactEntity.getAge());
        filePath = contactEntity.getImage();
        if (contactEntity.getImage() == null) {
            imgChange.setImageResource(R.mipmap.ic_launcher);
        } else {
            imgChange.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(filePath));
        }


        btnSubmit.setOnClickListener(onclick_submit);
    }

    private View.OnClickListener onclick_change = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, SELECTED_PICTURE);
        }
    };

    private View.OnClickListener onclick_delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mySqlDatabase db = new mySqlDatabase(editContact.this);
            if (db.deleteContactByID(contactEntity.getId())) {
                Toast.makeText(editContact.this, "Delete Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(editContact.this, "No, Please Again", Toast.LENGTH_SHORT).show();
            }

            Intent back = new Intent();
            setResult(300, back);
            finish();

        }
    };

    private View.OnClickListener onclick_submit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            contactEntity.setName(edtEditName.getText().toString());
            contactEntity.setAddress(edtEditAddress.getText().toString());
            contactEntity.setPhone(edtEditPhone.getText().toString());
            contactEntity.setAge(edtEditAge.getText().toString());
            contactEntity.setEmail(edtEditEmail.getText().toString());
            contactEntity.setImage(filePath);

            mySqlDatabase db = new mySqlDatabase(editContact.this);

            if (contactEntity.getName().length() >= 1
                    && contactEntity.getPhone().length() >= 1
                    && contactEntity.getAddress().length() >= 1
                    && contactEntity.getAge().length() >= 1
                    && contactEntity.getEmail().length() >= 1) {

                if (db.editContact(contactEntity)) {
                    Toast.makeText(editContact.this, "Edit Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(editContact.this, "No, Please Again", Toast.LENGTH_SHORT).show();
                }
                Intent back = new Intent();
                setResult(200, back);
                finish();
            }
            else {
                Toast.makeText(editContact.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
            }



        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cs = getContentResolver().query(uri, projection, null, null, null);
                    cs.moveToFirst();
                    int columnIndex = cs.getColumnIndex(projection[0]);
                    filePath = cs.getString(columnIndex);
                    cs.close();
                    imgChange.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(filePath));

                }
                break;
        }

    }
}
