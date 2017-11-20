const http = require('http'),
  fs = require('fs'),
  phantom = require('phantom');

http.createServer((req, res) => {
  let url = req.url.replace('/?url=', ''),
    fileName = '/tmp/' + (+new Date()) + ".pdf",
    ph, page;
  phantom.create()
    .then((phInstance) => {
      ph = phInstance;
      return ph.createPage();
    }).then((pageInstance) => {
      page = pageInstance;
      return page.open(url);
    }).then((status) => {
      return page.render(fileName, { margin: '1cm' });
    }).then(() => {
      console.log('Page Rendered');
      ph.exit();
      const readStream = fs.createReadStream(fileName);
      readStream.on('open', () => {
        readStream.pipe(res);
        // mateus apagava
        //fs.unlink(fileName);
      }).on('error', (err) => {
        res.end(err);
        fs.unlink(fileName);
      });
    });
}).listen(8089);