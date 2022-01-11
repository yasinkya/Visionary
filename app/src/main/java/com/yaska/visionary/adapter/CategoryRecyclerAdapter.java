package com.yaska.visionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaska.visionary.R;
import com.yaska.visionary.model.AllCategories;
import com.yaska.visionary.model.Movie;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MainViewHolder> {



    String username;
    Context context ;
    List<AllCategories> allCategoriesList;

    public CategoryRecyclerAdapter(Context context, List<AllCategories> allCategoriesList, String username) {
        this.context = context;
        this.allCategoriesList = allCategoriesList;
        this.username = username;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mainpage_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        /* Anasayfadaki kategorileri oluştur ve Herbir kategori için kategori içeriklerini ayarla */
        holder.categoryName.setText(allCategoriesList.get(position).getCategoryTitle());
        setItemRecycler(holder.ItemRecycler,allCategoriesList.get(position).getCategoryItemList());
       
    }

    @Override
    public int getItemCount() {
        return allCategoriesList.size();
    }

    /* Ana sayfada gözükecek olan kategori kutcuklarını bul */
    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        RecyclerView ItemRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName=itemView.findViewById(R.id.item_category);
            ItemRecycler=itemView.findViewById(R.id.item_recycler);
        }
    }

    /* Her bir kategori içerisine verileri aktarmak için adapter kullan */
    private void setItemRecycler(RecyclerView recyclerView, List<Movie> categoryItemList){

        /* Yeni bir adapter nesnesi oluştur ve o adapter için verileri ilgili kategoriye akter */
        ItemCategoryAdapter itemCategoryAdapter = new ItemCategoryAdapter(context,categoryItemList, username);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(itemCategoryAdapter);

    }

}
