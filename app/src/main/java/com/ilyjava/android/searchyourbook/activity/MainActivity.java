package com.ilyjava.android.searchyourbook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
import android.view.View;
        import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyjava.android.searchyourbook.R;
import com.ilyjava.android.searchyourbook.utils.InternetConnection;

public class MainActivity extends AppCompatActivity {

    // Переменная поля поиска книги
    private EditText mBookInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация переменной
        mBookInput = findViewById(R.id.bookInput);
    }

    public void searchBooks(View view) {
        // Получает текст, который нужно найти
        String queryString = mBookInput.getText().toString();
        // Переход в BooksActivity
        if (InternetConnection.checkConnection(getApplicationContext()) && queryString.length()!=0) {
            Intent book_intent = new Intent(MainActivity.this, BooksActivity.class);
            book_intent.putExtra("search", queryString);
            startActivity(book_intent);
        }  else {
            if (queryString.length() == 0) {
                Toast.makeText(MainActivity.this, R.string.no_search_term, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
            }
        }
        // Убирает клавиатуру при нажатии кнопки
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}