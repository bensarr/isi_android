package dev.benswift.tp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentFormation extends Fragment {

    private ListView lvwFormations;
    private String[] tab_formation,tab_details;

    private String formation,details;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_formation, container, false);

        lvwFormations= view.findViewById(R.id.lvwFormation);
        tab_formation= getResources().getStringArray(R.array.tab_formations);
        tab_details= getResources().getStringArray(R.array.tab_details);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,tab_formation);
        lvwFormations.setAdapter(adapter);

        lvwFormations.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        formation= tab_formation[position];
                        details= tab_details[position];

                        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                        dialog.setIcon(R.mipmap.ic_launcher);
                        dialog.setTitle(formation);
                        dialog.setMessage(details);
                        dialog.setNegativeButton(getString(R.string.cancel),null);//nuul pour fermer la boite de dialogue
                        dialog.setPositiveButton(getString(R.string.inscription), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }//simuler le clique qui nous redirige vers une autre page
                        });
                        dialog.show();

                    }
                }
        );

        return view;
    }
}