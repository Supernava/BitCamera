package supernava.bitcombine.co.th.bitcamera.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import supernava.bitcombine.co.th.bitcamera.R;

public class MainFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    private String resultString;
    private String Tag = "17AugV1";


    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        zXingScannerView = new ZXingScannerView(getActivity());
        return zXingScannerView;
    }

    @Override
    public void handleResult(Result result) {

        resultString = result.getText().toString().trim();
        Log.d(Tag, "Result ==> " + resultString);
        if (!resultString.trim().isEmpty()) {
//            Replace Fragment
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFragmentMain, TakePhotoFragment.takePhotoInstance(resultString))
                    .addToBackStack(null)
                    .commit();
        }
        

            Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                zXingScannerView.resumeCameraPreview(MainFragment.this);
            }
        }, 1000);


    }
}   //Main Class