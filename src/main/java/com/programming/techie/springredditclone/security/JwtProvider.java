package com.programming.techie.springredditclone.security;


import com.programming.techie.springredditclone.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;


@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/spring-reddit-clone.jks");
			keyStore.load(resourceAsStream, "reddit12345".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occurred while loading keystore");
		}
	}

	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("spring-reddit-clone", "reddit12345".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore");
		}
	}
	
	 public boolean validateToken(String jwt) {
	        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
	        return true;
	    }
	
	private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("spring-reddit-clone").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception occured while " +
                    "retrieving public key from keystore", e);
        }
    }
}