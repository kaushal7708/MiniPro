package com.example.minipro;


import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private FirebaseAnalytics mFirebaseAnalytics;
	public TextView t1, t2;
	public ImageView profile;
	RecyclerView recyclerView;
	recyclerAdapter adpt;
	List<notice> noticeList;
	private FirebaseAuth firebaseAuth;
	FirebaseAuth.AuthStateListener mAuthListener;
	PrefManager prefManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Obtain the FirebaseAnalytics instance.
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		firebaseAuth = FirebaseAuth.getInstance();


		recyclerView = findViewById(R.id.ricyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		setuserdata();
		noticeList = new ArrayList<>();
		setdata();

		adpt = new recyclerAdapter(noticeList, this);
		recyclerView.setAdapter(adpt);
		t1 = findViewById(R.id.gname);
		//t2 = findViewById(R.id.email);
		prefManager = new PrefManager(this);
		if (!prefManager.isUserLogedOut()) {
			String str = prefManager.getEmail();
			NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
			View headerView = navigationView.getHeaderView(0);
			TextView navUsername = (TextView) headerView.findViewById(R.id.email);
			navUsername.setText(str);

			//t2.setText(str);
			Log.d("AND", str);
		}

		profile = findViewById(R.id.profilepic);
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		fab.requestFocus();
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
	}


	@Override
	protected void onStart() {
		super.onStart();
		setuserdata();
		//firebaseAuth.addAuthStateListener(mAuthListener);
	}

	private void setuserdata() {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			String str = user.getDisplayName();
			NavigationView navigationView = findViewById(R.id.nav_view);
			View headerView = navigationView.getHeaderView(0);
			TextView navUsername = headerView.findViewById(R.id.gname);
			TextView Email =  headerView.findViewById(R.id.email);
			ImageView img = headerView.findViewById(R.id.profilepic);
			String mail = user.getEmail();
			String imgurl = String.valueOf(user.getPhotoUrl());
			Picasso.get().load(imgurl).into(img);
			navUsername.setText(str);
			Email.setText(mail);
		} else {
			NavigationView navigationView =  findViewById(R.id.nav_view);
			View headerView = navigationView.getHeaderView(0);
			TextView navUsername =  headerView.findViewById(R.id.gname);
			TextView Email =  headerView.findViewById(R.id.email);
			ImageView img = headerView.findViewById(R.id.profilepic);
			Email.setText("Name@gmail.com");
			navUsername.setText("User Name");
			img.setImageResource(R.drawable.ic_logo_google);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		setuserdata();
		//	firebaseAuth.addAuthStateListener(mAuthListener);
	}


	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_home) {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.login_google) {
			Intent i = new Intent(this, loginAct.class);
			startActivity(i);
		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void setdata() {
		noticeList.add(new notice("21-02-2019", "BBA", "In another life, I would be your girl\n" +
				"We keep all our promises, be us against the world\n" +
				"In another life, I would make you stay\n" +
				"So I don't have to say you were the one that got away\n" +
				"The one that got away\n" +
				"All this money can't buy me a time machine, no\n" +
				"Can't replace you with a million rings, no\n" +
				"I should've told you what you meant to me\n" +
				"'Cause now I pay the price"));
		noticeList.add(new notice("10-02-2019", "BCA", "I'm selfish, impatient and a little insecure. I make mistakes, I am out of control and at times hard to handle." +
				" \nBut if you can't handle me at my worst, then you sure as hell don't deserve me at my best."));

		noticeList.add(new notice("04-06-2019", "BA", "#सिक्का  #दोनोंका  #होता है,\n" +
				"#Heads  का भी #Tale  का #भी,\n" +
				"पर #वक्त  सिर्फ #उसकाहोता  है जो #पलट कर #उपर_आता है …\n" +
				"#कुछ लोग  हमारी हैसियत #पूछनेलगे,\n" +
				"#उनकीशख्सियत  बिक जाए  इतनी हैसियत है #हमारी ।।\n" +
				"#खटकता  तो उनको हूँ साहब  जहाँ मैं #झुकतानहीं,\n" +
				"#बाकी जिन्हें अच्छा लगता हूँ वो मुझे कही झुकने #नहींदेते ।।"));

		noticeList.add(new notice("15-06-2019", "B.Com", "Accidentally you come into my life.\n" +
				"\n" +
				"Unexpectedly you fell in love with me.\n" +
				"Unknowingly I too fell in love with you.\n" +
				"Unconditionally you loved me with all your heart\n" +
				"\n" +
				"And now,mentally, physically and emotionally you Own me.\n" +
				"I belong to you,now and forever :gift_heart:"));

		noticeList.add(new notice("13-12-2019", "BCA", "in your dark days\n" +
				"Just turn around and\n" +
				"I will be there\n" +
				"And maybe I won't\n" +
				"Any more light\n" +
				"To give than what you\n" +
				"Already have. But\n" +
				"I will take your hand\n" +
				"And we will find the\n" +
				"Light together.\n" +
				"#peace☮️ #love ❤️"));


	}
}
