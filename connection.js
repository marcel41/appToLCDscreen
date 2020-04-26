const mysql = require("mysql");
var mysqlConnection = mysql.createConnection({
  host: "localhost",
  user: "",
  password: "",
  database: "wb",
  multipleStatements: true
});

mysqlConnection.connect((err) => {
  if(!err)
  {
    console.log("Connected");
  }
  else
  {
    console.log("Connection failed")
    console.log(err);
  }
});

module.exports = mysqlConnection;
