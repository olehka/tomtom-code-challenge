package com.tomtom.codechallenge.data.db;

import android.util.Log;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DocumentTypeConverters {

    @TypeConverter
    public static String fromListToString(List<String> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Log.e("DocumentTypeConverters", "Error: " + e.getMessage());
            return "";
        }
    }

    @TypeConverter
    public static List<String> fromStringToList(String string) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(string, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Log.e("DocumentTypeConverters", "Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
