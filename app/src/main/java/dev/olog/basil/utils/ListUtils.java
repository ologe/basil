package dev.olog.basil.utils;

import com.github.dmstocking.optional.java.util.Optional;

import java.util.List;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class ListUtils {

    @NonNull
    @CheckResult
    public static <In, Out> List<Out> map(List<In> input, Function<In, Out> mapper){

        return Observable.fromIterable(input)
                .map(mapper)
                .toList()
                .blockingGet();
    }

    @Nullable
    @CheckResult
    public static <T> Optional<T> find(List<T> input, Predicate<T> predicate){
        return Observable.fromIterable(input)
                .filter(predicate)
                .map(Optional::of)
                .first(Optional.empty())
                .blockingGet();
    }

}
