package dev.olog.basil.app.schedulers;

import javax.inject.Inject;

import dev.olog.basil.domain.executors.IoScheduler;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IoSchedulerImpl implements IoScheduler {

    @Inject IoSchedulerImpl(){

    }

    @Override
    public Scheduler getWorker() {
        return Schedulers.io();
    }

    @Override
    public Scheduler getUi() {
        return AndroidSchedulers.mainThread();
    }


}
