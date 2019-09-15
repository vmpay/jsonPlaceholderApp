package eu.vmpay.jsonplaceholder.utils

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun main(): Scheduler
}