package com.mihir.searchmovies.view;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mihir.searchmovies.R;

/**
 * Created by mihir on 26-06-2019.
 */
public class ImageBinder {

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView img, String imageUrl) {
        Glide.with(img.getContext()).load("https://image.tmdb.org/t/p/w342" + imageUrl).placeholder(R.drawable.placeholder).into(img);
//        img.setImageURI(Uri.parse(imageUrl));
    }
}
