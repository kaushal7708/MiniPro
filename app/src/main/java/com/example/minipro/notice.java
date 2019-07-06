package com.example.minipro;

public class notice {
	private String date, title, notice;

	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getNotice() {
		return notice;
	}

	public notice(String date, String title, String notice) {
		this.date = date;
		this.title = title;
		this.notice = notice;
	}
}
