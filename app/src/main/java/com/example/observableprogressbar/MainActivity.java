package com.example.observableprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button btn;
    ProgressBar progressBar;
    ReactiveManager reactiveManager;
    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
        progressBar=findViewById(R.id.progressBar);
        reactiveManager=new ReactiveManager();
        compositeDisposable=new CompositeDisposable();

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);

                    Disposable disposable = Observable.zip(reactiveManager.progressCounter(Integer.parseInt(et.getText().toString())),
                            Observable.interval(1, TimeUnit.SECONDS), (d, f) -> d)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new DisposableObserver<Integer>() {
                                @Override
                                public void onNext(Integer integer) {
                                    tv.setText("Progress:\n" + integer + " of " + et.getText().toString());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onComplete() {
                                    tv.setText(R.string.onCompleteTvText);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    tv.setVisibility(View.INVISIBLE);
                                }
                            });
                    compositeDisposable.add(disposable);

                }
            }
        });
    }
}
