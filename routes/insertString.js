const express = require("express");
const Router = express.Router();
const mysqlConnection = require("../connection");

//let stmt = `INSERT INTO values_stored(idvalues_stored,values_storedcol)
//            VALUES(?,?)`;

let stmt = `UPDATE values_stored
            SET idvalues_stored = ?,values_storedcol = ?`;
let todo = [0, "hahaaXD"];

Router.post("/",(req, res) => {
   console.log(req.body.strToPrint);
  // console.log(req.body.user.email);
  todo[1] = req.body.strToPrint;
  //console.log(req)
  mysqlConnection.query(stmt, todo, (err, results, fields) =>{
    if (err) {
     return console.error(err.message);
   }
   // get inserted id
   console.log('Todo Id:' + results.insertId);
  })
  res.sendStatus(200);
})
//mysqlConnection.end();
module.exports = Router;
