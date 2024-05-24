package ru.mirea.sharova.a.d.mireaproject;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class FileOperationsFragment extends Fragment {
    private EditText editTextInput, editTextOutput;
    private Button buttonEncrypt, buttonDecrypt;
    private Key secretKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_operations, container, false);

        editTextInput = view.findViewById(R.id.editTextInput);
        editTextOutput = view.findViewById(R.id.editTextOutput);
        buttonEncrypt = view.findViewById(R.id.buttonEncrypt);
        buttonDecrypt = view.findViewById(R.id.buttonDecrypt);

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String input = editTextInput.getText().toString();
                    String encrypted = encrypt(input, secretKey);
                    editTextOutput.setText(encrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encrypted = editTextOutput.getText().toString();
                    String decrypted = decrypt(encrypted, secretKey);
                    editTextOutput.setText(decrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private String encrypt(String data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    private String decrypt(String encryptedData, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT));
        return new String(decrypted);
    }
}
