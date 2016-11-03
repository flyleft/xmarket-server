package me.jcala.xmarket.server.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicCreds {
    final String kid;
    final String b64UrlPublicKey;

    @JsonCreator
    public PublicCreds(@JsonProperty("kid") String kid, @JsonProperty("b64UrlPublicKey") String b64UrlPublicKey) {
        this.kid = kid;
        this.b64UrlPublicKey = b64UrlPublicKey;
    }

    public String getKid() {
        return kid;
    }

    public String getB64UrlPublicKey() {
        return b64UrlPublicKey;
    }
}
