var router = require('express').Router();

var Events = {
	/*
	 * List all events of a user.
	 * 
	 * To test: 
	 * 
	 */
	 getEvents: function(req, res){
		
	},

	/*
	 * Find the event's information for the given parameter. 
	 * 
	 * To test: 
	 * 
	 */
	 getEvent: function(req, res){
		
	},

	/*
     * Create a new event with the json data contained in request body. The content-type must be set to application/json.
     * To make the below sample code run correctly, the json data must contain id attribute, here is the sample.
     * {
     *  'event_id':'1',
     *  'fb_id': 'MBaaS',
     *	'list': ['fb_id_1','fb_id_2'],
     *	'message': 'hello',
     * 
     *	'iban' nameofaccountholder address 
     * }
     * 
     * To test: curl -X POST -H 'Content-Type: application/json' -d '{\'id\':\'1\', \'name\':\'MBaaS\'}' http://${appHostName}.mybluemix.net/${appHostName}/v1/apps/${applicationId}/users
     * 
     */
	 createEvent: function(req, res){
		var PushLib = require('./push'),
			pushIns = new PushLib();
		var message = {
		    alert : "Push Notification from Javascript SDK",
		    url : "abc"
		};
		pushIns.pushByIds(message, ['5479d0641fde007c801eeebc'], function(response) {
		    console.log(response);
		    res.status(200).end();
		},function(err) {
		    console.log(err);
		});
	},

	/*
	 * Modify the user's data matched the id attribute with the put request body.
	 * 
	 * To test: curl -X PUT -H 'Content-Type: application/json' -d '{\'id\':\'1\', \'name\':\'MBaaS\'}' http://${appHostName}.mybluemix.net/${appHostName}/v1/apps/${applicationId}/users/1
	 * 
	 */
	 updateEvent: function(req, res){	
		var query = req.data.Query.ofType('User');
		query.find({id: req.params.id}, {limit: 1}).then(function(user) {
			user.forEach(function(person){
				person.set(req.body);
				person.save().then(function(updated) {
					res.json(updated);
				}, function(err){
				    res.send(err);
				});
			});
		}, function(err){
		    res.send(err);
		});
	},

	/*
	 * Delete the user's data for the given parameter. Below shows how to delete the user data by the id attribtue. 
	 * It can be also deleted by other attribute like name by changing query.find option.
	 * 
	 * To test: curl -X DELETE http://${appHostName}.mybluemix.net/${appHostName}/v1/apps/${applicationId}/users/1
	 * 
	 */
	 deleteEvent: function(req, res){
		var query = req.data.Query.ofType('User');
		query.find({id: req.params.id}, {limit: 1}).done(function(users) {
			if (users.length==1) {
				users[0].del().done(function(deleted) {
					var isDeleted = deleted.isDeleted();
					if (deleted.isDeleted()) {
						res.send('delete successfully.');
					}
					else {
						res.status(500);
						res.send('delete failed.');
					}
				}, function(err){
				    res.send(err);
				});
			}
			else {
				res.status(404);
				res.send('No such user found');
			}
		}, function(err){
		    res.send(err);
		});
	}
};

router.get('/events',    	Events.getEvents);

router.get('/events/:id', 	Events.getEvent);

router.post('/events',		Events.createEvent);

router.put('/events/:id',	Events.updateEvent);

router.delete('/events/:id',   	Events.deleteEvent);

module.exports = exports = router;


