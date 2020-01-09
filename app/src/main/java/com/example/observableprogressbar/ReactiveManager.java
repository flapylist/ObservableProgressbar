package com.example.observableprogressbar;

import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observable;

public class ReactiveManager {

    public Observable<Integer> progressCounter(Integer num){
        return Observable.create(e ->{

            for(int i=1; i<=num; i++){
                e.onNext(i);
            }
            e.onComplete();
        });
    }
}
