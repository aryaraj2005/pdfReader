package com.example.epdfreader;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<myModel , myAdapter.myViewholder>{

    public myAdapter(@NonNull FirebaseRecyclerOptions<myModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, int position, @NonNull myModel model) {
           holder.header.setText(model.getFilename());
           holder.cmttext.setText(String.valueOf(model.getNoc()));
          holder.liketext.setText(String.valueOf(model.getNol()));
          holder.disliketext.setText(String.valueOf(model.getNod()));
          // now we are trying to display the pdf first we recieve the link from realtime database and make
        // able to read the pdf in new intent
            holder.bookimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(holder.bookimg.getContext() , pdfReadActivity.class  );
                    intent.putExtra("filename" , model.getFilename());
                    intent.putExtra("fileurl" , model.getFileurl());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    holder.bookimg.getContext().startActivity(intent);

                }
            });
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign , parent , false);
        return new myViewholder(view);
    }

    public  class  myViewholder  extends RecyclerView.ViewHolder {
             ImageView bookimg ,  likeimg , dislike , commentimg;
             TextView header , liketext , disliketext , cmttext;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
             bookimg = itemView.findViewById(R.id.textbookimg);
             header = itemView.findViewById(R.id.header);
             liketext = itemView.findViewById(R.id.liketext);
             disliketext = itemView.findViewById(R.id.disliketext) ;
             cmttext = itemView.findViewById(R.id.commentxt);
        }
    }
}
