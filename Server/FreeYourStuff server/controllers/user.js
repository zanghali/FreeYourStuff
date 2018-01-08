const { Pool, Client } = require('pg');
const config = require('../config/db');

module.exports = {

    addUser: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "INSERT INTO usr (lastname,firstname,email) VALUES ($1, $2, $3)";
            let userdetails = [data.lastname, data.firstname, data.email];

            client.query(query, userdetails, function (err, result) {
                done();
                callback(err == null);
            });
        })
        pool.end()
    },
    getUserByEmail: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT id_user FROM usr WHERE email=$1";
            let userdetails = [data.email];
            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows[0]);
                else
                callback(false);
            });
        })
        pool.end()
    }
    
}