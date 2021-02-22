package dev.benswift.tp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.benswift.tp1.model.Utilisateur;
import dev.benswift.tp1.tools.DatabaseHelper;

public class SqliteFragment extends Fragment {

    private DatabaseHelper db;

    private EditText txtLogin,txtPassword;
    private String login,password;
    private Button btnSave;

    private ListView lvwUsers;
    private List<Utilisateur> listUsers;
    private Utilisateur user;
    private int i;

    private final Handler handler=new Handler();//pour mettre à jour le ListUsers

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sqlite, container, false);
        lvwUsers=v.findViewById(R.id.lvwUsers);
        txtLogin=v.findViewById(R.id.txtLoginUser);
        txtPassword=v.findViewById(R.id.txtPasswordUser);
        btnSave=v.findViewById(R.id.btnSave);
        db=new DatabaseHelper(getActivity());
        listUsers= db.userGetAll();
        ArrayAdapter<Utilisateur> adapter= new ArrayAdapter<Utilisateur>(getActivity(), android.R.layout.simple_list_item_1, listUsers);
        lvwUsers.setAdapter(adapter);

        btnSave.setOnClickListener(v1 -> {
            login=txtLogin.getText().toString().trim();
            password=txtPassword.getText().toString().trim();
            if(!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password))
            {
                if(i>0)
                {
                    user.setPassword(password);
                    user.setLogin(login);
                    i=db.userUpdate(user);

                }else
                {
                    user=new Utilisateur(login,password);
                    i=db.userCreate(user);
                    listUsers.add(user);
                }
                if(i>0)
                {
                    txtLogin.setText("");
                    txtPassword.setText("");
                    listUsers.clear();
                    listUsers.addAll(db.userGetAll());
                    adapter.notifyDataSetChanged();
                    i=0;
                    Toast.makeText(getActivity(), "SUCCES Enregistrement Utilisateur ",
                            Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "ERREUR Enregistrement Utilisateur",
                            Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.e("Erreur","**********************Un des Inputs est" +
                        " Vides***********"+ LocalDateTime.now());
                Toast.makeText(getActivity(), "Un des Inputs est Vides",
                        Toast.LENGTH_LONG).show();

            }
        });
        lvwUsers.setOnItemClickListener((parent, view, position, id) -> {
            Utilisateur u=listUsers.get(position);

            AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setTitle("Information: "+u.getLogin());
            dialog.setMessage("Id:"+u.getId()+"\nLogin:"+u.getLogin()+"\nPassword:"+u.getPassword());
            dialog.setNeutralButton(getString(R.string.delete_user), (dialog1, which) -> {
                int i=db.userDelete(u);
                if(i>0) {
                    listUsers.clear();
                    listUsers.addAll(db.userGetAll());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "SUCCES Suppression Utilisateur ",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "ERREUR Suppression Utilisateur ",
                            Toast.LENGTH_LONG).show();
                }
            });//nuul pour fermer la boite de dialogue
            dialog.setPositiveButton(getString(R.string.update_user), (dialog1, which) -> {
                user=listUsers.get(position);
                i=user.getId();
                txtLogin.setText(user.getLogin());
                txtPassword.setText(user.getPassword());
                Toast.makeText(getActivity(), "Remplissez le formulaire Utilisateur ",
                        Toast.LENGTH_LONG).show();
            });

            dialog.show();
        });
        return v;
    }
}