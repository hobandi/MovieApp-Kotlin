package pl.kfert.movie.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.repository.DetailRepository

class DetailFragmentViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    lateinit var detailRepository: DetailRepository

    lateinit var detailFragmentViewModel : DetailFragmentViewModel

    lateinit var movie : Movie


    @Before fun beforeTest()
    {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMovie() {
        movie = Movie("1")
        detailFragmentViewModel = DetailFragmentViewModel(detailRepository, movie, false)
        var movies  =  MutableLiveData<DataResult<Movie>>()
        given(detailRepository.getMovie("1", false)).willReturn(movies)


        Assert.assertEquals(detailFragmentViewModel.movie, movies)
    }

    @Test
    fun checkIfIsFavorite() = runBlockingTest {
        movie = Movie("1")

        detailFragmentViewModel = DetailFragmentViewModel(detailRepository, movie, false)

        detailFragmentViewModel.setFavorite(true)

        Assert.assertTrue(movie.isFavorite!!)

        verify(detailRepository).updateDB(movie)
        detailFragmentViewModel.favoriteChanged.observeForever { }
        Assert.assertEquals(detailFragmentViewModel.favoriteChanged.value, true)
    }

}