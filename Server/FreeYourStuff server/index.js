var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var multer = require('multer');
var item = require('./controllers/item');
//var upload = require('./controllers/upload');
var user = require('./controllers/user');
//var chat = require('./controllers/chat');
//var interested=('./interested');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});
//Upload
// var storage =   multer.diskStorage({
//     destination: function (req, file, callback) {
//       callback(null, '/uploads');
//     },
//     filename: function (req, file, callback) {
//       callback(null, file.fieldname + '-' + Date.now());
//     }
//   });
//   var upload = multer({ storage : storage}).single('photo');
 
//   app.post('/upload',function(req,res){
//       upload(req,res,function(err) {
//           if(err) {
//               return res.end("Error uploading file.");
//           }
//           res.end("File is uploaded"+req.body.filename);
//       });
//   });

//Item
app.post('/addItem', function (request, response) {
    item.addItem(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemList', function (request, response) {
    item.getItemList(request, (result) => {
        response.send(result);
    })
});

app.post('/getItemById', function (request, response) {
    item.getItemById(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemByKeywords', function (request, response) {
    item.getItemByKeywords(request.body, (result) => {
        response.send(result);
    })
});

app.post('/deleteItem', function (request, response) {
    item.deleteItem(request.body, (result) => {
        response.send(result);
    })
});

//User

app.post('/addUser', function (request, response) {
    user.addUser(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getUserByEmail', function (request, response) {
    user.getUserByEmail(request.body, (result) => {
        console.log(result);
        response.send(result);
    })
});


//Chat


app.listen(3000);