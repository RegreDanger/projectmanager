package domain.services;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class TokenService {
    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutos en milisegundos
    private Map<String, TokenData> verificationDataMap = new HashMap<>();

    private class TokenData {
        private String code;
        private long expirationTime;

        public TokenData(String code, long expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        public String getCode() {
            return code;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
    
    public String generateVerificationCode(String email) {
        Random random = new Random();
        String verificationCode = String.valueOf(100000 + random.nextInt(900000));

        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME; // Establecer tiempo de expiración
        
        verificationDataMap.put(email, new TokenData(verificationCode, expirationTime));

        return verificationCode;
    }
    
    public boolean verifyCode(String email, String inputCode) {
        TokenData data = verificationDataMap.get(email);

        if (data == null || System.currentTimeMillis() > data.getExpirationTime()) {
            verificationDataMap.remove(email);
            return false;
        }
        
        if (data.getCode().equals(inputCode)) {
            verificationDataMap.remove(email);
            return true;
        }

        return false;
    }
    
    public boolean isCodeActive(String email) {
        TokenData data = verificationDataMap.get(email);
        return data != null && System.currentTimeMillis() <= data.getExpirationTime();
    }
}
