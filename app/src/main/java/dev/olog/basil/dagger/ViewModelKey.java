package dev.olog.basil.dagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

@MapKey
@Target({ElementType.METHOD})
public @interface  ViewModelKey {
    Class<? extends ViewModel> value();
}
