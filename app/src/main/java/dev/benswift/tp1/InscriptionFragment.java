package dev.benswift.tp1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class InscriptionFragment extends Fragment {

    private EditText txtFirstName,txtLastName;
    private CheckBox cbOLevel,cbBachelor,cbMaster;
    private Button btnSave;
    String firstName,lastName,degrees;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inscription, container, false);
        txtFirstName= view.findViewById(R.id.txtFirstName);
        txtLastName= view.findViewById(R.id.txtLastName);
        cbOLevel=view.findViewById(R.id.cbOLevel);
        cbBachelor=view.findViewById(R.id.cbBachelor);
        cbMaster=view.findViewById(R.id.cbMasterr);
        btnSave=view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {

        });


        return view;
    }
}