const { Pool, Client } = require('pg');
const config = require('../config/db');
var mkdirp = require('mkdirp');
const fs = require('fs');

module.exports = {
   

    // createFile: function (data, callback) {
    //     const dir='/users/'+data.id_user.toString();
    //     //creation of the folder user/id_user if it does not exist
    //     mkdirp(dir, function(err) {
    //         if(err)
    //         callback(false);
    //         else{
    //             //count the number of file uploaded and check if it's <10
    //             fs.readdir(dir, (err, files) => {
    //                 if(files.length<10)
    //                 callback(dir);
    //                 else 
    //                 callback(false);
    //             });
    //             }
           
            
    //          }); 
   
   //}
}