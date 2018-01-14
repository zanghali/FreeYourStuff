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
            };
            var geocoder = NodeGeocoder(options);
            geocoder.geocode(data.address, function(err, res) {
                // If the json response is not null, we set gps field to lat,lng
                let gps = (!Object.keys(res).length) ? ',' : (res[0].latitude + ',' + res[0].longitude);

                if(err!=null)
                     callback(false);
                let query = "INSERT INTO item (category,title,description,photo,address,phone,status,creation_date,gps,availability,id_user) VALUES ($1,$2,$3,$4,$5,$6,'waiting',current_timestamp,$7,$8,$9)";
                let itemdetails = [data.category, data.title, data.description, data.photo,data.address,data.phone,gps,data.availability,data.id_user];
    
                client.query(query, itemdetails, function (err, result) {
                    done();
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
            let query = "SELECT * FROM item WHERE id_user=$1";
            let itemdetails = [data.id_user];

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
            let query = "SELECT * FROM item WHERE CONCAT(description,' ',title) @@ to_tsquery($1) AND (item.status='inProgress' OR item.status='waiting')";
            let keyword= data.keywords.split(' ');
            let itemdetails="'";

            keyword.forEach(function(element) {
                itemdetails+=element+' & ';
            }, this);

            itemdetails=[itemdetails.substring(0,itemdetails.length-3)+"'"];

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
            let distanceMax=convertMetreGps(parseFloat(data.distance));
            console.log(distanceMax);

            let query = "SELECT * FROM item WHERE (CAST (split_part(gps, ',', 1) AS FLOAT)-$1)^2+(CAST (split_part(gps, ',', 2) AS FLOAT)-$2)^2<=$3  AND (item.status='inProgress' OR item.status='waiting')";
            let itemdetails=[parseFloat(coordinates[0]),parseFloat(coordinates[1]),distanceMax];
            
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

    updateItemStatus: function (data, callback) {
        const pool = new Pool({
            connectionString: config.connectionString,
        })

        pool.connect(function (err, client, done) {
     
                let query = "UPDATE item SET status=$1 WHERE id_item=$2";
                let itemdetails = [data.status,data.id_item];
    
                client.query(query, itemdetails, function (err, result) {
                    done();
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
                console.log(result);
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


function convertMetreGps(distance)
{
  
    R = 6378137 //Rayon de la terre en mètre
    //depuis la formle de distance entre deux point a et b: 
    //distanceab = R * Math.acos( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a));
    //si on prend le point d'origine a (0,0) et le point sur le cercle b de rayon "distanceab" (0,lon_b)
    //=>lon_b=distanceab/R
    //b et a une distance de lat_b^2+lon_b^2  de a soit long_b^2 (rad)
    return Math.pow(distance/R*180/Math.PI,2);
}
