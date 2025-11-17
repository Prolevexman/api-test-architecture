package prolevexman.testdata;

public class TestUsers {

    private static CredentialsProvider PROVIDER = new EnvCredentialsProvider();

    private TestUsers() {}

    public static User admin() {
        return PROVIDER.getUser("admin", 1);
    }

    public static User admin(int index) {
        return PROVIDER.getUser("admin", index);
    }

    public static User supplier() {
        return PROVIDER.getUser("supplier", 1);
    }

    public static User supplier(int index) {
        return PROVIDER.getUser("supplier", index);
    }

    public static User partner() {
        return PROVIDER.getUser("partner", 1);
    }

    public static User partner(int index) {
        return PROVIDER.getUser("partner", index);
    }

    public static User user(String type, int index) {
        return PROVIDER.getUser(type, index);
    }

}
