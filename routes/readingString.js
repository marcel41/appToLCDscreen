const express = require("express");
const Router = express.Router();
const mysqlConnection = require("../connection");

Router.get("/",(req, res) => {
  mysqlConnection.query("SELECT * from values_stored", (err,rows,fields) => {
    if(!err)
    {
      //stripRes = res.split("")
      //res.send("word:"+ rows[0].values_storedcol)
      res.send("*"+ rows[0].values_storedcol)
      //res.json(rows);
      //let xd = rows[0].values_storedcol
      //console.log(xd)
      //res.send(rows);
    }
    else
    {
      console.log(err);
    }
  })

})

module.exports = Router;
