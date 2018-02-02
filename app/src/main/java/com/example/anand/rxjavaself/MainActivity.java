package com.example.anand.rxjavaself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView out;
    EditText editText;
    Button buttonclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = findViewById(R.id.textview);
        editText = findViewById(R.id.editText);
        buttonclick = findViewById(R.id.button);




    }

    Observable<String> observableNew = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subscriber.onNext(editText.getText().toString());
            subscriber.onCompleted();
        }
    });

    public void clickbutton(View view) {
        observableNew.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(integerObserver);
    }
    //Observable<Integer> integerObservable = Observable.just(4,5,6,7,8);

    Observer<String> integerObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.d("RXjava-call", "onCompleted: ");
        }

        @Override
        public void onError(Throwable e) {
            Log.d("RXjava-call", "onError: "+ e);
        }

        @Override
        public void onNext(String s) {
            Log.d("RXjava-call", "onNext: "+ s);
            out.setText(s);
        }
    };


}
