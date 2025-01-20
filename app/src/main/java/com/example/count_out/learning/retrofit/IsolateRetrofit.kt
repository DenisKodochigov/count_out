package com.example.count_out.learning.retrofit
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.count_out.learning.retrofit.GetUserUseCase.Response
//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Inject
//import jakarta.inject.Singleton
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.http.GET
//import java.util.concurrent.TimeUnit
//
//class IsolateRetrofit {
//}
///** Entity layer*/
//    data class Entity(
//        val field1: String,
//        val field2: String
//    )
///** End entity layer*/
//
///** Use case layer*/
//    interface DataSource {
//        suspend fun getEntity(): Entity
//    }
//    //Here, we have invoked ConcreteDataService and then converted the network model into an entity.
//    class DataRepository @Inject constructor(private val dataSource: DataSource) {
//        suspend fun getEntity(): Entity {
//            return dataSource.getEntity()
//        }
//    }
//    class DataUseCase @Inject constructor(private val dataRepository: DataRepository) {
//        fun getEntity(): Flow<Entity> {
//            return flow { emit( dataRepository.getEntity()) }.flowOn(Dispatchers.IO)
//        }
//    }
///** End use case layer*/
//
///** Interface adapter layer*/
//    @HiltViewModel
//    class MainViewModel @Inject constructor(private val dataUseCase: DataUseCase): ViewModel() {
//        private val _textData: MutableStateFlow<String> = MutableStateFlow("")
//        val textData: StateFlow<String> = _textData.asStateFlow()
//        fun loadConcreteData() {
//            viewModelScope.launch {
//                dataUseCase.getEntity().collect { data -> _textData.value = data.field1 }
//            }
//        }
//    }
//    class DataSourceImpl(private val dataService:DataService): DataSource {
//        override suspend fun getEntity():Entity {
//            val data = dataService.getData()
//            return Entity(data.field1,data.field2)
//        }
//    }
///** End interface adapter layer*/
//
///** Frameworks and drivers layer */
//    @JsonClass(generateAdapter = true) data class Data(
//        @Json(name = "field1") val field1: String,
//        @Json(name = "field1") val field2: String
//    )
//    interface DataService {
//        @GET("/path")
//        suspend fun getData(): Data
//    }
//    @AndroidEntryPoint class MainActivity : ComponentActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContent { Screen() }
//        }
//    }
//    @Composable fun Screen(mainViewModel: MainViewModel = viewModel()){
//        mainViewModel.loadConcreteData()
//        UpdateText(mainViewModel)
//    }
//    @Composable fun UpdateText(mainViewModel: MainViewModel) {
//        val text: String = " Text" //by mainViewModel.textData.collectAsState()
//        Text(text = text)
//    }
///** End frameworks and drivers layer */
//
//@Module
//@InstallIn(SingletonComponent::class)
//class ApplicationModule {
//    @Singleton
//    @Provides
//    fun provideHttpClient(): OkHttpClient {
//        return OkHttpClient
//            .Builder()
//            .readTimeout(15, TimeUnit.SECONDS)
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .build()
//    }
//    @Singleton
//    @Provides
//    fun provideFactory(): MoshiConverterFactory = MoshiConverterFactory.create()
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: MoshiConverterFactory): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("schema://host.com")
//            .client(okHttpClient)
//            .addConverterFactory(gsonConverterFactory)
//            .build()
//    }
//    @Singleton
//    @Provides
//    fun provideService(retrofit: Retrofit): DataService = retrofit.create(DataService::class.java)
//
//    @Singleton
//    @Provides
//    fun provideDataSource(dataService: DataService): DataSource = DataSourceImpl(dataService)
//}
//data class Location(
//    val id: String,
//    val userId: String,
//    val lat: Double,
//    val long: Double
//)
//data class User(
//    val id: String,
//    val firstName: String,
//    val lastName: String,
//    val email: String
//) {
//    fun getFullName() = "$firstName $lastName"
//}
//data class UserWithLocation(val user: User, val location: Location)
//interface LocationRepository {
//    fun getLocation(userId: String): Flow<Location>
//}
//interface UserRepository {
//    fun getUser(id: String): Flow<User>
//}
//class GetUserUseCase1(private val userRepository: UserRepository) {
//    fun getUser(id: String) = userRepository.getUser(id)
//}
//class GetUserWithLocationUseCase1(
//    private val userRepository: UserRepository,
//    private val locationRepository: LocationRepository
//){
//    fun getUser(id: String) =
//        combine(userRepository.getUser(id), locationRepository.getLocation(id)){ user, location ->
//            Result.Success( UserWithLocation(user, location)) as Result<UserWithLocation>
//        }.flowOn(Dispatchers.IO).catch { emit(Result.Error(UseCaseException.extractException(it))) }
//}
//
//sealed class UseCaseException(override val cause: Throwable?): Throwable(cause) {
//    class UserException(    cause: Throwable): UseCaseException(cause)
//    class LocationException(cause: Throwable): UseCaseException(cause)
//    class UnknownException( cause: Throwable): UseCaseException(cause)
//    companion object {
//        fun extractException(throwable: Throwable): UseCaseException {
//            return if (throwable is UseCaseException) throwable else UnknownException(throwable)
//        }
//    }
//}
//sealed class Result< out T: Any> {
//    data class Success <out T: Any>(val data: T): Result<T>()
//    class Error(val exception: UseCaseException): Result<Nothing>()
//}
//
//abstract class UseCase <T: UseCase.Request, R: UseCase.Response>(private val dispatcher: CoroutineDispatcher) {
//    fun execute(input: T): Flow<Result<R>> = executeData(input)
//        .map{ Result.Success(it) as Result<R> }
//        .flowOn(dispatcher)
//        .catch { emit(Result.Error(UseCaseException.extractException(it))) }
//    internal abstract fun executeData(input: T): Flow<R>
//    interface Request
//    interface Response
//}
//
//class GetUserUseCase(dispatcher: CoroutineDispatcher, private val userRepository: UserRepository
//): UseCase<GetUserUseCase.Request, GetUserUseCase.Response>(dispatcher) {
//    override fun executeData(input: Request): Flow<Response> {
//        return userRepository.getUser(input.userId).map { Response(it) } }
//    data class Request(val userId: String): UseCase.Request
//    data class Response(val user: User): UseCase.Response
//}
//
//class GUS(dispatcher: CoroutineDispatcher, private val userRepository: UserRepository){
//    fun executeData(input: Request): Flow<Response>{
//        return userRepository.getUser(input.userId).map { Response(it) } }
//    data class Request(val userId: String)
//    data class Response(val user: User)
//}
//
//class GetLocationUseCase(dispatcher: CoroutineDispatcher, private val locationRepository: LocationRepository
//) : UseCase<GetLocationUseCase.Request, GetLocationUseCase.Response>(dispatcher) {
//    override fun executeData(input: Request): Flow<Response> {
//        return locationRepository.getLocation(input.userId).map { Response(it) } }
//    data class Request(val userId: String): UseCase.Request
//    data class Response(val location: Location): UseCase.Response
//}
//class GetUserWithLocationUseCase(
//    dispatcher: CoroutineDispatcher,
//    private val getUserUseCase: GetUserUseCase,
//    private val getLocationUseCase: GetLocationUseCase
//) : UseCase<GetUserWithLocationUseCase.Request, GetUserWithLocationUseCase.Response>(dispatcher) {
//    override fun executeData(input: Request): Flow<Response> {
//        return combine( getUserUseCase.executeData(GetUserUseCase.Request(input.userId)),
//            getLocationUseCase.executeData(GetLocationUseCase.Request(input.userId)))
//        { userResponse, locationResponse ->
//            Response(UserWithLocation(userResponse.user, locationResponse.location))
//        }
//    }
//    data class Request(val userId: String): UseCase.Request
//    data class Response(val userWithLocation: UserWithLocation): UseCase.Response
//}
//



