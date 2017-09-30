package com.mftest.security;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Class that manipulates JWT tokens.
 *
 */
public class JWTToken {
	private static String key = "mftest_APP";

	/**
	 * Creates the Token.
	 * @param subject The user login asking for a token.
	 * @param userId User id in the system.
	 * @param userRole Id representing the user role in the system.
	 * @return The token.
	 */
	public static String create(String user, int userId, int userRole) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("user", user);
		claims.put("userId", userId);
		claims.put("userRole", userRole);
		
		return Jwts.builder().
				setSubject(user).
				setClaims(claims).
				signWith(SignatureAlgorithm.HS512, key).compact();
	}

	/**
	 * Validates the Token decoding it.<br><br>
	 * The Claims contain the following data:<br>
	 * - <b>getBody().get("user")</b> user login<br>
	 * - <b>getBody().get("userId")</b> user Id in the system<br>
	 * - <b>getBody().get("userRole")</b> user role Id in the system<br><br>
	 * If the token is not valid, throws a SignatureException.
	 * @param token Token sent by the client.
	 * @return The claims in the body of the token.
	 */
	public static Jws<Claims> decode(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
}
