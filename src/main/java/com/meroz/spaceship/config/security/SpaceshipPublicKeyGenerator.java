package com.meroz.spaceship.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.config.exception.SpaceshipEnumError;
import com.meroz.spaceship.exception.GeneralSecurityException;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SpaceshipPublicKeyGenerator {

	public static RSAPublicKey createPublicRSAKey(String publicKey) {
		byte[] decoded = Base64.getDecoder().decode(publicKey.getBytes());
		EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

		try {
			return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new GeneralSecurityException(ErrorsEnum.SECURITY_EXCEPTION, new Object[]{e.getMessage()});
		}
	}

	public static RSAPublicKey createPublicRSAKeyFromUri(String uri) throws JsonProcessingException {
		JsonNode jsonNode = (new ObjectMapper()).readTree((new RestTemplate()).getForObject(uri, String.class));

		return createPublicRSAKey(jsonNode.get("value").asText().replaceAll("-----BEGIN PUBLIC KEY----- \\s*(.*?)\\s*-----END PUBLIC KEY-----", "$1"));
	}
}
