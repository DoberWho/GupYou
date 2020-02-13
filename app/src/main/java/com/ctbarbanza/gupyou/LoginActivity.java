package com.ctbarbanza.gupyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ctbarbanza.gupyou.auth.AuthEvent;
import com.ctbarbanza.gupyou.auth.GoogleAuthController;
import com.ctbarbanza.gupyou.menu.MenuEvent;
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
        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        initButtons();
    }

    private void initButtons() {
        Button btnGmail    =  findViewById(R.id.login_gmail_btn);
        Button btnTwitter  =  findViewById(R.id.login_twitter_btn);
        Button btnFacebook =  findViewById(R.id.login_facebook_btn);

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_LOGIN_ACT_RESULCODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_LOGIN_ACT_RESULCODE) {
            GoogleAuthController.init(mAuth, this).googleLogin(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Logger.i("USER: "+currentUser);

        String arr = "";
        for (UserInfo info : currentUser.getProviderData()){
            String user = "Display:"+info.getDisplayName()+"\n\r"
                    +"Email:"+info.getEmail()+"\n\r"
                    +"Phone:"+info.getPhoneNumber()+"\n\r"
                    +"P-ID:"+info.getProviderId()+"\n\r"
                    +"Photo:"+info.getPhotoUrl()+"\n\r"
                    +"UID: "+info.getUid();
            arr += "\n\r========="+user;
        }

        String user = "Display:"+currentUser.getDisplayName()+"\n\r"
                +"Email:"+currentUser.getEmail()+"\n\r"
                +"Phone:"+currentUser.getPhoneNumber()+"\n\r"
                +"P-ID:"+currentUser.getProviderId()+"\n\r"
                +"Photo:"+currentUser.getPhotoUrl()+"\n\r"
                +"ProviderData:"+arr+"\n\r"
                +"UID: "+currentUser.getUid();
        Log.i("LOGIN", user);

        if (currentUser != null){
            AuthEvent event = new AuthEvent();
            event.isOk = true;
            event.user = currentUser;
            EventBus.getDefault().post(event);
        }
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
