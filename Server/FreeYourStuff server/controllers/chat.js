const { Pool, Client } = require('pg');
const config = require('../config/db');

module.exports = {

    addChat: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "INSERT INTO chat (id_item,id_sender,id_receiver,message,date) VALUES ($1,$2,$3,$4,current_timestamp)";
            let chatdetails = [data.id_item, data.id_sender, data.id_receiver,data.message];

            client.query(query, chatdetails, function (err, result) {
                done();
                callback(err == null);
            });
        })
        pool.end()
    },

    getChat: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM chat WHERE chat.id_item=$1 AND ((chat.id_sender=$2 AND chat.id_receiver=$3) OR ( chat.id_sender=$3 AND chat.id_receiver=$2))"
            let chatdetails = [data.id_item, data.first_person, data.second_person];
            client.query(query, chatdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },
    
}