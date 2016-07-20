package quangdung.quanlynhansu_2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class addContact extends AppCompatActivity {

    ImageView imgChange;
    Button btnSubmit;
    String filePath;
    EditText edtEditName, edtEditAge, edtEditAddress, edtEditPhone, edtEditEmail;
    private static final int SELECTED_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        edtEditName = (EditText) findViewById(R.id.edtEditName);

        edtEditAge = (EditText) findViewById(R.id.edtEditAge);

        edtEditAddress = (EditText) findViewById(R.id.edtEditAddress);

        edtEditEmail = (EditText) findViewById(R.id.edtEditEmail);

        edtEditPhone = (EditText) findViewById(R.id.edtEditPhone);

        imgChange = (ImageView) findViewById(R.id.imgChange);

        imgChange.setOnClickListener(onclick_change);

        btnSubmit.setOnClickListener(onclick_submit);

    }

    private View.OnClickListener onclick_change = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, SELECTED_PICTURE);
        }
    };

    private View.OnClickListener onclick_submit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setName(edtEditName.getText().toString());
            contactEntity.setAddress(edtEditAddress.getText().toString());
            contactEntity.setPhone(edtEditPhone.getText().toString());
            contactEntity.setAge(edtEditAge.getText().toString());
            contactEntity.setEmail(edtEditEmail.getText().toString());

            contactEntity.setImage(filePath);

            mySqlDatabase db = new mySqlDatabase(addContact.this);

            if (contactEntity.getName().length() >= 1
                    & contactEntity.getPhone().length() >= 1
                    & contactEntity.getAddress().length() >= 1
                    & contactEntity.getAge().length() >= 1
                    & contactEntity.getEmail().length() >= 1) {
                if (db.insertContact(contactEntity)) {
                    Toast.makeText(addContact.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(addContact.this, "No, Please Again", Toast.LENGTH_SHORT).show();
                }
                Intent back = new Intent();
                setResult(100, back);
                finish();
            } else{
                Toast.makeText(addContact.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
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
