package com.example.domain

import com.example.domain.repository.training.ActivityRepo
import com.example.domain.repository.training.TrainingRepo
import com.example.domain.use_case.UseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class UseCaseTest {
    @ExperimentalCoroutinesApi
    private val configuration = UseCase.Configuration(StandardTestDispatcher())
    private val request = mock<UseCase.Request>()
    private val response = mock<UseCase.Response>()
    @ExperimentalCoroutinesApi
    private lateinit var useCase: UseCase<UseCase.Request, UseCase.Response>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        useCase = object: UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun executeData(input: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, input)
                return flowOf(response)
            }
        }
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteSuccess() = runTest {
        val result = useCase.execute(request).first()
        assertEquals(com.example.domain.entity.throwable.Result.Success(response), result)
    }
}



class GetPostsWithUsersWithInteractionUseCaseTest {
    private val postRepository = mock<TrainingRepo>()
    private val userRepository = mock<ActivityRepo>()
//    private val interactionRepository = mock<InteractionRepository>()
//    private val useCase = GetPostsWithUsersWithInteractionUseCase(
//            mock(),
//            postRepository,
//            userRepository,
//            interactionRepository
//        )

//    @ExperimentalCoroutinesApi
//    @Test
//    fun testProcess() = runBlockingTest {
//        val user1 = User(1L, "name1", "username1", "email1")
//        val user2 = User(2L, "name2", "username2", "email2")
//        val post1 = Post(1L, user1.id, "title1", "body1")
//        val post2 = Post(2L, user1.id, "title2", "body2")
//        val post3 = Post(3L, user2.id, "title3", "body3")
//        val post4 = Post(4L, user2.id, "title4", "body4")
//        val interaction = Interaction(10)
//        whenever(userRepository.getUsers()).thenReturn(flowOf(listOf(user1, user2)))
//        whenever(postRepository.getPosts()).thenReturn(flowOf(listOf(post1, post2, post3, post4)))
//        whenever(interactionRepository.getInteraction()).thenReturn(flowOf(interaction))
//        val response = useCase.process(GetPostsWithUsersWithInteractionUseCase.Request).first()
//        assertEquals(GetPostsWithUsersWithInteractionUseCase.Response(
//                listOf(
//                    PostWithUser(post1, user1),
//                    PostWithUser(post2, user1),
//                    PostWithUser(post3, user2),
//                    PostWithUser(post4, user2),
//                ), interaction
//            ),
//            response
//        )
//    }
}