//abstract class UseCase<T: Any, R: Any>(private val dispatcher: CoroutineDispatcher) {
//    fun execute(input: T): Flow<Result<R>> =
//        executeData(input).map{ Result.Success(it) as Result<R> }.flowOn(dispatcher)
//            .catch { emit(Result.Error(UseCaseException.extractException(it))) }
//    internal abstract fun executeData(input: T): Flow<R>
//}
//class GetUserWithLocationUseCase2(
//    dispatcher: CoroutineDispatcher,
//    private val userRepository: UserRepository,
//    private val locationRepository: LocationRepository
//): UseCase<String, UserWithLocation>(dispatcher) {
//    override fun executeData(input: String): Flow<UserWithLocation> {
//        return combine(userRepository.getUser(input), locationRepository.getLocation(input)){ user, location ->
//            UserWithLocation(user, location)
//        }
//    }
//}

//class GetUserWithLocationUseCase3(
//    dispatcher: CoroutineDispatcher,
//    private val userRepository: UserRepository,
//    private val locationRepository: LocationRepository
//) : UseCase<GetUserWithLocationUseCase.Request, GetUserWithLocationUseCase.Response>(dispatcher) {
//    override fun executeData(input: Request): Flow<Response> {
//        return combine(userRepository.getUser(input.userId), locationRepository.getLocation(input.userId)
//        ) { user, location -> Response(UserWithLocation(user, location)) }
//    }
//    data class Request(val userId: String) : UseCase.Request
//    data class Response(val userWithLocation: UserWithLocation) : UseCase.Response
//}
