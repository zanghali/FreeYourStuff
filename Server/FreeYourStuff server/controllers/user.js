const { Pool, Client } = require('pg');
const config = require('../config/db');

module.exports = {

    addUser: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "INSERT INTO usr (lastname,firstname,email) VALUES ($1,$2,$3) ON CONFLICT (email) DO NOTHING RETURNING id_user";
            let userdetails = [data.lastname, data.firstname, data.email];

            client.query(query, userdetails, function (err, result) {
                done();
 
                if(err==null)
                {
                    callback(result.rows);
                }
                callback(null);
            });
        })
        pool.end()
    },
    getUserByEmail: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM usr WHERE email=$1";
            let userdetails = [data.email];
            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    setUserInterestedByItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "INSERT INTO user_interested_by_item (id_user,id_item,date) VALUES ($1,$2,current_timestamp)";
            let userdetails = [data.id_user,data.id_item];
            

            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                {
                  
                    callback(true);
                }
                else
                {
                callback(false);
                }
            });
        })
        pool.end()
    },

    getUserInterestByItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT usr.id_user,user_interested_by_item.date,usr.firstname,usr.lastname,usr.email FROM user_interested_by_item LEFT JOIN usr ON usr.id_user=user_interested_by_item.id_user WHERE user_interested_by_item.id_item=$1"
            let userdetails = [data.id_item];
            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },
    getNumberInterestByItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT COUNT(*) FROM user_interested_by_item WHERE user_interested_by_item.id_item=$1"
            let userdetails = [data.id_item];
            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(false);
            });
        })
        pool.end()
    },

    deleteUserInterestByItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "DELETE FROM user_interested_by_item WHERE id_item=$1 AND id_user=$2";
            let userdetails = [data.id_item, data.id_user];

            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null && result.rowCount==1)
                {
                callback(true)
                }
                else
                callback(false);
            });
        })
        pool.end()
    },

    getUserList: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })
        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM usr";
            client.query(query, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    getUserById: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * from usr WHERE id_user=$1";
            let userdetails = [data.id_user];

            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null)
                {
                callback(result.rows)
                }
                else
                callback(false);
            });
        })
        pool.end()
    },


    updateUser: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })
        pool.connect(function (err, client, done) {
            let query = "UPDATE usr SET firstname = $1, lastname =$2 WHERE email=$3"
            let userdetails = [data.firstname,data.lastname,data.email];
            client.query(query,userdetails, function (err, result) {
                done();
                if(err==null && result.rowCount==1)
                callback(true);
                else
                callback(false);
            });
        })
        pool.end()
    }
    
}