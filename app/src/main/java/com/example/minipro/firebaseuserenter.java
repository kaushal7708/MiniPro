package com.example.minipro;

import com.google.firebase.auth.FirebaseUser;

class firebaseuserenter {
	FirebaseUser user = null;

	public FirebaseUser getUser() {
		return user;
	}

	public firebaseuserenter() {

	}

	public firebaseuserenter(FirebaseUser user) {
		this.user = user;
	}


}
