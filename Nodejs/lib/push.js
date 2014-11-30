
var PushLib = function () {
	var ibmpush = require('ibmpush'),
		ibmbluemix 	= require('ibmbluemix');
	var config = {
	    applicationId:"d80dee38-dde5-49b6-8e38-c7ed2af2f1c2",
	    applicationRoute:"smaug.mybluemix.net",
	    applicationSecret:"f8fec7c30264082a8bebd569184431353059d0f1"
	};
	ibmbluemix.initialize(config);
	this.push = ibmpush.initializeService();
}


PushLib.prototype.pushByIds = function(message, dev_ids, callback){
	// Send the notification
	this.push.sendNotificationByDeviceIds(message, dev_ids).then(callback);
}

module.exports = PushLib;


