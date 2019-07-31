package com.mihir.searchmovies.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.mihir.searchmovies.MyApplication;
import com.mihir.searchmovies.R;
import com.mihir.searchmovies.databinding.MainActivityBinding;
import com.mihir.searchmovies.utils.Constant;
import com.mihir.searchmovies.rest.ViewModelFactory;
import com.mihir.searchmovies.viewmodel.PagingMovieViewModel;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by mihir on 26-06-2019.
 */
public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    PagingMovieViewModel viewModel;

    MainActivityBinding binding;
    MovieAdapter adapter;

    @BindView(R.id.searchMovie)
    EditText searchMovie;
    private int TRIGGER_SERACH = 2;
    private long SEARCH_TRIGGER_DELAY_IN_MS = 1000;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIGGER_SERACH) {
                viewModel.replaceSubscription(MainActivity.this, searchMovie.getText().toString());
                viewModel.getListLiveData().observe(MainActivity.this, adapter::submitList);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PagingMovieViewModel.class);
        init();
    }

    private void init() {

        binding.list.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        adapter = new MovieAdapter();
        binding.list.setAdapter(adapter);

        if (!Constant.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), Constant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }

        viewModel.getListLiveData().observe(this, adapter::submitList);

        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                binding.progress.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                binding.progress.setVisibility(View.GONE);
            }
        });

    }

    @OnTextChanged(R.id.searchMovie)
    public void onTextChanged(Editable editable) {
        handler.removeMessages(TRIGGER_SERACH);
        handler.sendEmptyMessageDelayed(TRIGGER_SERACH, SEARCH_TRIGGER_DELAY_IN_MS);
    }
}