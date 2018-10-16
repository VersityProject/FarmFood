package com.farmfood.rabbi;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGrid extends ArrayAdapter<DataInput> {
    private Activity context;
    private List<DataInput> userlist;

    public CustomGrid(Activity context, List<DataInput> userlist){
        super(context,R.layout.grid_single, userlist);
        this.context = context;
        this.userlist=userlist;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.grid_single,null,true);
        CardView cardView=(CardView)listviewitem.findViewById(R.id.cardv);
        cardView.setRadius(30);
        cardView.setCardElevation(30);
        cardView.setContentPadding(5,5,5,5);
        TextView textnema = (TextView) listviewitem.findViewById(R.id.grid_text);
        TextView textdesc = (TextView) listviewitem.findViewById(R.id.grid_descrip);
        TextView textprice=(TextView) listviewitem.findViewById(R.id.pricetext);
        ImageView imagev=(ImageView) listviewitem.findViewById(R.id.imgeload);

        DataInput datas=userlist.get(position);
        textnema.setText(datas.getUsername());
        textdesc.setText(datas.getUserdescription());
        textprice.setText(datas.getPrice());
        Picasso.with(context)
                .load(datas.getMimageUrl())
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .noFade()
                .into(imagev);

        return listviewitem;
    }
}