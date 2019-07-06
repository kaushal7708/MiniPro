package com.example.minipro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home {
	FirebaseAuth fbath;
	FirebaseUser user;

	public Home(FirebaseUser user) {
		this.user = user;
	}
}