package com.example.uzair.my_people;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;



public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {

    Context context;

    List<Person> people;

    public PeopleAdapter(Context context, List<Person> people){
        this.people = people;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person,parent,false));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddPerson.class);
                intent.putExtra("edit",true);
                intent.putExtra("person",people.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBHelper(context).deletePerson(people.get(holder.getAdapterPosition()).id);
                people.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(people.get(position).name);
        holder.gender.setText(people.get(position).gender);
        holder.phone.setText(people.get(position).phone);

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(""+people.get(position).name.charAt(0), ColorGenerator.MATERIAL.getRandomColor());

        holder.imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView phone;
        TextView gender;

        ImageView edit;
        ImageView delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            gender = itemView.findViewById(R.id.textView2);
            phone = itemView.findViewById(R.id.textView3);

            edit = itemView.findViewById(R.id.imageView2);
            delete = itemView.findViewById(R.id.imageView3);

        }
    }
}
