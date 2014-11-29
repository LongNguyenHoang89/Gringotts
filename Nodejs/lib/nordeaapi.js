
var router = require('express').Router();

var NordeaAPI = {
  /*
   * List all events of a user.
   * 
   * To test: 
   * 
   */
   createTransaction: function(req, res){
    var payload = req.body ? req.body: "",
        https = require('https');
    req.logger.info(payload);
    var options = {
        host: 'nordea-api.icds.ibmcloud.com',
        path: '/nordea/sb/transactions/transferMoney?client_id=245f9659-dbd1-4460-8ab9-e07a1127937c',
        method: "POST",
        port: 443,
        rejectUnauthorized: false,
        headers: {
          "Content-Type": "application/json",
          "Content-Length": payload.length
        }
      };
      var data = "";
      var apiReq = https.request(options, function(response) {
        response.on('data', function(d) {
          data += d;
        });
        response.on('end', function() {
          res.json(data);
        });
        response.on('error', function(error) {
          process.stdout.write(error);
        });
      });
      if (payload.length > 0) {
        apiReq.write(payload);
      }
      apiReq.end();  
    }
};


router.post('/transactions',    NordeaAPI.createTransaction);


module.exports = exports = router;


