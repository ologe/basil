package dev.olog.basil.app.schedulers;

import javax.inject.Inject;

import dev.olog.basil.domain.executors.ComputationScheduler;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComputationSchedulerImpl implements ComputationScheduler {

    @Inject
    ComputationSchedulerImpl() {
    }

    @Override
    public Scheduler getWorker() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler getUi() {
        return AndroidSchedulers.mainThread();
    }
}
