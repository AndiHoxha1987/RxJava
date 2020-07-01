package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.rxjava.model.Note;
import com.example.rxjava.utils.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private int i = 0;
    private TextView showText;
    private List<Note> noteList;
    private List<Note> emptyList = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showText = findViewById(R.id.show_text);
        noteList = DataSource.createDataList();

        //createSampleWithFromIterable();
        //createSampleWithJust();
        //createSampleWithCreate();
        //createSampleWithRangeAndRepeat();
        //createSampleWithInterval();
        //createSampleWithTimer();
        //createSampleWithFromArray();
        //createSampleWithFromCallable();
        //createSampleWithFilter();
        //createSampleWithDistinct();
        //createSampleWithTake();
        //createSampleWithTakeWhile();
        //createSampleWithMap();
        //createSampleWithMapGetString();
        //createSampleWithBuffer();
        //createSampleWithDebounce();
        //createSampleWithThrottleFirst();
        //createSampleWithDefaultIfEmpty();
        //createSampleWithSwitchIfEmpty();
        //createSampleWithSingle();
        //createSampleWithMaybe();
        //createSampleWithSkip();
        //createSampleWithSkipWhile();
        //createSampleWithScan();
        //createSampleWithSort();
        //createSampleWithFlatMap();
        //createSampleWithConcatMap();
        //createSampleWithSwitchMap();
    }

    private void createSampleWithFromIterable() {
        /**
         * use-cases
         * To emit an arbitrary number of items that are known upfront.
         * Same as the fromArray() operator but it's an iterable.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithJust() {
        /**
         * use-cases
         * The 'just()' operator should be used if you want to create a single Observable.
         * The Just() operator has the ability to accept a list of up to 10 entries.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .just(noteList.get(1), noteList.get(4))// apply 'just' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithCreate() {
        /**
         * use-cases
         * The 'create()' operator should be used if you want to create a single Observable.
         */
        Observable<Note> noteObservable = Observable
                .create((ObservableOnSubscribe<Note>) emitter -> {

                    // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                    for (Note note : noteList) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(note);
                        }
                    }
                    // Once the loop is complete, call the onComplete() method
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscribe(noteObservable);
    }

    private void createSampleWithRangeAndRepeat() {
        /**
         * use-cases
         * range() and repeat() are great for replacing loops or any iterative processes / methods.
         * You can do the work on a background thread and observe the results on the main thread.
         */
        Observable<Integer> noteObservable = Observable // create a new Observable object
                .range(0, noteList.size())// apply 'range' operator
                .repeat(2)// apply 'repeat' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        noteObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                String text = integer.toString()+"\n";
                showText.append(text);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithScan() {

        Observable<Integer> noteObservable = Observable // create a new Observable object
                .range(0, noteList.size())// apply 'range' operator
                .scan(3,(accumulator,next)->accumulator + next)// apply 'scan' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        noteObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                String text = integer.toString()+"\n";
                showText.append(text);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithInterval(){
        /**
         * use-cases
         * The Interval operator returns an Observable that emits an infinite sequence of ascending integers,
         * with a constant interval of time of your choosing between emissions.
         * I'm using the takeWhile() operator to perform a check to each emitted value.
         * If the value becomes greater than 5, the observable stops emitting results.
         */
        Observable<Long> intervalObservable = Observable
                .interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() { // stop the process if aLong is bigger than 5
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong <= 5;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        intervalObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }
            @Override
            public void onNext(Long aLong) {
                String text = aLong.toString()+"\n";
                showText.append(text);
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithSort(){
        Observable intervalObservable = Observable
                .just(1,4,3,2,5)
                .sorted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        intervalObservable.subscribe(new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                String text = o.toString();
                showText.append(text);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithTimer(){
        /**
         * use-cases
         * emit single observable after a given delay
         */
        Observable<Long> timeObservable = Observable
                .timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        timeObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }
            @Override
            public void onNext(Long aLong) {
                String text = aLong.toString()+"\n";
                showText.setText(text);
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithFromArray(){
        /**
         * use-cases
         * To emit an arbitrary number of items that are known upfront.
         */

        Note[] notes =  new Note[5];
        notes[0] = noteList.get(0);
        notes[1] = noteList.get(1);
        notes[2] = noteList.get(2);
        notes[3] = noteList.get(3);
        notes[4] = noteList.get(4);

        Observable<Note> noteObservable = Observable
                .fromArray(notes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscribe(noteObservable);
    }

    private void createSampleWithFromCallable() {
        /**
         * use-cases
         * To generate a single Observable item on demand.
         * Like calling a method to retrieve some objects or a list of objects.
         * fromCallable() will execute a block of code (usually a method) and return a result.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromCallable(new Callable<Note>() {
                    @Override
                    public Note call() throws Exception {
                        return noteList.get(3);
                    }
                })// apply 'fromCallable' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithFilter() {
        /**
         * use-cases
         * When you have a list of custom objects and want to filter based on a specific field
         * and the list is very large so the filtering should probably be done on a background thread.
         *
         * If that happens to you frequently, the filter() operator will become your new best friend.
         */
        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .filter(new Predicate<Note>() {
                    @Override
                    public boolean test(Note note) throws Throwable {
                        return note.getName().equals("Work");
                    }
                })//apply filter operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithDistinct() {
        /**
         * use-cases
         * The Distinct operator filters an Observable by only allowing items through that
         * have not already been emitted.
         * But what defines the object as "distinct" is up to the developer to determine
         * and we can't compare objects but only variables
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .distinct(new Function<Note, String>() {
                    @Override
                    public String apply(Note note) throws Throwable {
                        return note.getName();
                    }
                })//apply distinct operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithTake() {
        /**
         * use-cases
         * take() and takeWhile() fall into the filters category. They are similar to the filter()
         * operator in that they filter through lists of objects.
         *
         * The take() operator will emit only the first 'n' items emitted by an Observable and then complete
         * while ignoring the remaining items.
         *
         * The main difference between the take() operators and the filter() operator is that the filter()
         * operator will check every object in the list. So you could say the filter() operator is inclusive.
         *
         * Whereas the take() operators would be considered exclusive because they don't necessary check
         * every item in the list. They will emit objects only until the condition of their function is
         * satisfied.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .take(3)//apply take operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithSkip() {
        /**
         * use-cases
         * Reverse of take() and takeWhile()
         *
         * take() and takeWhile() fall into the filters category. They are similar to the filter()
         * operator in that they filter through lists of objects.
         *
         * The take() operator will emit only the first 'n' items emitted by an Observable and then complete
         * while ignoring the remaining items.
         *
         * The main difference between the take() operators and the filter() operator is that the filter()
         * operator will check every object in the list. So you could say the filter() operator is inclusive.
         *
         * Whereas the take() operators would be considered exclusive because they don't necessary check
         * every item in the list. They will emit objects only until the condition of their function is
         * satisfied.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .skip(3)//apply skip operator for first 3 items
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithSkipWhile() {
        /**
         * use-cases
         * Reverse of take() and takeWhile()
         *
         * take() and takeWhile() fall into the filters category. They are similar to the filter()
         * operator in that they filter through lists of objects.
         *
         * The take() operator will emit only the first 'n' items emitted by an Observable and then complete
         * while ignoring the remaining items.
         *
         * The main difference between the take() operators and the filter() operator is that the filter()
         * operator will check every object in the list. So you could say the filter() operator is inclusive.
         *
         * Whereas the take() operators would be considered exclusive because they don't necessary check
         * every item in the list. They will emit objects only until the condition of their function is
         * satisfied.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .skipWhile(new Predicate<Note>() {
                    @Override
                    public boolean test(Note note) throws Exception {
                        return note.getName().length()<10;
                    }
                })//apply skipWhile operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithTakeWhile() {
        /**
         * use-cases
         * take() and takeWhile() fall into the filters category. They are similar to the filter()
         * operator in that they filter through lists of objects.
         *
         * The TakeWhile() mirrors the source Observable until such time as some condition you specify becomes false.
         * If the condition becomes false, TakeWhile() stops mirroring the source Observable and terminates its own Observable.
         *
         * The main difference between the take() operators and the filter() operator is that the filter()
         * operator will check every object in the list. So you could say the filter() operator is inclusive.
         *
         * Whereas the take() operators would be considered exclusive because they don't necessary check
         * every item in the list. They will emit objects only until the condition of their function is
         * satisfied.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .takeWhile(new Predicate<Note>() {
                    @Override
                    public boolean test(Note note) throws Exception {
                        return note.getName().length()<10;
                    }
                })//apply takeWhile operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithBuffer() {
        /**
         * use-cases
         * Periodically gather items from an Observable into bundles and emit the bundles rather
         * than emitting items one at a time.
         *
         * The first and most obvious application of the Buffer() operator is bundling emitted objects into groups.
         */

        Observable<Note> noteObservable = Observable
                .fromIterable(noteList)
                .subscribeOn(Schedulers.io());

        noteObservable
                .buffer(2) // Apply the Buffer() operator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Note>>() { // Subscribe and view the emitted results
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<Note> notes) {
                        showText.append("onNext: bundle results: -------------------\n");
                        for(Note note: notes){
                            String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() + "\n\n";
                            showText.append(text);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void createSampleWithDebounce() {
        /**
         * use-cases
         * The Debounce operator filters out items emitted by the source Observable that are rapidly followed
         * by another emitted item.
         *
         * Suppose you have a SearchView in your app. As the user enters characters into the SearchView,
         * you want to perform queries on the server. If you don't limit the capturing of the characters
         * to a time period, a new request will be made every time they enter a new character into
         * the SearchView. Typically this is unnecessary and would yield undesirable results.
         * Executing a new search every 0.5 seconds or so is probably fine.
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .debounce(100,TimeUnit.MILLISECONDS)//apply debounce operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithThrottleFirst() {
        /**
         * use-cases
         * The ThrottleFirst() operator filters out items emitted by the source Observable that are within a timespan.
         *  If a user is spamming a button, You don't want to register every click.
         *  You can use the ThrottleFirst() operator to only register new click events every time interval.
         */
        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .throttleFirst(1,TimeUnit.MILLISECONDS)//Throttle the clicks so 500 ms must pass before registering a new click
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithDefaultIfEmpty() {
        /**
         * use-cases
         * if a call for data gave us an  empty list we can add a default item to not let the user to be blank
         */

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(emptyList) // apply 'fromIterable' operator
                .defaultIfEmpty(new Note("a","b","c"))//
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithSwitchIfEmpty() {
        /**
         * use-cases
         * if a call for data gave us an empty list we can switch to another list of items
         */

        Observable<Note> secondObservable = Observable // create a new Observable object
                .fromIterable(emptyList) // apply 'fromIterable' operator
                .defaultIfEmpty(new Note("a","b","c"))//
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(emptyList) // apply 'fromIterable' operator
                .switchIfEmpty(secondObservable)
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithSingle(){
        Single
                .just(new Note("A","b","c"))
                .subscribe(new SingleObserver<Note>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Note note) {
                        String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() + "\n\n";
                        showText.append(text);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void createSampleWithMaybe(){
        Maybe
                .just(noteList)
                .subscribe(new MaybeObserver<List<Note>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Note> noteList) {
                        for(Note note:noteList){
                            String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() + "\n\n";
                            showText.append(text);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void createSampleWithMap() {
        /**
         * use-cases
         * Consider using Map operator where there is an offline operations needs to be done on emitted data.
         * We got something from server but that doesn't fulfils our requirement.
         * In that case, Map can be used to alter the emitted data.
         *
         * Applies a function to each emitted item. It transforms each emitted item by applying a function to it.
         */
        Observable<Note> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        note.setName("Test");
                        return note;
                    }
                })//apply map operator
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        subscribe(noteObservable);
    }

    private void createSampleWithMapGetString() {
        /**
         * use-cases
         * Consider using Map operator where there is an offline operations needs to be done on emitted data.
         * We got something from server but that doesn't fulfils our requirement.
         * In that case, Map can be used to alter the emitted data.
         *
         * Applies a function to each emitted item. It transforms each emitted item by applying a function to it.
         */

        Observable<String> noteObservable = Observable // create a new Observable object
                .fromIterable(noteList) // apply 'fromIterable' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .map(new Function<Note, String>() {
                    @Override
                    public String apply(Note note) throws Exception {
                        note.setDescription("Test");
                        return note.getDescription();
                    }
                })//apply map operator
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread

        noteObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull String string) {
                showText.append(string+"\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void createSampleWithFlatMap() {
        /**
         * use-cases
         * Choose FlatMap when the order is not important. Let’s say you are building a Airline Ticket Fair
         * app that fetches the prices of each airline separately and display on the screen.
         * For this both FlatMap and ConcatMap can be used. But if the order is not important and
         * want to send all the network calls simultaneously, I would consider FlatMap over ConcatMap.
         * If you consider ConcatMap in this scenario, the time takes to fetch the prices takes very
         * longer time as the ConcatMap won’t make simultaneous calls in order to maintain item order.
         */
        Observable // create a new Observable object
                .create((ObservableOnSubscribe<Note>) emitter -> {

                    // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                    for (Note note : noteList) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(note);
                        }
                    }
                    // Once the loop is complete, call the onComplete() method
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }

                })
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread())// designate observer thread
                .flatMap(new Function<Note, Observable<Note>>() {

                    @Override
                    public Observable<Note> apply(Note note) throws Exception {

                        // getting each note tag by making another network call
                        return getTagObservable(note);
                    }
                })//apply flatMap operator
                .subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Note note) {
                String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() +"\n" + note.getTag() + "\n\n";
                showText.append(text);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    private void createSampleWithConcatMap() {
        /**
         * use-cases
         * Choose FlatMap when the order is not important. Let’s say you are building a Airline Ticket Fair
         * app that fetches the prices of each airline separately and display on the screen.
         * For this both FlatMap and ConcatMap can be used. But if the order is not important and
         * want to send all the network calls simultaneously, I would consider FlatMap over ConcatMap.
         * If you consider ConcatMap in this scenario, the time takes to fetch the prices takes very
         * longer time as the ConcatMap won’t make simultaneous calls in order to maintain item order.
         */

        Observable // create a new Observable object
                .create((ObservableOnSubscribe<Note>) emitter -> {

                    // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                    for (Note note : noteList) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(note);
                        }
                    }
                    // Once the loop is complete, call the onComplete() method
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }

                })
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread())// designate observer thread
                .concatMap(new Function<Note, Observable<Note>>() {

                    @Override
                    public Observable<Note> apply(Note note) throws Exception {

                        // getting each note tag by making another network call
                        return getTagObservable(note);
                    }
                })//apply concatMap operator
                .subscribe(new Observer<Note>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Note note) {
                        String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() +"\n" + note.getTag() + "\n\n";
                        showText.append(text);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void createSampleWithSwitchMap() {
        /**
         * use-cases
         * SwitchMap is best suited when you want to discard the response and consider the latest one.
         * Let’s say you are writing an Instant Search app which sends search query to server each time
         * user types something. In this case multiple requests will be sent to server with multiple queries,
         * but we want to show the result of latest typed query only. For this case,
         * SwitchMap is best operator to use.
         * Another use case of SwitchMap is, you have a feed screen in which feed is refreshed each time
         * user perform pulldown to refresh. In this scenario, SwitchMap is best suited as it can ignores
         * the older feed response and consider only the latest request.
         */
        Observable // create a new Observable object
                .create((ObservableOnSubscribe<Note>) emitter -> {

                    // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                    for (Note note : noteList) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(note);
                        }
                    }
                    // Once the loop is complete, call the onComplete() method
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }

                })
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .observeOn(AndroidSchedulers.mainThread())// designate observer thread
                .switchMap(new Function<Note, Observable<Note>>() {

                    @Override
                    public Observable<Note> apply(Note note) throws Exception {

                        // getting each note tag by making another network call
                        return getTagObservable(note);
                    }
                })//apply concatMap operator
                .subscribe(new Observer<Note>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Note note) {
                        String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() +"\n" + note.getTag() + "\n\n";
                        showText.append(text);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private Observable<Note> getTagObservable(final Note note) {

        final String[] tag = new String[]{
                "1111",
                "2222",
                "3333",
                "4444",
                "5555"
        };

        return Observable
                .create(new ObservableOnSubscribe<Note>() {
                    @Override
                    public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            note.setTag(tag[i]);
                            i++;

                            emitter.onNext(note);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }


    private void subscribe(Observable<Note> noteObservable){
        noteObservable.subscribe(new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Note note) {
                String text = note.getName() + "\n" + note.getDescription() + "\n" + note.getContentType() + "\n\n";
                showText.append(text);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
