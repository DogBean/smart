package com.ebensz.shop.net.https;




import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class Authorization {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    public static final String UTF8 = "utf-8";
    public final String timestamp;
    public final String appId;
    public final String sig;

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public String user() {
        return appId;
    }

    public String password() {
        return sig;
    }

    @Override
    public String toString() {
        return "appId:" + appId + ", timestamp:" + timestamp + ", sig:" + sig;
    }

    private Authorization(String appId, String timestamp, String sig) {
        this.timestamp = timestamp == null || timestamp.isEmpty() ? null : timestamp;
        this.appId = appId == null || appId.isEmpty() ? null : appId;
        this.sig = sig == null || sig.isEmpty() ? null : sig;
    }

    public static String yyyyMMddHHmmss() {
        return yyyyMMddHHmmss(new Date());
    }

    public static String yyyyMMddHHmmss(Date date) {
        return sdf.format(date);
    }

    public static Date date(String yyyyMMddHHmmss) {
        try {
            return sdf.parse(yyyyMMddHHmmss);
        } catch (ParseException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * user:password
     * appId,timestamp:sig
     * @param authorization AuthorizationHeader
     * @return
     * @throws InvalidAuthorizationException
     */
    public static Authorization from(String authorization) throws InvalidAuthorizationException {
        final String START = "Basic ";
        if (authorization == null || !authorization.startsWith(START)) {
            throw new InvalidAuthorizationException("Bad authorization:" + authorization);
        }

        try {
            byte[] auth = authorization.substring(START.length()).getBytes(UTF8);
            String content = new String(Base64.decode(auth));
            if (content.isEmpty()) {
                throw new InvalidAuthorizationException("Bad decoded authorization:" + authorization);
            }

            String[] credentials = content.split(":");
            if (credentials == null || credentials.length < 2) {
                throw new InvalidAuthorizationException("Badly formated credentials:" + content);
            }

            String[] parts = credentials[0].split(",");
            return from(parts[0], parts.length > 1 ? parts[1] : null, credentials[1]);
        } catch (Throwable e) {
            throw new InvalidAuthorizationException("Bad Base64 authorization:" + authorization, e);
        }
    }

    public static Authorization from(String user, String password) throws InvalidAuthorizationException {
        return new Authorization(user,null,  password);
    }

    public static Authorization from(String appId, String timestamp, String sig) throws InvalidAuthorizationException {
        return new Authorization(appId, timestamp, sig);
    }

    public static String sig(String appId, String appToken, String timestamp) {
        return md5(appId + appToken + timestamp);
    }

    public static String authorizationYzx(String accountId, String timestamp) {
        try {
            return Base64.encodeBytes((accountId + ":" + timestamp).getBytes(UTF8));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String authorization(String user, String password) {
        return "Basic " + authorizationYzx(user, password);
    }

    public static String authorization(String appId, String token, String timestamp) {
        return authorization(appId + "," + timestamp, sig(appId, token, timestamp));
    }

    public static String auth(String appId, String token) {
        return authorization(appId, token, yyyyMMddHHmmss());
    }

    public static String md5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(src.getBytes(UTF8));
            return byte2HexStr(b);
        } catch (Throwable e) {
            throw new AssertionError(e);
        }
    }

    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }

    public static final class InvalidAuthorizationException  extends Exception {
        public InvalidAuthorizationException() {
            super();
        }

        public InvalidAuthorizationException(String message) {
            super(message);
        }

        public InvalidAuthorizationException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidAuthorizationException(Throwable cause) {
            super(cause);
        }
    }
}