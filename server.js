const fs = require('fs');
//const hostname = '192.168.137.1';

//const hostname = '10.8.12.191';

const hostname = '0.0.0.0';

var express = require('express');
var http = require('http');


const bodyParser = require("body-parser");

var app = express();
//app.use(bodyParser.json());

app.use(bodyParser.urlencoded({
    extended: true
}));

const mysqlConnection = require("./connection")
//const PeopleRoutes = require("./routes/people")
const StringRoutes = require("./routes/insertString")
const RdStringRoutes = require("./routes/readingString")

app.use("/rd", RdStringRoutes);
app.use("/",StringRoutes);



var server = http.createServer(app);

app.get('/', function(req, res) {
  //res.statusCode = 200;
  res.writeHead(200,('Content-Type', 'text/html'));
  fs.readFile('./index.html',null,function(error,data){
    if(error){
      res.writeHead(404);
      res.write("Whoops! File not found");
    } else {
      res.write(data);
    }
    res.end();
  });
  // res.end();
});

// app.post('/', function(request, response){
//   console.log(request.body.user.name);
//   console.log(request.body.user.email);
// });
server.listen(80, hostname);
server.on('listening', function() {
    console.log('Express server started on port %s at %s', server.address().port, server.address().address);
});
