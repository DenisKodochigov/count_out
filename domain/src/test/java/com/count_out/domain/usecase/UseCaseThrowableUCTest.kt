package com.count_out.domain.usecase

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class UseCaseThrowableUCTest {
    @ExperimentalCoroutinesApi
    private val configuration = UseCase.Configuration(UnconfinedTestDispatcher())
    private val request = mock<UseCase.Request>()
    private val response = mock<UseCase.Response>()
    private val result = mock<ResultDomain<Domain>>()
    @ExperimentalCoroutinesApi
    private lateinit var useCase: UseCase<UseCase.Request, UseCase.Response>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun method(request: Request): Flow<ResultDomain<Domain>> = flowOf(result)
            override fun response(result: Domain): Response = response
        }
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteSuccess() = runTest {
        val result = useCase.execute(request).first()
        assertEquals(ResultDomain.Success(response), result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteTrainingThrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun method(request: Request): Flow<ResultDomain<Domain>> = flowOf(result)
            override fun response(result: Domain): Response  = response
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultDomain.Error).throwable is ThrowableUC.TrainingThrow)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteActivityThrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun method(request: Request): Flow<ResultDomain<Domain>> = flowOf(result)
            override fun response(result: Domain): Response  = response
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultDomain.Error).throwable is ThrowableUC.ActivityThrow)
        }
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteWeatherTrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun method(request: Request): Flow<ResultDomain<Domain>> = flowOf(result)
            override fun response(result: Domain): Response  = response
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultDomain.Error).throwable is ThrowableUC.WeatherTrow)
        }
    }
}