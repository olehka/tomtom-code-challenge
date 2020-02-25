package com.tomtom.codechallenge.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tomtom.codechallenge.util.InstantAppExecutors;
import com.tomtom.codechallenge.util.TestUtils;
import com.tomtom.codechallenge.data.DataRepository;
import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.FetchResult;
import com.tomtom.codechallenge.data.db.AppDatabase;
import com.tomtom.codechallenge.data.db.DocumentDao;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class DataRepositoryTest {

    private DataRepository dataRepository;
    private DocumentDao documentDao;
    private ApiService apiService;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        AppDatabase database = mock(AppDatabase.class);
        when(database.documentDao()).thenReturn(documentDao);
        documentDao = mock(DocumentDao.class);
        apiService = mock(ApiService.class);
        dataRepository = new DataRepository(documentDao, apiService, new InstantAppExecutors());
    }

    @Test
    public void getDocumentByIdTest() {
        String documentId = "16";
        Document document = TestUtils.generateDocument(documentId);
        Observer<Document> observer = mock(Observer.class);
        MutableLiveData<Document> dbResult = new MutableLiveData<>();
        when(documentDao.getDocument(documentId)).thenReturn(dbResult);
        dataRepository.getDocumentById(documentId).observeForever(observer);
        dbResult.postValue(document);
        verify(observer).onChanged(document);
        verifyZeroInteractions(apiService);
    }

    @Test
    public void getAllDocumentsTest() {
        List<Document> documents = TestUtils.generateDocumentList();
        Observer<List<Document>> observer = mock(Observer.class);
        MutableLiveData<List<Document>> dbResult = new MutableLiveData<>();
        when(documentDao.getAllDocuments()).thenReturn(dbResult);
        dataRepository.getAllDocuments().observeForever(observer);
        dbResult.postValue(documents);
        verify(observer).onChanged(documents);
        verifyZeroInteractions(apiService);
    }

    @Test
    public void fetchDocumentsWithErrorTest() {
        String query = "tom";
        Observer<FetchResult> observer = result ->
                assertThat(result.hasError(), equalTo(true));
        ApiResponse<List<Document>> apiResponse = ApiResponse.error("ijk");
        when(apiService.getDocumentsByQuery(query)).thenReturn(apiResponse);
        dataRepository.fetchDocumentsByQuery(query).observeForever(observer);
    }

    @Test
    public void fetchDocumentsByQueryTest() {
        String query = "foo";
        List<Document> documentList = TestUtils.generateDocumentList();
        ApiResponse<List<Document>> apiResponse = ApiResponse.success(documentList);
        when(apiService.getDocumentsByQuery(query)).thenReturn(apiResponse);
        dataRepository.fetchDocumentsByQuery(query);
        verify(documentDao).deleteAllDocuments();
        verify(documentDao).saveDocuments(documentList);
    }

    @Test
    public void fetchDocumentsByTitleTest() {
        String title = "bar";
        List<Document> documentList = TestUtils.generateDocumentList();
        ApiResponse<List<Document>> apiResponse = ApiResponse.success(documentList);
        when(apiService.getDocumentsByTitle(title)).thenReturn(apiResponse);
        dataRepository.fetchDocumentsByTitle(title);
        verify(documentDao).deleteAllDocuments();
        verify(documentDao).saveDocuments(documentList);
    }

    @Test
    public void fetchDocumentsByAuthorTest() {
        String author = "dou";
        List<Document> documentList = TestUtils.generateDocumentList();
        ApiResponse<List<Document>> apiResponse = ApiResponse.success(documentList);
        when(apiService.getDocumentsByAuthor(author)).thenReturn(apiResponse);
        dataRepository.fetchDocumentsByAuthor(author);
        verify(documentDao).deleteAllDocuments();
        verify(documentDao).saveDocuments(documentList);
    }
}
