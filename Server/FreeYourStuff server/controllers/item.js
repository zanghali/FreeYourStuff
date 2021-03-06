const { Pool, Client } = require('pg');
const config = require('../config/db');
const fs = require('fs-extra');
var NodeGeocoder = require('node-geocoder');


module.exports = {

    addItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            var options = {
                provider: 'google',
                // Optional depending on the providers
                httpAdapter: 'https', // Default
                apiKey: 'AIzaSyBRQxnvggubjUOuDiFU3w5skHIo3-8UWEs', // for Mapquest, OpenCage, Google Premier
                formatter: null         // 'gpx', 'string', ...
            };
            var geocoder = NodeGeocoder(options);
            geocoder.geocode(data.address, function(err, res) {
                if(err != null || res == null || res == undefined)
                     callback(false);

                // If the json response is not null, we set gps field to lat,lng
                let gps = (!Object.keys(res).length) ? ',' : (res[0].latitude + ',' + res[0].longitude);

                let query = "INSERT INTO item (category,title,description,photo,address,phone,status,creation_date,gps,availability,id_user) VALUES ($1,$2,$3,$4,$5,$6,'waiting',current_timestamp,$7,$8,$9)";
                let itemdetails = [data.category, data.title, data.description, data.photo,data.address,data.phone,gps,data.availability,data.id_user];
    
                client.query(query, itemdetails, function (err, result) {
                    done();
                    console.log(err);
                    callback(err==null);
                });
               
              });
        })
        pool.end()
    },

    getItemList: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })
        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM ITEM WHERE item.status='inProgress' OR item.status='waiting'";
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
            let itemdetails = [data.id_item];

            client.query(query, itemdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    getItemByUser: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let coordinates=data.gps.split(',');
            let query = "SELECT *,(ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180)))) AS distance FROM item WHERE id_user=$3";
            let itemdetails = [parseFloat(coordinates[0]),parseFloat(coordinates[1]),data.id_user];

            client.query(query, itemdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    getItemOfUserInterestedBy: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let coordinates=data.gps.split(',');
            let query = "SELECT item.category,item.title,item.description,item.photo,item.address,item.phone,item.status,item.creation_date,item.gps,item.availability,item.id_user,item.id_item,(ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180)))) AS distance  FROM item  LEFT JOIN user_interested_by_item ON item.id_item=user_interested_by_item.id_item WHERE user_interested_by_item.id_user=$3";
            let itemdetails = [parseFloat(coordinates[0]),parseFloat(coordinates[1]),data.id_user];

            client.query(query, itemdetails, function (err, result) {
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
            let coordinates=data.gps.split(',');
            let distanceMax=parseFloat(data.distance);
         
            let query = "SELECT *,(ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180)))) AS distance FROM item WHERE (CONCAT(description,' ',title) @@ to_tsquery($4) OR $4='') AND (ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180))))<=$3  AND (item.status='inProgress' OR item.status='waiting') ORDER BY distance ASC"; 

                let keyword= data.keywords.split(' ');
                let itemdetail="'";
    
                keyword.forEach(function(element) {
                    itemdetail+=element+' & ';
                }, this);
    
                itemdetail=[itemdetail.substring(0,itemdetail.length-3)+"'"];


            if(data.keywords=="")
            {
                itemdetail=[''];
            }
            console.log(itemdetail[0]);
           
            let itemdetails=[parseFloat(coordinates[0]),parseFloat(coordinates[1]),distanceMax,itemdetail[0]];
            client.query(query,itemdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    
    getItemByFilterCategory: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "SELECT * FROM item WHERE item.category=$1 AND (item.status='inProgress' OR item.status='waiting')";
            let itemdetails=[data.category];

            client.query(query,itemdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

     
    getItemByFilterAvailability: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            
            let query = "SELECT * FROM item WHERE item.availability=$1 AND (item.status='inProgress' OR item.status='waiting')";
            let itemdetails=[data.availability];
            
            client.query(query,itemdetails, function (err, result) {
                done();
                if(err==null)
                callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    getItemByFilterGeo: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {

            let coordinates=data.gps.split(',');
            let distanceMax=parseFloat(data.distance);

            let query = "SELECT *,(ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180)))) AS distance FROM item WHERE (ROUND(6378137 * acos(sin(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * sin($1*pi()/180) + cos((CAST(split_part(gps, ',', 2) AS FLOAT)*pi()/180) - $2*pi()/180) * cos(CAST(split_part(gps, ',', 1) AS FLOAT)*pi()/180) * cos($1*pi()/180))))<=$3  AND (item.status='inProgress' OR item.status='waiting') ORDER BY distance ASC";
            let itemdetails=[parseFloat(coordinates[0]),parseFloat(coordinates[1]),distanceMax];
            
            client.query(query,itemdetails, function (err, result) {
                console.log(err);
                done();
                if(err==null)                                  
                    callback(result.rows);
                else
                callback(null);
            });
        })
        pool.end()
    },

    updateItemStatus: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
                let query ;
                if(data.id_user==data.id_userInterestedBy)
                {
                 query = "UPDATE user_interested_by_item SET buyer ='true' WHERE user_interested_by_item.id_user=$1 AND user_interested_by_item.id_user=$2 AND user_interested_by_item.id_item=$3";
                }
                else
                {
               query = "UPDATE user_interested_by_item SET seller='true' WHERE (SELECT id_user FROM item WHERE item.id_item=$3) = $1 AND user_interested_by_item.id_user=$2 AND user_interested_by_item.id_item=$3";
                }
                let itemdetails = [data.id_user,data.id_userInterestedBy,data.id_item];
    
                client.query(query, itemdetails, function (err, result) {
                    done();
                    console.log(err);
                    callback(err==null);
                });
               
              });
        pool.end()
    },

    deleteItem: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
            let query = "DELETE FROM item WHERE item.id_item=$1";
            let itemdetails = [data.id_item];

            client.query(query, itemdetails, function (err, result) {
                done();

                if(err==null)
                {
                fs.unlink('./uploads'+data.photo, err => {
                    if(err)
                    callback(false); 
                });
                callback(true)
                }
                else
                callback(false);
            });
       
        })
        pool.end()
    }

}


