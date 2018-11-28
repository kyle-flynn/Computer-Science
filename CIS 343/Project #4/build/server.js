"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const fs_1 = __importDefault(require("fs"));
const path_1 = __importDefault(require("path"));
const database = require('../db/programmers.json');
// We must have our list of programmers to use
if (!fs_1.default.existsSync(path_1.default.join(__dirname, '../db/programmers.json'))) {
    throw new Error('Could not find database of programmers!');
}
if (!(database instanceof Array)) {
    throw new Error(`The database isn't an array! I can't even right now!`);
}
const app = express_1.default();
const port = 3000;
app.use(body_parser_1.default.json());
app.get('/', (req, res) => {
    res.send(database);
});
app.get('/:id', (req, res) => {
    const id = req.params.id;
    let result = {};
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
    res.send(`Fill me in to update values with ID: ${id}`);
});
app.post('/', (req, res) => {
    const body = req.body; // Hold your JSON in here!
    res.send(`You sent: ${body}`);
});
// IMPLEMENT A ROUTE TO HANDLE ALL OTHER ROUTES AND RETURN AN ERROR MESSAGE
app.all("*", (req, res, next) => {
    res.send(`nothing to see here.`);
});
app.listen(port, () => {
    console.log(`She's alive on port ${port}`);
});
