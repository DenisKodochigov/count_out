package com.count_out.domain

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
    @ExperimentalCoroutinesApi
    private lateinit var useCase: UseCase<UseCase.Request, UseCase.Response>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun executeData(input: Request): Flow<Response> {
                assertEquals(this@UseCaseThrowableUCTest.request, input)
                return flowOf(response)
            }
        }
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteSuccess() = runTest {
        val result = useCase.execute(request).first()
        assertEquals(ResultUC.Success(response), result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteTrainingThrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun executeData(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseThrowableUCTest.request, request)
                return flow { throw ThrowableUC.TrainingThrow(Throwable()) }
            }
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultUC.Error).throwable is ThrowableUC.TrainingThrow)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteActivityThrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun executeData(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseThrowableUCTest.request, request)
                return flow { throw ThrowableUC.ActivityThrow(Throwable()) }
            }
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultUC.Error).throwable is ThrowableUC.ActivityThrow)
        }
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteWeatherTrow() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun executeData(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseThrowableUCTest.request, request)
                return flow { throw ThrowableUC.WeatherTrow(Throwable()) }
            }
        }
        runTest {
            val result = useCase.execute(request).first()
            Assert.assertTrue((result as ResultUC.Error).throwable is ThrowableUC.WeatherTrow)
        }
    }
}