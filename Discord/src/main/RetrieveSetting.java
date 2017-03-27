package main;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class RetrieveSetting {

	enum JSONDocument {
		secret, setting;
	}

	public static String getKey(String key, JSONDocument fileSort){

		if(fileSort==JSONDocument.secret){
			try {
				String prefix=";";
				JSONParser parser = new JSONParser();
				Object object = null;

				if(System.getProperty("os.name").toLowerCase().contains("windows")){
					object = parser.parse(new FileReader("C:\\Users\\Glenn\\Documents\\DiscordBot\\Secret.json"));
				}
				else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
					object =  parser.parse(new FileReader("/home/pi/DiscordBot/Secret.json"));
				}

				JSONObject jsonObject = (JSONObject) object;

				return (String) jsonObject.get(key);

			} catch (Exception e) {
				IO.Logg(e, "Error in fetching from secret file", "String key: --"+key+"--", null);
			}
		}
		else if (fileSort==JSONDocument.setting) {
			try {
				String prefix=";";
				JSONParser parser = new JSONParser();
				Object object = null;

				object = parser.parse(new FileReader("Files/settings.json"));

				JSONObject jsonObject = (JSONObject) object;

				return (String) jsonObject.get(key);

			} catch (Exception e) {
				IO.Logg(e, "Error in fetching from settings file", "String key: --"+key+"--", null);
			}
		}


		return null;
	}
	@SuppressWarnings({ "unchecked"})
	public static void setKey(String key, String value){


		try {
			JSONParser parser = new JSONParser();
			Object object = null;

			object = parser.parse(new FileReader("Files/settings.json"));
			
			JSONObject jsonObject = (JSONObject) object;
			
			jsonObject.put(key, value);
			
			try (FileWriter file = new FileWriter("Files/settings.json")){
				file.write(jsonObject.toJSONString());
				IO.print("Successfully wrote {\""+key+"\":\""+value+"\"}", false);
			}
			
		} catch (Exception e) {
			IO.Logg(e, "setKey in RetrieveSettings", "Trying to setKey in settings.json", null);

		}
	}

}