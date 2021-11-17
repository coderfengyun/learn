pragma solidity >0.4.0;
import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/MyContract.sol";
contract MyContractTest {
function testInitialStoredValue() {
            MyContract mycontract = new MyContract();
            uint expected = 101;
            Assert.equal(mycontract.getAmount(), expected, "Initial amount set should be 101.");
    }
function testTheThrow() {
            MyContract mycontract = new MyContract();
            ThrowProxy throwproxy = new ThrowProxy(address(mycontract));
            MyContract(address(throwproxy)).updateAmount(97);
            bool r = throwproxy.execute.gas(200000)();
            Assert.isFalse(r, "Should be false because is should throw!");
    }
function testNoThrow() {
            MyContract mycontract = new MyContract();
            ThrowProxy throwproxy = new ThrowProxy(address(mycontract));
            MyContract(address(throwproxy)).updateAmount(122);
            bool r = throwproxy.execute.gas(200000)();
            Assert.isTrue(r, "Should be true!");
    }
}
// Proxy contract for testing throws
contract ThrowProxy {
  address public target;
  bytes data;
  function ThrowProxy(address _target) {
    target = _target;
  }
  //prime the data using the fallback function.
  function() {
    data = msg.data;
  }
  function execute() returns (bool) {
    return target.call(data);
  }
}
