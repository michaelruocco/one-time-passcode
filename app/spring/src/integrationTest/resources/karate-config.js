function fn() {
    var port = karate.properties["server.port"];
    var config = {
        baseUrl : "http://localhost:" + port,
        authToken : karate.properties["auth.token"]
    };
    return config;
}