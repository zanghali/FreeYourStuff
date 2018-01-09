const { Pool, Client } = require('pg');
const config = require('../config/db');

module.exports = {

    addItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "INSERT INTO item (category,title,description,photo,address,phone,status,creation_date,gps,availability,id_user) VALUES ($1,$2,$3,$4,$5,$6,$7,current_timestamp,$8,$9,$10)";
            let userdetails = [data.category, data.title, data.description, data.photo,data.address,data.phone,data.status,data.gps,data.availability,data.id_user];

            client.query(query, userdetails, function (err, result) {
                done();
                callback(err==null);
            });
        })
        pool.end()
    },

    getItemList: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM ITEM";
        
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
    getItemById: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM item WHERE id_item=$1";
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

    getItemByKeywords: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM item WHERE CONCAT(description,' ',title) @@ to_tsquery($1)";
            let keyword= data.keywords.split(' ');
            let userdetails="'";

            keyword.forEach(function(element) {
                userdetails+=element+' & ';
            }, this);

            userdetails=[userdetails.substring(0,userdetails.length-3)+"'"];

            client.query(query,userdetails, function (err, result) {
                done();
                console.log(result);
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },
    

    deleteItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "DELETE FROM item WHERE id_item=$1 AND id_user=$2";
            let userdetails = [data.id_item, data.id_user];

            client.query(query, userdetails, function (err, result) {
                done();
                if(err==null && result.rowCount==1)
                callback(true)
                else
                callback(false);
            });
        })
        pool.end()
    }
    

    
    // getMatchsByUsername: function (data, callback) {
    //     const pool = new Pool({
    //         connectionString: config.connectionString,
    //     })

    //     pool.connect(function (err, client, done) {
    //         let query = "SELECT u.username, u.surname, u.lastname, u.birthdate, u.email, m.second_username, m.date, m.latitude, m.longitude FROM matchs m LEFT JOIN users u ON m.second_username = u.username WHERE m.first_username = $1";
    //         let userdetails = [data.username];

    //         client.query(query, userdetails, function (err, result) {
    //             done();
    //             callback(result.rows);
    //         });
    //     })
    //     pool.end()
    // },
}