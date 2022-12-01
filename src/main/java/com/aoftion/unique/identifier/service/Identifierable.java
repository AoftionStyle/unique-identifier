package com.aoftion.unique.identifier.service;

public interface Identifierable<T, K> {
    /**
     * @param source would like encrypt process
     * @return result of encryption
     */
    public T generate(K val) throws Exception;


    /**
     * @param encrypt would like decrypt process
     * @return result as origin
     */
    public T validate(K val) throws Exception;
}
