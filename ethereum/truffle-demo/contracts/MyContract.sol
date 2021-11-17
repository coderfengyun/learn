pragma solidity >0.4.0;
contract MyContract {
    uint private amount;

    constructor() public {
        amount = 101;
    }
    
    function updateAmount(uint newAmount) public returns (bool success) {
        require(newAmount > 100); /* Contract stores numbers greater than 100. */
        amount = newAmount;
        return true;
    }
    
    function getAmount() public view returns (uint) {
        return amount;
    }
}
