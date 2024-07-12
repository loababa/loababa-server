package com.loababa.api.auth.domain.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.loababa.api.auth.domain.impl.model.AccessToken;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.JWTProperties;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import com.loababa.api.auth.exception.AuthClientErrorInfo;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.auth.ui.AuthCredential;
import com.loababa.api.common.exception.ServerErrorInfo;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Auth0JWTManager implements JWTManager {

    private static final String OAUTH_USER_ID = "oauthUserId";
    private static final String MEMBER_ID = "memberId";

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final JWTProperties jwtProperties;

    private final RefreshTokenWriter refreshTokenWriter;

    public Auth0JWTManager(
            JWTProperties jwtProperties, RefreshTokenWriter refreshTokenWriter
    ) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.secretKey());
        this.refreshTokenWriter = refreshTokenWriter;
        this.verifier = JWT.require(algorithm).build();
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AuthToken generate(AuthCredential authCredential) {
        Instant now = Instant.now();
        AccessToken accessToken = generateAccessToken(authCredential, now);
        RefreshToken refreshToken = generateRefreshToken(authCredential, now);

        AuthToken authToken = new AuthToken(accessToken, refreshToken);
        refreshTokenWriter.save(authToken.refreshToken());
        return authToken;
    }

    private AccessToken generateAccessToken(AuthCredential authCredential, Instant now) {
        return new AccessToken(
                createToken(authCredential, now, jwtProperties.accessTokenExpirationTimeInSec())
        );
    }

    private RefreshToken generateRefreshToken(AuthCredential authCredential, Instant now) {
        return new RefreshToken(
                createToken(authCredential, now, jwtProperties.refreshTokenExpirationTimeInSec())
        );
    }

    private String createToken(AuthCredential authCredential, Instant now, int expirationTime) {
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirationTime))
                .withClaim(OAUTH_USER_ID, authCredential.oauthUserId())
                .withClaim(MEMBER_ID, authCredential.memberId())
                .sign(algorithm);
    }

    @Override
    public AuthCredential extractClaims(String token) {
        DecodedJWT decodedJWT = verify(token);

        Long memberId = decodedJWT.getClaim(MEMBER_ID).asLong();
        Long oauthId = decodedJWT.getClaim(OAUTH_USER_ID).asLong();
        return new AuthCredential(oauthId, memberId);
    }

    @Override
    public DecodedJWT verify(String token) {
        token = parseToken(token);

        try {
            return verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new InvalidTokenException(
                    AuthClientErrorInfo.EXPIRED_TOKEN,
                    new ServerErrorInfo(e, "토큰이 만료되었습니다.")
            );
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(
                    AuthClientErrorInfo.INVALID_TOKEN,
                    new ServerErrorInfo(e, "유효하지 않은 토큰입니다.")
            );
        }
    }

    private String parseToken(String token) {
        String[] split = token.split(" ");
        token = split[1];
        return token;
    }

}

