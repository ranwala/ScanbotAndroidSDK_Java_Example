package com.example.scanbotexample;

import android.content.Context;

import com.squareup.picasso.Picasso;

public class PicassoHelper {
    public static Picasso with(Context context) {
        return Picasso.get();
    }
}
