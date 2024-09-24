package com.game.scrabble.utilities;

import java.io.File;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.scrabble.model.Letter;

public class JsonReader {
	private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

	public Map<String, Letter> readLetters() {
		Map<String, Letter> result = null;
		try {
			File file = new File(this.getClass().getClassLoader().getResource("letters.json").getFile());
			ObjectMapper mapper = new ObjectMapper();

			JSONObject obj = mapper.readValue(file, JSONObject.class);
			if (obj.containsKey("letters")) {
				HashMap letters = (HashMap) obj.get("letters");
				result = toLettersMap(letters);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return result;
	}

	private Map<String, Letter> toLettersMap(HashMap letters) throws Exception {
		Map<String, Letter> map = new HashMap<String, Letter>();
		Set keys = letters.keySet();
		try {
			for (Object key : keys) {
				Object value = letters.get(key);
				if (value instanceof Map && key instanceof String) {
					String keyStr = (String)key;
					Letter x = new Letter((Map) value);
					x.setKey(keyStr.charAt(0));
					map.put((String) key, x);
				}
			}
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
}