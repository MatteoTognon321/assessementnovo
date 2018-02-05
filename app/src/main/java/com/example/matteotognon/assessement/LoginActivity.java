package com.example.matteotognon.assessement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edtlogin;
    EditText edtconfirmarsenha;
    Button btnlogar;
    Button btnvoltar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtlogin = (EditText) findViewById(R.id.edtlogin);
        edtconfirmarsenha = (EditText) findViewById(R.id.edtconfirmarsenha);
        btnlogar = (Button) findViewById(R.id.btnlogar);
        btnvoltar = (Button) findViewById(R.id.btnvoltar);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
          if(firebaseAuth.getCurrentUser() == null ){



          }
            }
        };

    }
    public void voltar(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void logar(View view){

        metodologar();


    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
}


    public void metodologar(){
       String email = edtlogin.getText().toString();
       String password = edtconfirmarsenha.getText().toString();

       if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
           edtlogin.setError("!");
           edtconfirmarsenha.setError("!");
       }else{

       mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(!task.isSuccessful()){

                   Toast.makeText(LoginActivity.this, "Email ou Senha estão erradas!", Toast.LENGTH_SHORT).show();


               }else{
                   Intent intent = new Intent(LoginActivity.this, TelaPrincipal.class);
                   startActivity(intent);
           }
       }
    });
} }}
