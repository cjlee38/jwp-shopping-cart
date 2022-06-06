package woowacourse.auth.support;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtAttribute {

    @Size(min = 80, message = "토큰 키의 길이가 너무 짧습니다.")
    private String secretKey;
    @Positive(message = "토큰 만료 시간은 양수여야 합니다.")
    private Long expireLength;

    public static JwtAttribute of(String secretKey, Long expireLength) {
        JwtAttribute attribute = new JwtAttribute();
        attribute.setSecretKey(secretKey);
        attribute.setExpireLength(expireLength);
        return attribute;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getExpireLength() {
        return expireLength;
    }

    public void setExpireLength(Long expireLength) {
        this.expireLength = expireLength;
    }
}
