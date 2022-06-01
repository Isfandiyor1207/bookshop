package epam.project.bookshop.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseDomain{

    String firstName;

    String lastName;

    String username;

    String password;

    String email;

    String phoneNumber;

    Long roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(roleId, user.roleId);
    }

    @Override
    public int hashCode() {

        int result = 1;

            result = 31 * result + (firstName == null ? 0 : firstName.hashCode());
            result = 31 * result + (lastName == null ? 0 : lastName.hashCode());
            result = 31 * result + (username == null ? 0 : username.hashCode());
            result = 31 * result + (email == null ? 0 : email.hashCode());
            result = 31 * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
            result = 31 * result + (password == null ? 0 : password.hashCode());
            result = 31 * result + (roleId == null ? 0 : roleId.hashCode());
            result = 31 * super.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("email='" + email + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("roleId=" + roleId)
                .add(super.toString())
                .toString();
    }
}

