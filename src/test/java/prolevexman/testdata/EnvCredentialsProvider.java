package prolevexman.testdata;

import prolevexman.ui.utils.ConfigReader;

public class EnvCredentialsProvider implements CredentialsProvider {

    @Override
    public User getUser(String type, int index) {
        String email = ConfigReader.getEnvCredential(type, "email", index);
        String pass = ConfigReader.getEnvCredential(type, "pass", index);
        return new User(email, pass);
    }
}
