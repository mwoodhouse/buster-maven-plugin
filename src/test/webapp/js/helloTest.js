HelloTest = TestCase("HelloTest");

HelloTest.prototype.testValue = function() {
  assertEquals("HelloMatt", hello("Matt"));
};