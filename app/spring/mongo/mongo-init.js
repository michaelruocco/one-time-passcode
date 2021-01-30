db.createUser(
    {
        user: "idv",
        pwd: "welcome01",
        roles: [ { role: "readWrite", db: "idv-local" } ]
    }
);

db.createCollection("otpVerification");
db.otpVerification.createIndex({ "timestamp": 1 }, { expireAfterSeconds: 360 });