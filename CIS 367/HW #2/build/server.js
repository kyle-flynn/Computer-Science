"use strict";
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const http_1 = require("http");
const path = __importStar(require("path"));
const app = express();
app.use(express.static(path.join(__dirname, "../public")));
app.use("/", (req, res) => {
    res.sendFile(path.join(__dirname, "../public/index.html"));
});
app.get("*", (req, res) => {
    res.status(404).send("Content not found.");
});
http_1.createServer(app).listen({
    port: 8081,
    host: "127.0.0.1"
}, () => console.log(`Web server has started running.`));
