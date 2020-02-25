package com.tomtom.codechallenge.util;

import android.graphics.Bitmap;

import com.tomtom.codechallenge.data.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {

    public static List<Document> generateDocumentList() {
        List<Document> documentList = new ArrayList<>();
        documentList.add(new Document("1", "abc", Collections.emptyList(), Collections.emptyList()));
        documentList.add(new Document("2", "def", Collections.emptyList(), Collections.emptyList()));
        documentList.add(new Document("3", "ghi", Collections.emptyList(), Collections.emptyList()));
        return documentList;
    }

    public static Document generateDocument(String id) {
        return new Document(id, "abc", Collections.emptyList(), Collections.emptyList());
    }

    public static Bitmap generateBitmap1x1() {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        return Bitmap.createBitmap(1, 1, config);
    }
}
