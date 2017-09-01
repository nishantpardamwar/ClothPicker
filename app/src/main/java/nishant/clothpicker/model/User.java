package nishant.clothpicker.model;

/**
 * Created by serious on 2/9/17.
 */

public class User {
    private String name;
    private String email;

    private User(Builder builder) {
        name = builder.name;
        email = builder.email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static final class Builder {
        private String name;
        private String email;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
