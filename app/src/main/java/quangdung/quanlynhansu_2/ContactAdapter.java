package quangdung.quanlynhansu_2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactAdapter extends BaseAdapter {

    ArrayList<ContactEntity> listData;
    LayoutInflater inflater;
    private final int THUMBSIZE = 100;

    public ContactAdapter(ArrayList<ContactEntity> listData, Context context) {
        this.listData = listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<ContactEntity> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ContactEntity> listData) {
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public ContactEntity getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    TextView txtAge, txtAddress, txtName, txtPhone, txtEmail;
    ImageView imgAvt;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactEntity data = (ContactEntity) getItem(position);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_nhansu, null);

       /* txtId = (TextView) convertView.findViewById(R.id.txtId);*/
        txtAge = (TextView) convertView.findViewById(R.id.txtAge);
        txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
        txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
        imgAvt = (ImageView) convertView.findViewById(R.id.imgAvt);


      /*  txtId.setText(data.getId() + "");*/
        txtName.setText(data.getName());
        txtAge.setText(data.getAge());
        txtPhone.setText(data.getPhone());
        txtAddress.setText(data.getAddress());
        txtEmail.setText(data.getEmail());

        imgAvt.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(data.getImage()), THUMBSIZE, THUMBSIZE));

        return convertView;
    }

}

