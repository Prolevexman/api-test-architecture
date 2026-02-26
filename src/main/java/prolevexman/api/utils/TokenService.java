package prolevexman.api.utils;

import prolevexman.api.models.request.login.LoginRequest;
import prolevexman.api.services.AuthService;
import prolevexman.api.session.SessionContext;
import prolevexman.api.session.SessionManager;

public class TokenService {

    private final AuthService authService;
    private final LoginRequest loginRequest = new LoginRequest("vdsup@dev.test", "Sdev-_-79]?");

    public TokenService(AuthService authService) {
        this.authService = authService;
    }

    public String getToken() {

        SessionContext session = SessionManager.session();

        if (session.getToken() == null) {
            String token = authService.loginS(loginRequest);

            session.setToken(token);
        }
        return session.getToken();
    }
}
