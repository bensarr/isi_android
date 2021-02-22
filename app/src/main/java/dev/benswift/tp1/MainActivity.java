package dev.benswift.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText txtLogin,txtPassword;
    private Button btnSignin,btnLogin;

    private String password;
    public static String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prof);

        txtLogin=findViewById(R.id.txtLogin);
        txtPassword=findViewById(R.id.txtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignin=findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(v -> {
            login=txtLogin.getText().toString().trim();
            password=txtPassword.getText().toString().trim();
            if(login.isEmpty()||password.isEmpty())
            {
                final String message=getString(R.string.error_fields);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Service Connexion
                loginServer();
            }
        }
        );
    }
    //Communication Login & API SPRING
    public void loginServer(){

        OkHttpClient client=new OkHttpClient();
        String url="http://192.168.1.7:8081/api/connexion?login="+login+"&password="+password;
        Request request= new Request.Builder()
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message= getString(R.string.error_connetion);
                runOnUiThread((new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }));
                //call.cancel();//si erreur
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //si ok
                try {
                    String result=response.body().string();
                    JSONObject jo=new JSONObject(result);
                    String status=jo.getString("status");
                    if(status.equalsIgnoreCase("KO"))
                    {
                        final String message= getString(R.string.error_parameters);
                        runOnUiThread((new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                    else {
                        Intent intent= new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }
}