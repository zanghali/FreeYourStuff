var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var item = require('./controllers/item');
var upload = require('./controllers/upload');
var user = require('./controllers/user');
var chat = require('./controllers/chat');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use('/assets', express.static('uploads'))
app.use(function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});

//Upload

app.post('/upload', function (request, response) {
    upload.upload(request,response,(result) => {
        response.send(result);
    })
});


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
app.post('/getItemByUser', function (request, response) {
    item.getItemByUser(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemOfUserInterestedBy', function (request, response) {
    item.getItemOfUserInterestedBy(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemByFilterCategory', function (request, response) {
    item.getItemByFilterCategory(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemByFilterAvailability', function (request, response) {
    item.getItemByFilterAvailability(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getItemByFilterGeo', function (request, response) {
    item.getItemByFilterGeo(request.body, (result) => {
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

app.post('/updateItemStatus', function (request, response) {
    item.updateItemStatus(request.body, (result) => {
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

app.post('/getUserInterestedByItem', function (request, response) {
    user.getUserInterestByItem(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getNumberInterestedByItem', function (request, response) {
    user.getNumberInterestByItem(request.body, (result) => {
        response.send(result);
    })
});

app.post('/setUserInterestedByItem', function (request, response) {
    user.setUserInterestedByItem(request.body, (result) => {
        response.send(result);
    })
});

app.post('/deleteUserInterestedByItem', function (request, response) {
    user.deleteUserInterestByItem(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getUserList', function (request, response) {
    user.getUserList(request.body, (result) => {
        response.send(result);
    })
});



//Chat


app.post('/addChat', function (request, response) {
    chat.addChat(request.body, (result) => {
        response.send(result);
    })
});

app.post('/getChat', function (request, response) {
    chat.getChat(request.body, (result) => {
        response.send(result);
    })
});



app.listen(3000);