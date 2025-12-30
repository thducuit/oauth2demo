const http = require("http");

const PORT = 3000;
const APP_NAME = process.env.APP_NAME || "UNKNOWN";

const server = http.createServer((req, res) => {
  res.writeHead(200, { "Content-Type": "text/plain" });
  res.end(`Response from ${APP_NAME}\n`);
});

server.listen(PORT, () => {
  console.log(`${APP_NAME} running on port ${PORT}`);
});
