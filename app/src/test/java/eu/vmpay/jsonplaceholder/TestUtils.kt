package eu.vmpay.jsonplaceholder

import eu.vmpay.jsonplaceholder.utils.SchedulerProvider
import io.reactivex.Scheduler
import org.mockito.Mockito

fun <T> any(): T {
    return Mockito.any()
}

class TestSchedulerProvider(private val testScheduler: Scheduler) : SchedulerProvider {

    override fun computation() = testScheduler

    override fun io() = testScheduler

    override fun main() = testScheduler
}