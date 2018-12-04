package dev.olog.basil.domain.executors;

import io.reactivex.Scheduler;

public interface Schedulers {

    Scheduler getWorker();
    Scheduler getUi();

}
