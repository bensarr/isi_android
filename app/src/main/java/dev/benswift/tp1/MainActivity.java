package dev.benswift.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText txtLogin,txtPassword;
    private Button btnSignin,btnLogin;

    private String login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prof);

        txtLogin=findViewById(R.id.txtLogin);
        txtPassword=findViewById(R.id.txtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignin=findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(v -> {
            Intent intent= new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        );
    }
}