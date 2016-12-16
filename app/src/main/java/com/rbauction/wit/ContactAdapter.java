package com.rbauction.wit;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.Console;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<AuctionInfo> contactList;
    AuctionInfo ci;


    public ContactAdapter(List<AuctionInfo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ci = contactList.get(i);

        contactViewHolder.vAuction_Name.setText(ci.Auction_Name);
        contactViewHolder.vAuction_Date.setText(ci.Auction_Date);
        contactViewHolder.vAuction_Number.setText(ci.Auction_Number);
        contactViewHolder.auct_info = ci;
        contactViewHolder.mOnClickListener =  new MyOnClickListener(ci);
        contactViewHolder.v.setOnClickListener(contactViewHolder.mOnClickListener);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView, ci);
    }

        public static class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vAuction_Name;
            protected TextView vAuction_Date;
            protected TextView vAuction_Number;
            public AuctionInfo auct_info;
            public View v;

            public View.OnClickListener mOnClickListener;

            public ContactViewHolder(View v, AuctionInfo af) {

                super(v);
                this.v = v;
                vAuction_Name =  (TextView) v.findViewById(R.id.auctName);
                vAuction_Date = (TextView)  v.findViewById(R.id.auctDate);
                vAuction_Number = (TextView)  v.findViewById(R.id.auctNum);
            }
        }
}
