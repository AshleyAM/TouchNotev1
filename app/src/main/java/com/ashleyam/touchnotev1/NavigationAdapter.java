
package com.ashleyam.touchnotev1;

import android.content.DialogInterface;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private String navTitles[];

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView navTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            navTextView = (TextView) itemView.findViewById(R.id.navigation_item_text_view);
        }
    }

    NavigationAdapter(String NavigationTitles []) {
        navTitles = NavigationTitles;
    }

    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_row, parent, false);

        ViewHolder vhItem = new ViewHolder(v); // int supposed to be included in constructor
        return vhItem;
    }

    @Override
    public void onBindViewHolder(NavigationAdapter.ViewHolder holder, int position) {
        holder.navTextView.setText(navTitles[position]);
    }

    @Override
    public int getItemCount() {
        return navTitles.length;
    }


}

