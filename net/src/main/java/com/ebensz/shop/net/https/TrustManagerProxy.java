package com.ebensz.shop.net.https;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


final public class TrustManagerProxy implements X509TrustManager {
    private static final List<BigInteger> BLACKLIST = new LinkedList<BigInteger>() {{
        add(new BigInteger("4098"));
    }};
    private final X509TrustManager x509;

    public static X509TrustManager init() {
        return init(KeyStoreData.keyStore, KeyStoreData.password);
    }

    public static X509TrustManager init(byte[] keyStoreData, char[] keyStorePassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance("BKS");

            keyStore.load(new ByteArrayInputStream(keyStoreData), keyStorePassword);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    return new TrustManagerProxy((X509TrustManager) trustManager);
                }
            }
            throw new AssertionError("no X509TrustManager");
        } catch (Throwable e) {
            throw new AssertionError(e);
        }
    }

    private TrustManagerProxy(X509TrustManager trustManagers) {
        x509 = trustManagers;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return x509.getAcceptedIssuers();
    }
}
