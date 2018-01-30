package net.topan_xt.movielist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.topan_xt.movielist.R;
import net.topan_xt.movielist.model.cast.CastItem;
import net.topan_xt.movielist.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*************************************************
 * Author     : Topan E.                         *
 * Contact    : topan.xt@gmail.com               *
 * Created on : Jan 30, 2018.                    *
 *************************************************/

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<CastItem> casts;
    private Context context;

    public CastAdapter(List<CastItem> casts, Context context) {
        this.casts = casts;
        this.context = context;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position) {
        CastItem cast = casts.get(position);
        holder.mTextCastName.setText(casts.get(position).getName());
        holder.mTextCharacterName.setText(casts.get(position).getCharacter());
        Picasso.with(context).load(Constant.BACKDROP_PATH+cast.getProfilePath()).into(holder.mImageCast);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_cast_name) TextView mTextCastName;
        @BindView(R.id.txt_cast_character) TextView mTextCharacterName;
        @BindView(R.id.img_cast) ImageView mImageCast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
