package User;


import com.github.javafaker.Faker;

public class UserGenerator {
    static Faker faker = new Faker();

    public User random() {
        return new User(faker.name().username() + "@yandex.ru", faker.animal().name(), faker.name().name());
    }

    public static String generateEmail() {
        return String.format(faker.name().username() + "@yandex.ru");
    }
}
