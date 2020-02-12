package com.ctbarbanza.gupyou.auth;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.ctbarbanza.gupyou.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.orhanobut.logger.Logger;

public class GoogleAuthController {

    private FirebaseAuth mAuth;

    private static final GoogleAuthController instance = new GoogleAuthController();
    private Activity act;

    public static GoogleAuthController init(FirebaseAuth mAuth, Activity act){
        instance.mAuth = mAuth;
        instance.act   = act;
        return instance;
    }

    public void googleLogin(Intent data) {

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Logger.w("Google sign in failed", e);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Logger.d( "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Logger.w("signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }
}
