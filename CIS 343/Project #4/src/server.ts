interface IValidationResult {
  valid: boolean,
  missing: string
}

import express from 'express';
import parser from "body-parser";
import fs from 'fs';
import path from 'path';

const requirements: string[] = ["firstName", "lastName", "homeAddress", "SID", "goodSlave", "beatingsToDate", "family"];

const database = require('../db/programmers.json');

// We must have our list of programmers to use
if (!fs.existsSync(path.join(__dirname, '../db/programmers.json'))) {
  throw new Error('Could not find database of programmers!');
}

if (!(database instanceof Array)) {
  throw new Error(`The database isn't an array! I can't even right now!`);
}

const app = express();
const port = 3000;

app.use(parser.json());

app.get('/', (req, res) => {
  res.send(database);
});

app.get('/:id', (req, res) => {
  const id = req.params.id;
  let result: object = {};
  for (const person of database) {
    if (person.SID === id) {
      result = person;
      break;
    }
  }
  res.send(result);
});

app.put('/:id', (req, res) => {
  const id = req.params.id;
  const body = req.body;
  const result = isValid(body);
  if (!result.valid) {
    res.send(`json is invalid. you're missing the ${result.missing} field.`);
  }

  let newPerson: object = {};
  for (let i = 0; i < database.length; i++) {
    if (database[i].SID === id) {
      database[i] = body;
      newPerson = database[i];
    }
  }
  res.send(newPerson);
});

app.post('/', (req, res) => {
  const id = req.params.id;
  const body = req.body;
  const result = isValid(body);
  if (!result.valid) {
    res.send(`json is invalid. you're missing the ${result.missing} field.`);
  }

  database.push(body);
  res.send(database[database.length - 1]);
});

// IMPLEMENT A ROUTE TO HANDLE ALL OTHER ROUTES AND RETURN AN ERROR MESSAGE
app.all("*", (req, res, next) => {
  res.send(`nothing to see here.`);
});

app.listen(port, () => {
  console.log(`She's alive on port ${port}`);
});

function isValid(json: any): IValidationResult {
  if (json instanceof Array) {
    for (const record of json) {
      for (let i = 0; i < requirements.length; i++) {
        if (typeof record[requirements[i]] === "undefined") {
          return {valid: false, missing: requirements[i]};
        }
      }
    }
  } else {
    for (let i = 0; i < requirements.length; i++) {
      if (typeof json[requirements[i]] === "undefined") {
        return {valid: false, missing: requirements[i]};
      }
    }
  }
  return {valid: true, missing: ''};
}