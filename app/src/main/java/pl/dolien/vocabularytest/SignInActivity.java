package pl.dolien.vocabularytest;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    Button googleAuth;
    FirebaseAuth auth;
    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN = 20;
    private boolean isLoggingOut = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        googleAuth = findViewById(R.id.singInWithGoogle);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleAuth.setOnClickListener(v -> googleSignIn());

        if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(SignInActivity.this, NavbarActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void googleSignIn(){
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!isLoggingOut) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Log.w(TAG, "Google sign in failed", e);
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                AuthResult authResult = task.getResult();
                                if (authResult != null && authResult.getAdditionalUserInfo() != null) {
                                    boolean isNewUser = authResult.getAdditionalUserInfo().isNewUser();

                                    if (isNewUser) {
                                        User userData = writeNewUser(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), 0);
                                        mDatabase.child("users").child(user.getUid()).setValue(userData);
                                        Toast.makeText(SignInActivity.this, "Nowy użytkownik zalogowany po raz pierwszy", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Użytkownik już wcześniej się zalogował
                                        Toast.makeText(SignInActivity.this, "Użytkownik już wcześniej zalogowany", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            Intent intent = new Intent(SignInActivity.this, NavbarActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public User writeNewUser(String userId, String userName, String userProfile, int userBestScore) {
        User user = new User(userName, userProfile, userBestScore);

        mDatabase.child("users").child(userId).setValue(user);
        return user;
    }
}