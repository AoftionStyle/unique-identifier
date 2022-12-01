package com.aoftion.unique.identifier.util.cryptography;

public interface Cryptographyable<T, V> {
    T encrypt(V v) throws Exception;
    V decrypt(T t) throws Exception;
}
