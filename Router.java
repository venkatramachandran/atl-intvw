import java.util.*;
import java.util.regex.Pattern;

interface IRouter {
    void withRoute(String path, String result);
    String route(String path);
}

class Router implements IRouter {
    @Override
    public void withRoute(String path, String result) throws IllegalArgumentException {
        // error handling
        if (routes.containsKey(path))
            throw new IllegalArgumentException("path "+path+" already exists");
        routes.put(path.replace("*", ".*").replace("/", "\\/"), result);
    }

    @Override
    public String route(String path) {
        String retVal = routes.get(path);
        if (retVal != null) {
            return retVal;
        }
        for (String input : routes.keySet()) {
            if (path.matches(input)) {
                return routes.get(input);
            }
        }
        return null;
    }

    private static Map<String,String> routes;

    private static Router router;
    private Router() {
        if (routes == null) {
            routes = new HashMap<>();
        }
    }

    static {
        if (router == null) {
            router = new Router();
        }
    }

    public static Router getInstance() {
        return router;
    }

    public static void main(String[] args) {
        Router router = Router.getInstance();
        router.withRoute("/abc/1/def", "first");
        router.withRoute("/abc/*/def", "second");
        router.withRoute("/bar", "bar");
        router.withRoute("/xyz/*/123/*/def", "3");
        

        System.out.printf("/bar is mapped to %s, must be bar\n", router.route("/bar"));
        System.out.printf("/abc/1/def is mapped to %s, must be first\n", router.route("/abc/1/def"));
        System.out.printf("/abc/xyz/def is mapped to %s, must be second\n", router.route("/abc/xyz/def"));
        System.out.printf("/abc/xyz/1/def is mapped to %s, must be second\n", router.route("/abc/xyz/1/def"));
        System.out.printf("/xyz/ttt/123/ss/def is mapped to %s, must be 3\n", router.route("/xyz/ttt/123/ss/def"));
    }
}

