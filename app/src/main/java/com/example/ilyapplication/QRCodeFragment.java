package com.example.ilyapplication;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class QRCodeFragment extends Fragment implements View.OnClickListener {

    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    int i = 0;

    public QRCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_q_r_code, container, false);

        qrCodeIV = view.findViewById(R.id.idIVQrcode);
        dataEdt = view.findViewById(R.id.idEdt);
        generateQrBtn = view.findViewById(R.id.idBtnGenerateQR);

        generateQrBtn.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(dataEdt.getText().toString())) {

            // if the edittext inputs are empty then execute
            // this method showing a toast message.
            Toast.makeText(getActivity(), "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
        } else {
            // below line is for getting
            // the windowmanager service.
            WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

            // initializing a variable for default display.
            Display display = manager.getDefaultDisplay();

            // creating a variable for point which
            // is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);

            // getting width and
            // height of a point
            int width = point.x;
            int height = point.y;

            // generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen);
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                qrCodeIV.setImageBitmap(bitmap);

            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        }
    }

}