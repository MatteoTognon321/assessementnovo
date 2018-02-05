package com.example.matteotognon.assessement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CadastrarActivity extends AppCompatActivity {

    EditText edtnome;
    EditText edtemail;
    EditText edtsenha;
    EditText edtsenhaconf;
    EditText edtcpf;
    Button btnconfirmarcadastro;
    Button btnvoltar;
    String nomedousuario;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private  AlertDialog alerta;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edtnome = (EditText) findViewById(R.id.edtnome);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtsenha = (EditText) findViewById(R.id.edtsenha);
        edtsenhaconf = (EditText) findViewById(R.id.edtsenhaconf);
        edtcpf = (EditText) findViewById(R.id.edtCPF);
        btnconfirmarcadastro = (Button) findViewById(R.id.btnconfirmarcadastro);
        btnvoltar = (Button) findViewById(R.id.btnvoltar);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("usuario");
        mFirebaseInstance.getReference("nomeusuario").setValue("salvar usuario");
        mAuth = FirebaseAuth.getInstance();



        SimpleMaskFormatter x = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher z = new MaskTextWatcher(edtcpf, x);
        edtcpf.addTextChangedListener(z);


    }
    public void novoUsuario(String nome, String email, String senha, String cpf){



        if(TextUtils.isEmpty(nomedousuario)){
            nomedousuario = mFirebaseDatabase.push().getKey();
        }
        Usuario usuario = new Usuario(nome, email, senha, cpf);
        mFirebaseDatabase.child(nomedousuario).setValue(usuario);
    }
    public void salvar(View view){
        if(edtnome.getText().length() == 0 || !validarnome(edtnome.getText().toString())){
            edtnome.setError("!");
        }
        else if (edtemail.getText().length() == 0 || !validaremail(edtemail.getText().toString())){
            edtemail.setError("!");
        }
        else if (edtsenha.getText().length() < 6){
            edtsenha.setError("!");
        }
        else if (edtsenhaconf.getText().length() < 6){
            edtsenhaconf.setError("!");
        }
        else if (edtcpf.getText().length() != 14){
            edtcpf.setError("!");
        }
        else if (!edtsenha.getText().toString().equals(edtsenhaconf.getText().toString())) {
           edtsenha.setError("!");
           edtsenhaconf.setError("!");
        }
        else if (TextUtils.isEmpty(nomedousuario)){
            novoUsuario(
                    edtnome.getText().toString(),
                    edtemail.getText().toString(),
                    edtsenha.getText().toString(),
                    edtcpf.getText().toString());

            String getemail = edtemail.getText().toString().trim();
            String getpassword = edtsenha.getText().toString().trim();
            criarUsuario(getemail,getpassword);




            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cadastrado");
            builder.setMessage("Sua conta foi criada com sucesso");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(CadastrarActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            });
              alerta = builder.create();
              alerta.show();
             limpar();
           }else{
            Toast.makeText(this, "Erro", Toast.LENGTH_LONG).show();
        }

    }
    public void limpar(){
        edtnome.setText("");
        edtemail.setText("");
        edtsenha.setText("");
        edtsenhaconf.setText("");
        edtcpf.setText("");
    }
    public void voltar(View view){
        Intent intent = new Intent(CadastrarActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void criarUsuario(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });


}

        public boolean validaremail(String email){

            Pattern pattern;
            Matcher matcher;
            final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL);
            matcher = pattern.matcher(email);

            return matcher.matches();
        }
        public boolean validarnome(String nome){

            Pattern pattern;
            Matcher matcher;
            final String NOME = "^[a-zA-Z\\s]*$";
            pattern = Pattern.compile(NOME);
            matcher = pattern.matcher(nome);

            return matcher.matches();
        }
}
