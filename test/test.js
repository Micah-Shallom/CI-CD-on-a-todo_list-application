var capitalize = require('../libraries/capitalize')
var expect = require('chai').expect

describe("#capitalize", function () {
   it ("capitalizes the first word in a string", function() {
     expect(capitalize("hello")).to.equal("HELLO")
   })
})