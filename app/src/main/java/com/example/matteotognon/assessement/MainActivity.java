package com.example.matteotognon.assessement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
    Button btnlogin;
    Button btncadastrar;
    private LoginButton loginButton;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btncadastrar = (Button) findViewById(R.id.btncadastrar);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {

                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void cadastrar(View view){
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }


}