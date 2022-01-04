package com.board.adapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
	
	@Override				// 객채 -> JSON
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src));
	}
	
	@Override				// JSON -> 객체
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
