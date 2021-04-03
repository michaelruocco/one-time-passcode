function fn() {
    var port = karate.properties["server.port"];
    var authToken = karate.properties["auth.token"];
    var config = {
        baseUrl : "http://localhost:" + port,
        authToken : authToken
    };
    return config;
}