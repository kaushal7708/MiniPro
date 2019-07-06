package com.example.minipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class sign_up_page extends AppCompatActivity {
	EditText email, pass, conpass;
	FirebaseAuth auth;
	Button signup;

	private ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_page);
		email = findViewById(R.id.sign_up_emailID);
		pass = findViewById(R.id.sign_up_pass);
		conpass = findViewById(R.id.sign_up_con_pass);
		signup = findViewById(R.id.sign_up_btu);
		auth = FirebaseAuth.getInstance();
pb =findViewById(R.id.sign_up_process);
		signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				String email_id = email.getText().toString();
				String password = pass.getText().toString();
				String con_password = conpass.getText().toString();


				if (email_id != null && email_id.contains("@") && email_id.endsWith(".com")) {
					if (password != null && password.length() >= 8) {
						if (con_password != null && con_password.equals(password)) {
							auth.createUserWithEmailAndPassword(email_id, password).addOnCompleteListener(task -> {
								if (task.isSuccessful()){
									pb.setVisibility(View.INVISIBLE);
									Toast.makeText(sign_up_page.this,"user Created  ", Toast.LENGTH_LONG).show();
									Intent i =new Intent(sign_up_page.this,loginAct.class);
									startActivity(i);
								}else{
									pb.setVisibility(View.INVISIBLE);
									Toast.makeText(sign_up_page.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
									Log.d("AND",task.getException().getMessage() );
								}
							});
						}else{
							pb.setVisibility(View.INVISIBLE);
							conpass.requestFocus();
							Toast.makeText(sign_up_page.this, "Password not match ", Toast.LENGTH_LONG).show();
						}
					} else {
						pb.setVisibility(View.INVISIBLE);
						pass.requestFocus();
						Toast.makeText(sign_up_page.this, "Password is too small ", Toast.LENGTH_LONG).show();
					}

				} else {
					pb.setVisibility(View.INVISIBLE);
					email.requestFocus();
					Toast.makeText(sign_up_page.this, "Email Id is Invalid ", Toast.LENGTH_LONG).show();
				}

			}
		});


	}
}
