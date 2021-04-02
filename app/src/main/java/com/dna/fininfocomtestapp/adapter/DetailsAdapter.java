package com.dna.fininfocomtestapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dna.fininfocomtestapp.databinding.DetailsAdapterItemBinding;
import com.dna.fininfocomtestapp.model.Contact;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private final ArrayList<Contact> contacts;
    public DetailsAdapter(ArrayList<Contact> contacts){
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsViewHolder(DetailsAdapterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.binding.tvEmail.setText(contact.getEmail());
        holder.binding.tvNumber.setText(contact.getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder{
        DetailsAdapterItemBinding binding;
        public DetailsViewHolder(DetailsAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
