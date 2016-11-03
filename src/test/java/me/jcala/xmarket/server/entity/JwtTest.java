package me.jcala.xmarket.server.entity;


import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.EllipticCurves;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JwtTest {

    @Test
   public void testJWT() throws Exception{
         EllipticCurveJsonWebKey senderJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);

         // Give the JWK a Key ID (kid), which is just the polite thing to do
         senderJwk.setKeyId("sender's key");


         // Generate an EC key pair, wrapped in a JWK, which will be used for encryption and decryption of the JWT
         EllipticCurveJsonWebKey receiverJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);

         // Give the JWK a Key ID (kid), which is just the polite thing to do
         receiverJwk.setKeyId("receiver's key");

         // Create the Claims, which will be the content of the JWT
         JwtClaims claims = new JwtClaims();
         claims.setIssuer("sender");  // who creates the token and signs it
         claims.setAudience("receiver"); // to whom the token is intended to be sent
         claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
         claims.setGeneratedJwtId(); // a unique identifier for the token
         claims.setIssuedAtToNow();  // when the token was issued/created (now)
         claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
         claims.setSubject("subject"); // the subject/principal is whom the token is about
         claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
         List<String> groups = Arrays.asList("group-1", "other-group", "group-3");
         claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

         // A JWT is a JWS and/or a JWE with JSON claims as the payload.
         // In this example it is a JWS nested inside a JWE
         // So we first create a JsonWebSignature object.
         JsonWebSignature jws = new JsonWebSignature();

         // The payload of the JWS is JSON content of the JWT Claims
         jws.setPayload(claims.toJson());

         // The JWT is signed using the sender's private key
         jws.setKey(senderJwk.getPrivateKey());

         // Set the Key ID (kid) header because it's just the polite thing to do.
         // We only have one signing key in this example but a using a Key ID helps
         // facilitate a smooth key rollover process
         jws.setKeyIdHeaderValue(senderJwk.getKeyId());

         // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
         jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

         // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
         // representation, which is a string consisting of three dot ('.') separated
         // base64url-encoded parts in the form Header.Payload.Signature
         String innerJwt = jws.getCompactSerialization();

         // The outer JWT is a JWE
         JsonWebEncryption jwe = new JsonWebEncryption();

         // The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
         jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);

         // The content encryption key is used to encrypt the payload
         // with a composite AES-CBC / HMAC SHA2 encryption algorithm
         String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
         jwe.setEncryptionMethodHeaderParameter(encAlg);

         // We encrypt to the receiver using their public key
         jwe.setKey(receiverJwk.getPublicKey());
         jwe.setKeyIdHeaderValue(receiverJwk.getKeyId());

         // A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
         jwe.setContentTypeHeaderValue("JWT");

         // The inner JWT is the payload of the outer JWT
         jwe.setPayload(innerJwt);

         // Produce the JWE compact serialization, which is the complete JWT/JWE representation,
         // which is a string consisting of five dot ('.') separated
         // base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
         String jwt = jwe.getCompactSerialization();


         // Now you can do something with the JWT. Like send it to some other party
         // over the clouds and through the interwebs.
         System.out.println("JWT: " + jwt);


         // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
         // be used to validate and process the JWT.
         // The specific validation requirements for a JWT are context dependent, however,
         // it typically advisable to require a expiration time, a trusted issuer, and
         // and audience that identifies your system as the intended recipient.
         JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                 .setRequireExpirationTime() // the JWT must have an expiration time
                 .setRequireSubject() // the JWT must have a subject claim
                 .setExpectedIssuer("sender") // whom the JWT needs to have been issued by
                 .setExpectedAudience("receiver") // to whom the JWT is intended for
                 .setDecryptionKey(receiverJwk.getPrivateKey()) // decrypt with the receiver's private key
                 .setVerificationKey(senderJwk.getPublicKey()) // verify the signature with the sender's public key
                 .build(); // create the JwtConsumer instance

         try
         {
              //  Validate the JWT and process it to the Claims
              JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
              System.out.println("JWT validation succeeded! " + jwtClaims);
         }
         catch (InvalidJwtException e)
         {
              // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
              // Hopefully with meaningful explanations(s) about what went wrong.
              System.out.println("Invalid JWT! " + e);
         }
    }
}
