package dev.benswift.tp1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
            firstName=txtFirstName.getText().toString().trim();
            lastName=txtLastName.getText().toString().trim();
            degrees="";
            if(cbOLevel.isChecked())
            {
                degrees+=cbOLevel.getText().toString()+" ";
            }
            if(cbBachelor.isChecked())
            {
                degrees+=cbBachelor.getText().toString()+" ";
            }
            if(cbMaster.isChecked())
            {
                degrees+=cbMaster.getText().toString()+" ";
            }
            else {
                String resume= firstName+"\n\n"+lastName+"\n\n"+degrees;
                Toast.makeText(getActivity(),resume ,Toast.LENGTH_SHORT).show();
            }

        });


        return view;
    }
}