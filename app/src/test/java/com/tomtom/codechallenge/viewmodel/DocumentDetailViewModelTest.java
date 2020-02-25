package com.tomtom.codechallenge.viewmodel;

import android.graphics.Bitmap;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tomtom.codechallenge.concurrent.TaskRunner;
import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;
import com.tomtom.codechallenge.util.InstantAppExecutors;
import com.tomtom.codechallenge.util.TestUtils;
import com.tomtom.codechallenge.viewmodels.DocumentDetailViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class DocumentDetailViewModelTest {

    private static final String DOCUMENT_ID = "16";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DataRepository repository;
    private TaskRunner taskRunner;
    private DocumentDetailViewModel viewModel;

    @Before
    public void init() {
        repository = mock(DataRepository.class);
        taskRunner =  new TaskRunner(new InstantAppExecutors());
        MutableLiveData<Document> liveData = new MutableLiveData<>();
        liveData.postValue(TestUtils.generateDocument(DOCUMENT_ID));
        when(repository.getDocumentById(DOCUMENT_ID)).thenReturn(liveData);
        viewModel = new DocumentDetailViewModel(DOCUMENT_ID, repository, taskRunner);
    }

    @Test
    public void testNotNull() {
        assertThat(viewModel.getDocument(), notNullValue());
        assertThat(viewModel.getBitmapList(), notNullValue());
    }

    @Test
    public void getDocumentTest() {
        Observer<Document> observer = mock(Observer.class);
        viewModel.getDocument().observeForever(observer);
        verify(repository).getDocumentById(DOCUMENT_ID);
    }

    @Test
    public void getBitmapToFromMemoryCacheTest() {
        String isbn = "0011";
        String size = "S";
        Bitmap bitmap = TestUtils.generateBitmap1x1();
        Observer<Bitmap> observer = mock(Observer.class);
        ApiService apiService = mock(ApiService.class);
        ApiResponse<Bitmap> apiResponse = ApiResponse.success(bitmap);
        when(repository.getApiService()).thenReturn(apiService);
        when(repository.getApiService().getIsbnCoverImage(isbn, size)).thenReturn(apiResponse);
        viewModel.getBitmapList().get(0).observeForever(observer);
        viewModel.loadIsbnImage(1, isbn, size);
        verify(repository).addBitmapToMemoryCache(isbn, bitmap);
        verify(repository).getBitmapFromMemoryCache(isbn);
    }
}
