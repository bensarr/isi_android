package dev.benswift.tp1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarksFragment extends Fragment {
    private TextView tvMarks;
    private Button btnMarks;
    private String marks;
    private final Handler handler=new Handler();//pour mettre à jour le tvMarks

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_marks, container, false);

        tvMarks=view.findViewById(R.id.tvMarks);
        btnMarks=view.findViewById(R.id.btnMark);
        btnMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marks="";
                marksServer();
            }
        });

        return view;
    }
    private void marksServer()
    {
        OkHttpClient client=new OkHttpClient();
        String url="http://192.168.1.7:8081/api/marks/"+MainActivity.login;
        Request request=new Request.Builder()
                .url(url)
                .build()
                ;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), getString(R.string.error_connetion), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result= response.body().string();
                    JSONObject jo=new JSONObject(result);
                    JSONArray ja=jo.getJSONArray("marks");
                    for (int i = 0; i <ja.length() ; i++) {
                        JSONObject element=ja.getJSONObject(i);
                        String course=element.getString("course");
                        String grade=element.getString("grade");
                        marks+=course+": "+grade+"\n\n";
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvMarks.setText(marks);
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}