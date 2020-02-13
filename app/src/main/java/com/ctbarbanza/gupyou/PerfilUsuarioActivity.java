package com.ctbarbanza.gupyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.hawk.Hawk;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_usuario);

        user = Hawk.get("user");
    }
}
