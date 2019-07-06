package com.example.minipro;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.noticeholder> {
	List<notice> notices;
	Context contx;

	public recyclerAdapter(List<notice> notices, Context contx) {
		this.notices = notices;
		this.contx = contx;
	}

	@NonNull
	@Override
	public noticeholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		LayoutInflater inflater = LayoutInflater.from(contx);
		View view = inflater.inflate(R.layout.posts, null, false);
		return new noticeholder(view);

	}

	@Override
	public void onBindViewHolder(@NonNull noticeholder noticeholder, int i) {
		notice notice = notices.get(i);
		noticeholder.title.setText(notice.getTitle());
		noticeholder.date.setText(notice.getDate());
		noticeholder.mainmess.setText(notice.getNotice());

		noticeholder.send.setOnClickListener(v -> {
			String mess = noticeholder.et.getText().toString();
			if (!mess.equals("")) {
				Toast.makeText(contx, mess, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(contx, "No Question Ask", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public int getItemCount() {
		return notices.size();
	}

	class noticeholder extends RecyclerView.ViewHolder {
		TextView title, date, mainmess;
		EditText et;
		ImageView send;

		public noticeholder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.n_title);
			date = itemView.findViewById(R.id.date);
			mainmess = itemView.findViewById(R.id.n_message);
			send = itemView.findViewById(R.id.n_send);
			et = itemView.findViewById(R.id.ask);

		}
	}
}
