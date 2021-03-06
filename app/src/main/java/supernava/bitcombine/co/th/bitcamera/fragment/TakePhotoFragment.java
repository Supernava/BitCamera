package supernava.bitcombine.co.th.bitcamera.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;


import supernava.bitcombine.co.th.bitcamera.R;

public class TakePhotoFragment extends Fragment {

    private String resultQRString;
    private ImageView cameraCImageView, cameraDImageView;
    private Uri cameraCUri, cameraDUri; //สิ่งที่ถ่ายได้
    private File cameraFile, cameraCFile, cameraDFile;
    private String dirString, bitCFileString, bitDFileString;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//      Show View
        showView();

//      createPath
        createPath();

//      Cancel Controller
        cancelController();

//      CameraC Controller
        cameraCController();

//      CameraD Controller
        cameraDController();

//      saveController
        saveController();


    }   // Main Method

    private void createPath() {
        cameraFile = new File(Environment.getExternalStorageDirectory() + "/" + dirString);
        if (!cameraFile.exists()) {
            cameraFile.mkdirs();
        }
    }

    private void cameraDController() {
        cameraDImageView = getView().findViewById(R.id.imvCameraD);
        cameraDImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraDFile = new File(cameraFile, bitDFileString + "D.jpg");
                cameraDUri = Uri.fromFile(cameraDFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraDUri);
                startActivityForResult(intent, 2);

            }   // onClick
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            showPhoto(requestCode);

        } else {
            Toast.makeText(getActivity(), "Please Take Photo", Toast.LENGTH_SHORT).show();
        }


    }   // onActivityResult

    private void showPhoto(int requestCode) {

        try {

            switch (requestCode) {
                case 1:
                    Bitmap rowBitmapC = BitmapFactory.decodeStream(getActivity().getContentResolver()
                            .openInputStream(cameraCUri));
                    Bitmap resizeCBitmap = Bitmap.createScaledBitmap(rowBitmapC, 800, 480,false );
                    cameraCImageView.setImageBitmap(resizeCBitmap);
                    break;
                case 2:
                    Bitmap rowBitmapD = BitmapFactory.decodeStream(getActivity().getContentResolver()
                            .openInputStream(cameraDUri));
                    Bitmap resizeDBitmap = Bitmap.createScaledBitmap(rowBitmapD, 800, 480,false );
                    cameraDImageView.setImageBitmap(resizeDBitmap);
                    break;
            }


        } catch (Exception e) {
            Log.d("31AugV1", "e showPhoto ==> " + e.toString());
        }

    }   //  showPhoto

    private void cameraCController() {

        cameraCImageView = getView().findViewById(R.id.imvCameraC);
        cameraCImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraCFile = new File(cameraFile, bitCFileString + "C.jpg");
                cameraCUri = Uri.fromFile(cameraCFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraCUri);
                startActivityForResult(intent, 1);
            }   // onClick
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_photo, container, false);
        return view;
    }

    public static TakePhotoFragment takePhotoInstance(String resultString) {
        TakePhotoFragment takePhotoFragment = new TakePhotoFragment();
        Bundle bundle = new Bundle(); //สร้างกระดาษทด ค่าที่รับมา เพื่อนำข้อมูลส่งต่อ
        bundle.putString("Result", resultString);
        takePhotoFragment.setArguments(bundle);
        return takePhotoFragment;

    }

    private void showView() {
        resultQRString = getArguments().getString("Result", "Non");

//        รับค่า String ที่ Decode แล้ว
        String QRcode_Convert = "";
        String Tag = "29AugV1", DateTimeIn = "เข้า : ";
        String DateIn = "", TimeIn = "";

//        เช็คข้อมูลตัวแรกเท่ากับเครื่องหมาย | หรือไม่
        if(resultQRString.charAt(0) == '|') {

            int check_FontThai = 0;
//            วน loop เพื่อทำการ Decode Qrcode
            for(int i = 0; i < resultQRString.length(); ++i)
            {

                if(resultQRString.charAt(i) == '!')
                {
                    i++;
                    char CharDecimal = resultQRString.charAt(i);
                    int ValueASCII = (int) CharDecimal;

                    char char_decode = (char)(ValueASCII + 3536);
                    if(ValueASCII == '}') {
                        QRcode_Convert += "ะ";
                    }else {
                        QRcode_Convert += char_decode;
                    }
                }
                else
                {
                    //กรณี QRcode ที่เข้ามาเป็นเครื่องหมาย | ให้คืนค่าว่างกลับไป
                    if(resultQRString.charAt(i) == '|'){
                        QRcode_Convert += "";
                    }else{
                        char CharDecimal = resultQRString.charAt(i);
                        int ValueASCII = (int) CharDecimal;
                        //เช็คว่า เป็นตัวอักษรไทยหรือไม่
                        if(ValueASCII <= 0)
                            check_FontThai=1;

                        if(check_FontThai == 1){
                            QRcode_Convert += resultQRString.charAt(i);
                        }else{
                            char char_decode = (char)(158 - ValueASCII);
                            if(ValueASCII == '>'){
                                //char_decode
                                QRcode_Convert += ">";
                            }else if(ValueASCII == ' '){
                                QRcode_Convert += " ";
                            }else{

                                QRcode_Convert += char_decode;
                            }
                        }
                    }
                }

            }
        }



        if(QRcode_Convert.split("\\$",-1).length-1 == 10){

            String[] data = QRcode_Convert.split("\\$");

            TextView textView = getView().findViewById(R.id.txtResult);
            //textView.setText(data[0] + ", " + data[2]);
            String styledText = "<font color='blue'>"+data[0]+"</font>"+", " + data[2];
            textView.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

            DateIn = data[8].substring(4, 6) + "/" + data[8].substring(2, 4);
            TimeIn = data[9].substring(0, 2) + ":" + data[9].substring(2, 4);
            DateTimeIn += DateIn +" "+TimeIn;

            TextView textView2 = getView().findViewById(R.id.txtResult2);
            textView2.setText(DateTimeIn);

            dirString = data[8]+data[10];
            bitCFileString = data[9]+data[10];
            bitDFileString = data[9]+data[10];

            Log.d("31AugV1", "dirString-----> " + dirString);
            Log.d("31AugV1", "bitFileString-----> " + bitCFileString);


            Log.d(Tag, "Debug QRcode ---->" + QRcode_Convert);
            Log.d(Tag, "Debug[0]---->" + data[0]);
            Log.d(Tag, "Debug[1]---->" + data[1]);
            Log.d(Tag, "Debug[2]---->" + data[2]);
            Log.d(Tag, "Debug[3]---->" + data[3]);
            Log.d(Tag, "Debug[4]---->" + data[4]);
            Log.d(Tag, "Debug[5]---->" + data[5]);
            Log.d(Tag, "Debug[6]---->" + data[6]);
            Log.d(Tag, "Debug[7]---->" + data[7]);
            Log.d(Tag, "Debug[8]---->" + data[8]);
            Log.d(Tag, "Debug[9]---->" + data[9]);
            Log.d(Tag, "Debug[10]---->" + data[10]);

        }else{
            Log.d(Tag, "Debug---->" + "Error!!!");
            TextView textView = getView().findViewById(R.id.txtResult);
            textView.setText(resultQRString);
        }

    }

    private void saveController() {
        Button button = getView().findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }

    private void cancelController() {
        Button button = getView().findViewById(R.id.btnCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }


}
