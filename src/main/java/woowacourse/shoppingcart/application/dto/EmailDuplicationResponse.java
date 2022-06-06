package woowacourse.shoppingcart.application.dto;

public class EmailDuplicationResponse {

    private final String email;
    private final Boolean duplicated;

    public EmailDuplicationResponse(String email, Boolean duplicated) {
        this.email = email;
        this.duplicated = duplicated;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getDuplicated() {
        return duplicated;
    }
}
