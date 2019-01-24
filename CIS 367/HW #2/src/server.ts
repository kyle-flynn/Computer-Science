import express = require("express");
import {createServer} from "http";
import * as path from "path";

const app: express.Application = express();

app.use(express.static(path.join(__dirname, "../public")));

app.use("/", (req, res) => {
  res.sendFile(path.join(__dirname, "../public/index.html"));
});

app.get("*", (req, res) => {
  res.status(404).send("Content not found.");
});

createServer(app).listen({
  port: 8081,
  host: "127.0.0.1"
}, () => console.log(`Web server has started running.`));