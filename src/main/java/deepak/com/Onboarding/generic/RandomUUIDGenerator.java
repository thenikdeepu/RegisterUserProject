package deepak.com.Onboarding.generic;

import java.util.UUID;

public class RandomUUIDGenerator {
	
	public static String genrateUUID(String prefix) {
		return prefix+UUID.randomUUID().toString();
	}

}
