package com.ctbarbanza.gupyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ctbarbanza.gupyou.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_usuario);

        user = Hawk.get("user");

        DbController.get(user.getUid());


        updateProfileData();
    }

    private void updateProfileData() {

        User nUser = new User();
        nUser.uid       = this.user.getUid();
        nUser.instagram = "INSTAGRAM-01";
        nUser.facebook  = "FACEBOOK-01";
        nUser.google    = this.user.getDisplayName();
        nUser.img       = this.user.getPhotoUrl().getPath();
        nUser.name      = this.user.getDisplayName();
        nUser.nick      = this.user.getDisplayName();

        DbController.saveUser(nUser);

    }
}
