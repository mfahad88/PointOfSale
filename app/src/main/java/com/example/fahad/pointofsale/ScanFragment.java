package com.example.fahad.pointofsale;




import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.fahad.pointofsale.Db.DbHelper;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
    View view;
    private Button btn_scan;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private Button scanButton;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;
    private FrameLayout preview;
    private String scanResult="";
    private String prodQuantity="";
    private String prodPrice="";
    private String prodName="";

    public ScanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_scan, container, false);
            btn_scan = (Button) view.findViewById(R.id.button2);
            preview = (FrameLayout) view.findViewById(R.id.cameraPreview);
            initControls();
        }catch (Exception e){
            Log.e("Error>>>>>>>>>>>>>>>",e.getMessage());
        }
        return view;
    }



    private void initControls() {


        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(view.getContext(), mCamera, previewCb,
                autoFocusCB);

        preview.addView(mPreview);



        btn_scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }



    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {

                    Log.i("<<<<<<Asset Code>>>>> ",
                            "<<<<Bar Code>>> " + sym.getData());
                    scanResult = sym.getData().trim();


                   Toast.makeText(view.getContext(), scanResult,
                            Toast.LENGTH_SHORT).show();
                    showDialog(view);
                    barcodeScanned = true;

                    break;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    public void showDialog(View view){
        final Dialog dialog=new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_qty);
        final EditText product=(EditText) dialog.findViewById(R.id.editText_Product);
        final EditText unitPrice=(EditText) dialog.findViewById(R.id.editText_UnitPrice);
        final EditText qty=(EditText) dialog.findViewById(R.id.editText_Qty);
        Button btn_done=(Button)dialog.findViewById(R.id.buttonDone);
        Button btn_cancel=(Button)dialog.findViewById(R.id.buttonCancel);
        unitPrice.setText("70");
        product.setText(scanResult);




        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodPrice=String.valueOf(Integer.parseInt(unitPrice.getText().toString())*Integer.parseInt(qty.getText().toString()));
                prodQuantity=String.valueOf(qty.getText().toString());
                prodName=String.valueOf(product.getText().toString());


                new insertSales().execute();

                dialog.dismiss();
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
        dialog.show();
    }



    private class insertSales extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String productName=prodName;
            String productQty=prodQuantity;
            String productPrice=prodPrice;
            DbHelper dbHelper=new DbHelper(view.getContext());
            //Toast.makeText(view.getContext(),productName+","+productQty+","+productPrice,Toast.LENGTH_SHORT).show();
            dbHelper.insertSales(productName,productQty,productPrice);
        }
    }
}
