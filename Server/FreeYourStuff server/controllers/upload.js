const { Pool, Client } = require('pg');
const config = require('../config/db');
var mkdirp = require('mkdirp');
const fs = require('fs-extra');
var multer = require('multer');
const uuidv4 = require('uuid/v4');

module.exports = {
   
    upload: function (req,res,callback) {

    var Storage = multer.diskStorage({
        destination: function (req, file, callback) {
            callback(null, './tmp');
        },
        filename: function (req, file, callback) {
            callback(null, uuidv4()+ '.jpg');
        }
    });
    var upload = multer({ storage: Storage }).single('photo');
 
    upload(req, res, function (err) {
        if (err) {
            return res.send(null)// Error uploading file
        }

        fs.move('./tmp/'+req.file.filename, './uploads/'+req.body.id_user+'/'+req.file.filename, err => {
            if (err) 
            return res.send(null)//  Error moving file into your user space
            });

        fs.readdir('./uploads/'+req.body.id_user, (err, files) => {

            if(files!=null && files.length>10)
            {
                fs.unlink('./uploads/'+req.file.filename, err => {
                    if(err)
                    return res.send(null); //Error clearing uploads
                });
                return res.send(null);// You have reached your maximum limit of space, please delete some of your items or get the premium version
            }
            else{
                return res.send('/'+req.body.id_user+'/'+req.file.filename)//  succesfully uploaded
            }
            return res.send(null); // Error reading files
        });
    });

    }
}