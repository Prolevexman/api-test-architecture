package prolevexman.api.session;

public class SessionManager {

    private static final ThreadLocal<SessionContext> SESSION = ThreadLocal.withInitial(SessionContext::new);

    public static SessionContext session() {
        return SESSION.get();
    }

    public static void clear() {
        SESSION.remove();
    }
}
