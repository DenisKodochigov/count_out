//package com.example.data_repository.repository
//
//import com.example.data_repository.data_source.local.LocalInteractionDataSource
//import com.example.domain.entity.Interaction
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.whenever
//
//class InteractionRepositoryImplTest {
//
//    private val localInteractionDataSource = mock<LocalInteractionDataSource>()
//    private val repositoryImpl = InteractionRepositoryImpl(localInteractionDataSource)
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testGetInteraction() = runBlockingTest {
//        val interaction = Interaction(10)
//        whenever(localInteractionDataSource.getInteraction()).thenReturn(flowOf(interaction))
//        val result = repositoryImpl.getInteraction().first()
//        assertEquals(interaction, result)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testSaveInteraction() = runBlockingTest {
//        val interaction = Interaction(10)
//        whenever(localInteractionDataSource.getInteraction()).thenReturn(flowOf(interaction))
//        val result = repositoryImpl.saveInteraction(interaction).first()
//        verify(localInteractionDataSource).saveInteraction(interaction)
//        assertEquals(interaction, result)
//    }
//}