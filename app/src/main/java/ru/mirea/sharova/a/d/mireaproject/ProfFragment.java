package ru.mirea.sharova.a.d.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ProfFragment extends Fragment {

    EditText editTextName, editTextLastname, editSex;
    Button button;
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_prof, container, false);

        editTextName = root.findViewById(R.id.name);
        editTextLastname = root.findViewById(R.id.lastname);
        editSex = root.findViewById(R.id.sex);

        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        Context context = getActivity();
        sharedPref = context.getSharedPreferences("profile_settings", Context.MODE_PRIVATE);

        String nameSave = sharedPref.getString("NAME ", "unknown");
        String lastnameSave = sharedPref.getString("LASTNAME ", "unknown");
        String sexSave = sharedPref.getString("SEX ", "unknown");

        if(!nameSave.equals("unknown"))
        {
            editTextName.setText(nameSave);
        }
        if(!lastnameSave.equals("unknown"))
        {
            editTextLastname.setText(lastnameSave);
        }
        if(!sexSave.equals("unknown"))
        {
            editSex.setText(sexSave);
        }

        return root;
    }

    public void saveProfile()
    {
        SharedPreferences.Editor editor	= sharedPref.edit();

        String name = editTextName.getText().toString();
        editor.putString("NAME", name);

        String email = editTextLastname.getText().toString();
        editor.putString("LASTNAME", email);

        String sex = editSex.getText().toString();
        editor.putString("SEX", sex);

        editor.apply();
    }
}