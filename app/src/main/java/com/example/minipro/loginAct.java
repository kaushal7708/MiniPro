package com.example.minipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginAct extends AppCompatActivity {
	private FirebaseAuth mAuth;
	private static final int GOOGLE_SIGN = 123;
	private Button login, logout, google_sign_in_btu, google_sign_out;
	private ImageView im;
	private EditText email, pass;
	private ProgressBar pb;
	private GoogleSignInClient gsc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = findViewById(R.id.btu);
		logout = findViewById(R.id.btu1);
		im = findViewById(R.id.userimg);

		pb = findViewById(R.id.progress_circular);
		email = findViewById(R.id.emailID);
		pass = findViewById(R.id.passs);
		google_sign_out = findViewById(R.id.siginout);
		google_sign_in_btu = findViewById(R.id.google_sign_in);

		mAuth = FirebaseAuth.getInstance();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();
		gsc = GoogleSignIn.getClient(this, gso);

		login.setOnClickListener(v -> emailsignin());
		logout.setOnClickListener(v -> adduser());
		google_sign_in_btu.setOnClickListener(v -> SignInGoogle());
		google_sign_out.setOnClickListener(v -> signout());
	}

	private void adduser() {
		Intent i = new Intent(this, sign_up_page.class);
		startActivity(i);
	}

	private void emailsignin() {
		pb.setVisibility(View.VISIBLE);
		String email_id = email.getText().toString();
		String password = pass.getText().toString();
		if (email_id != null && email_id.contains("@") && email_id.endsWith(".com")) {
			if (password != null && password.length() >= 8) {
				mAuth.signInWithEmailAndPassword(email_id, password).addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						saveLoginDetails(email_id, password);
						pb.setVisibility(View.INVISIBLE);
						Toast.makeText(this, "user Created  ", Toast.LENGTH_LONG).show();
						Intent i = new Intent(this, MainActivity.class);
						startActivity(i);
						finish();
					} else {
						pb.setVisibility(View.INVISIBLE);
						Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
						Log.d("AND", task.getException().getMessage());
					}
				});
			} else {
				pb.setVisibility(View.INVISIBLE);
				pass.requestFocus();
				Toast.makeText(this, "Password is too small ", Toast.LENGTH_LONG).show();
			}
		} else {
			pb.setVisibility(View.INVISIBLE);
			email.requestFocus();
			Toast.makeText(this, "Email Id is Invalid ", Toast.LENGTH_LONG).show();
		}
	}

	private void SignInGoogle() {
		pb.setVisibility(View.VISIBLE);
		Intent signIntent = gsc.getSignInIntent();
		Log.d("AND", "enter to the signing in");
		startActivityForResult(signIntent, GOOGLE_SIGN);
	}

	private void saveLoginDetails(String email, String password) {
		new PrefManager(this).saveLoginDetails(email, password);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GOOGLE_SIGN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				if (account != null) googlefirebaseauth(account);
			} catch (ApiException e) {
				Log.w("TAG", "Google sign in failed", e);
			}
		}
	}

	private void googlefirebaseauth(GoogleSignInAccount googlesigninaccount) {
		Log.d("AND", "FireBase Auth " + googlesigninaccount.getId());
		AuthCredential credential = GoogleAuthProvider.getCredential(googlesigninaccount.getIdToken(), null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						pb.setVisibility(View.INVISIBLE);
						Log.d("AND", "sign in success full   " + googlesigninaccount.getIdToken());
						FirebaseUser user = mAuth.getCurrentUser();
						updateUI(user);
					} else {
						pb.setVisibility(View.INVISIBLE);
						Log.d("AND", "sign in fail");
						Toast.makeText(this, "something is worng", Toast.LENGTH_LONG).show();
						updateUI(null);
					}
				});
	}

	private void updateUI(FirebaseUser user) {
		if (user != null) {
			String str = user.getDisplayName();
			Toast.makeText(this, "Welcome " + str, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "User sign out", Toast.LENGTH_LONG).show();
		}
	}

	private void signout() {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			FirebaseAuth.getInstance().signOut();
			gsc.signOut().addOnCompleteListener(this, task -> updateUI(null));
		}
	}

}
