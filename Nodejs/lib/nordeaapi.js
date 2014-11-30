
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
    payload = JSON.stringify(payload);
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
          var result = JSON.parse(data);
          if (result.StatusCode === 0) {
            // succes
            // TODO: need dev_ids from database
            var message = {
                alert : "Push Notification from Javascript SDK",
                url : "abc"
            };
            req.push.sendNotificationByDeviceIds(message, ['547a05c31fde007c802026f9','5479d0641fde007c801eeebc'])
              .then(function(response) {
                  // push success
                  req.logger.info('push successfully');
              },function(err) {
                req.logger.info('push fail' + err);
              });
            res.json(data);
          } else {
            // fail
            res.status(400).end();
          }
          
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


