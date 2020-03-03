package com.ctbarbanza.gupyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ctbarbanza.gupyou.auth.AuthEvent;
import com.ctbarbanza.gupyou.auth.GoogleAuthController;
import com.ctbarbanza.gupyou.menu.MenuEvent;
import com.ctbarbanza.gupyou.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity {

    private static final int GOOGLE_LOGIN_ACT_RESULCODE = 1223;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Hawk.init(this).build();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        initButtons();
    }

    private void initButtons() {
        ImageButton btnInstagram = findViewById(R.id.act_login_instagram);
        ImageButton btnFacebook  =  findViewById(R.id.act_login_facebook);
        ImageButton btnLinkedin  =  findViewById(R.id.act_login_linkedin);
        ImageButton btnTwitter   =  findViewById(R.id.act_login_twitter);
        ImageButton btnSnapchat  =  findViewById(R.id.act_login_snapchat);
        ImageButton btnTwitch    =  findViewById(R.id.act_login_twitch);
        ImageButton btnTiktok    =  findViewById(R.id.act_login_tiktok);
        ImageButton btnTinder    =  findViewById(R.id.act_login_tinder);
        ImageButton btnGmail     = findViewById(R.id.act_login_youtube);



        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = "USER_UID";
                DbController.get(uid);

                User nUser = new User();
                nUser.uid = uid;
                nUser.instagram = "INSTAGRAM-01";
                nUser.facebook = "FACEBOOK-01";
                nUser.google = "Google-01";
                nUser.img = "IMG--PATH";
                nUser.name = "Nombre";
                nUser.nick = "Apellidos";

                DbController.saveUser(nUser);
                sendEventLogin(nUser);

            }
        };

        btnInstagram.setOnClickListener(loginListener);
        btnFacebook.setOnClickListener(loginListener);
        btnLinkedin.setOnClickListener(loginListener);
        btnTwitter.setOnClickListener(loginListener);
        btnSnapchat.setOnClickListener(loginListener);
        btnTwitch.setOnClickListener(loginListener);
        btnTiktok.setOnClickListener(loginListener);
        btnTinder.setOnClickListener(loginListener);
        btnGmail.setOnClickListener(loginListener);
    }

    private void sendEventLogin(User user){
        AuthEvent event = new AuthEvent();
        event.isOk = true;
        event.user = user;
        //EventBus.getDefault().post(event);
        Hawk.put("user", event.user);
        Intent intent = new Intent(this, PerfilUsuarioActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AuthEvent event) {
        if (event.isOk){
            Logger.i("Usuario Logeado");
            Hawk.put("user", event.user);

            Intent intent = new Intent(this, PerfilUsuarioActivity.class);
            startActivity(intent);

        }else{
            Logger.e("Usuario NO Logeado");
            Hawk.delete("user");
        }

    };



}
