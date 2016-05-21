package com.muravyovdmitr.shoplocator.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muravyovdmitr.shoplocator.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Dima Muravyov on 5/20/16.
 */
public class BackupDialogFragment extends DialogFragment {
    private EditText mEmail;
    private Button mOk;
    private Button mCancel;

    private final OnClickListener mOkClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            sendMail(mEmail.getText().toString());
        }
    };

    private final OnClickListener mCancelClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        mEmail = (EditText) view.findViewById(R.id.fragment_dialog_mail);
        mOk = (Button) view.findViewById(R.id.fragment_dialog_ok);
        mCancel = (Button) view.findViewById(R.id.fragment_dialog_cancel);

        mOk.setOnClickListener(mOkClickListener);
        mCancel.setOnClickListener(mCancelClickListener);

        return view;
    }

    private void sendMail(String mail) {
        File backupDB = null;

        try {
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getContext().getPackageName() + "//databases//";
                String dbName = "ShopLocator.db";

                File currentDB = new File(currentDBPath, dbName);
                backupDB = new File(sd, dbName);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (backupDB != null) {
            Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);

            sendEmailIntent.setType("text/plain");
            sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
            sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.activity_settings_mail_subject));
            sendEmailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.activity_settings_mail_body));
            sendEmailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(backupDB));

            startActivity(Intent.createChooser(sendEmailIntent, getResources().getString(R.string.activity_settings_mail_chooser_title)));
        } else {
            Toast.makeText(
                    getContext(),
                    getResources().getString(R.string.activity_settings_mail_error),
                    Toast.LENGTH_LONG
            ).show();
        }

        dismiss();
    }
}
