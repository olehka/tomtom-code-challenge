package com.tomtom.codechallenge.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tomtom.codechallenge.data.FetchResult;
import com.tomtom.codechallenge.util.TestUtils;
import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.viewmodels.DocumentListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DocumentListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DataRepository repository;
    private DocumentListViewModel viewModel;

    @Before
    public void init() {
        repository = mock(DataRepository.class);
        MutableLiveData<List<Document>> liveData = new MutableLiveData<>();
        liveData.postValue(TestUtils.generateDocumentList());
        when(repository.getAllDocuments()).thenReturn(liveData);
        viewModel = new DocumentListViewModel(repository);
    }

    @Test
    public void testNotNull() {
        assertThat(viewModel.getDocuments(), notNullValue());
        assertThat(viewModel.getResult(), notNullValue());
    }

    @Test
    public void dontFetchWithoutObserversTest() {
        verify(repository, never()).fetchDocumentsByQuery(anyString());
        verify(repository, never()).fetchDocumentsByTitle(anyString());
        verify(repository, never()).fetchDocumentsByAuthor(anyString());
    }

    @Test
    public void searchByQueryTest() {
        String query = "foo";
        Observer<FetchResult> observer = mock(Observer.class);
        viewModel.getResult().observeForever(observer);
        viewModel.searchByQuery(query);
        verify(repository).fetchDocumentsByQuery(query);
        verify(repository).getAllDocuments();
    }

    @Test
    public void searchByTitleTest() {
        String title = "bar";
        Observer<FetchResult> observer = mock(Observer.class);
        viewModel.getResult().observeForever(observer);
        viewModel.searchByTitle(title);
        verify(repository).fetchDocumentsByTitle(title);
        verify(repository).getAllDocuments();
    }

    @Test
    public void searchByAuthorTest() {
        String author = "dou";
        Observer<FetchResult> observer = mock(Observer.class);
        viewModel.getResult().observeForever(observer);
        viewModel.searchByAuthor(author);
        verify(repository).fetchDocumentsByAuthor(author);
        verify(repository).getAllDocuments();
    }
}
