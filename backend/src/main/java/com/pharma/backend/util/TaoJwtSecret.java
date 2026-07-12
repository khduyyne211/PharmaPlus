package com.pharma.backend.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class TaoJwtSecret {
    public static void main(String[] args) {
        SecretKey khoaBiMat = Jwts.SIG.HS256.key().build();

        String khoaBiMatBase64 = Encoders.BASE64.encode(
            khoaBiMat.getEncoded()
        );

        System.out.println("JWT SECRET:");
        System.out.println(khoaBiMatBase64);
    }
}
