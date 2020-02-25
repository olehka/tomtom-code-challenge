package com.tomtom.codechallenge.network;

import android.graphics.Bitmap;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.tomtom.codechallenge.data.Document;
import com.tomtom.codechallenge.data.network.ApiResponse;
import com.tomtom.codechallenge.data.network.ApiService;
import com.tomtom.codechallenge.data.network.ApiServiceImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ApiServiceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ApiService apiService;
    private MockHttpClient mockHttpClient;

    @Before
    public void createService() {
        mockHttpClient = MockHttpClient.getInstance();
        apiService = ApiServiceImpl.getInstance(mockHttpClient);
    }

    @Test
    public void getDocumentsByQueryTest() {
        mockHttpClient.setFileName("search-query.json");
        String query = "The Lord Of The Rings";
        ApiResponse<List<Document>> apiResponse = apiService.getDocumentsByQuery(query);
        assertThat(apiResponse.hasError(), is(false));
        List<Document> documentList = apiResponse.getBody();
        assertThat(documentList, notNullValue());
        assertThat(documentList.size(), is(100));
        assertThat(documentList.get(0).getTitle().toLowerCase(), containsString(query.toLowerCase()));
    }

    @Test
    public void getDocumentsByTitleTest() {
        mockHttpClient.setFileName("search-title.json");
        String title = "The Lord Of The Rings";
        ApiResponse<List<Document>> apiResponse = apiService.getDocumentsByTitle(title);
        assertThat(apiResponse.hasError(), is(false));
        List<Document> documentList = apiResponse.getBody();
        assertThat(documentList, notNullValue());
        assertThat(documentList.size(), is(100));
        assertThat(documentList.get(0).getTitle().toLowerCase(), containsString(title.toLowerCase()));
    }

    @Test
    public void getDocumentsByAuthorTest() {
        mockHttpClient.setFileName("search-author.json");
        String author = "Tolkien";
        ApiResponse<List<Document>> apiResponse = apiService.getDocumentsByAuthor(author);
        assertThat(apiResponse.hasError(), is(false));
        List<Document> documentList = apiResponse.getBody();
        assertThat(documentList, notNullValue());
        assertThat(documentList.size(), is(100));
        assertThat(documentList.get(0).getAuthor().toLowerCase(), containsString(author.toLowerCase()));
    }

    @Test
    public void getIsbnCoverImageTest() {
        mockHttpClient.setFileName("OL229501A-M.jpg");
        String isbnValue = "OL229501A";
        ApiResponse<Bitmap> apiResponse = apiService.getIsbnCoverImage(isbnValue, "M");
        assertThat(apiResponse.hasError(), is(false));
        Bitmap bitmap = apiResponse.getBody();
        assertThat(bitmap, notNullValue());
        assertThat(bitmap.getWidth(), not(0));
        assertThat(bitmap.getHeight(), not(0));
    }
}