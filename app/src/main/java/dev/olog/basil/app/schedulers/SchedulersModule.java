package dev.olog.basil.app.schedulers;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dev.olog.basil.domain.executors.ComputationScheduler;
import dev.olog.basil.domain.executors.IoScheduler;

@Module
public abstract class SchedulersModule {

    @Binds
    @Singleton
    abstract ComputationScheduler provideComputation(ComputationSchedulerImpl impl);

    @Binds
    @Singleton
    abstract IoScheduler provideIo(IoSchedulerImpl impl);

}
