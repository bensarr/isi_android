package dev.benswift.tp1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscriptionFragment extends Fragment {

    private EditText txtFirstName,txtLastName;
    private CheckBox cbOLevel,cbBachelor,cbMaster;
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
        Button btnSave = view.findViewById(R.id.btnSave);

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
            if (firstName.isEmpty()||lastName.isEmpty()||degrees.isEmpty())
            {
                Toast.makeText(getActivity(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
            }
            else {
                /*String resume= firstName+"\n\n"+lastName+"\n\n"+degrees;
                Toast.makeText(getActivity(),resume ,Toast.LENGTH_SHORT).show();
                 */
                inscriptionServer();
                Intent intent= new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
            }


        });


        return view;
    }
    public void inscriptionServer(){
        OkHttpClient client=new OkHttpClient();
        String formation=FragmentFormation.formation;
        String url="http://192.168.1.7:8081/api/inscription?first_name="+firstName+"&last_name="+lastName+"&formation="+formation+"&degrees="+degrees;
        RequestBody body=new FormBody.Builder()
                .add("first_name",firstName)
                .add("last_name",lastName)
                .add("formation",formation)
                .add("degrees",degrees)
                .build()
                ;
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build()
                ;
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String message=getString(R.string.error_connetion);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result=response.body().string();
                        JSONObject jo=new JSONObject(result);
                        String status=jo.getString("status");
                        if(status.equalsIgnoreCase("OK"))
                        {
                            Toast.makeText(getActivity(), getString(R.string.success_register), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), getString(R.string.error_register), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e)
        {

        }
    }

}