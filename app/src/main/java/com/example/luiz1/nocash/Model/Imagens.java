package com.example.luiz1.nocash.Model;

import com.example.luiz1.nocash.R;



public class Imagens {
    private String[] img;

    public Imagens() {
        this.img = new String[]{
                                // parceiros 1, 2, 3, 4
                                String.valueOf(R.drawable.aprod1),
                                String.valueOf(R.drawable.aprod2),
                                String.valueOf(R.drawable.aprod3),
                                String.valueOf(R.drawable.aprod4),

                                String.valueOf(R.drawable.bprod1),
                                String.valueOf(R.drawable.bprod2),
                                String.valueOf(R.drawable.bprod3),
                                String.valueOf(R.drawable.bprod4),

                                String.valueOf(R.drawable.cprod1),
                                String.valueOf(R.drawable.cprod2),
                                String.valueOf(R.drawable.cprod3),
                                String.valueOf(R.drawable.cprod4),

                                String.valueOf(R.drawable.dprod1),
                                String.valueOf(R.drawable.dprod2),
                                String.valueOf(R.drawable.dprod3),
                                String.valueOf(R.drawable.dprod4)



        };
    }



    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }
}
