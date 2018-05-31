package com.ilyjava.android.searchyourbook.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ilyjava.android.searchyourbook.api.ApiService;
import com.ilyjava.android.searchyourbook.model.BooksAdapter;
import com.ilyjava.android.searchyourbook.model.Item;
import com.ilyjava.android.searchyourbook.model.ItemList;
import com.ilyjava.android.searchyourbook.R;
import com.ilyjava.android.searchyourbook.api.RetroClient;
import com.ilyjava.android.searchyourbook.utils.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity {
    private String LOG_TAG = BooksActivity.class.getSimpleName();

    private ArrayList<Item> itemList;
    private BooksAdapter mAdapter;
    private RecyclerView recyclerView;
    private CardView mCardView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Инициализация переменных
        itemList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        mCardView = findViewById(R.id.thumbnail_card);
        Intent book_intent = getIntent();
        String searchString = book_intent.getStringExtra("search");


        final ProgressDialog dialog;
        dialog = new ProgressDialog(BooksActivity.this);
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.show();
        ApiService api = RetroClient.getApiService();
        String QUERY_PARAM = searchString; // Параметр строки поиска
        int MAX_RESULTS = 6; // Параметр для лимита результатов поиска
        String PRINT_TYPE = "books"; // Параметр для фильтрации по типу печати

        Call<ItemList> call = api.getData(QUERY_PARAM, MAX_RESULTS, PRINT_TYPE);

        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    itemList = response.body().getItems();
                    mAdapter = new BooksAdapter(BooksActivity.this, itemList);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(BooksActivity.this, R.string.no_data, Toast.LENGTH_LONG).toString();
                }
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(BooksActivity.this, R.string.error, Toast.LENGTH_LONG).toString();
            }
            });

    }
}

