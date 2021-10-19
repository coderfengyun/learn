/**
* SPDX-License-Identifier: Academic Free License v1.1
* Author: coderfengyun
*/
pragma solidity  >=0.4.22 <0.10.0;

contract Storage {
    
    uint[] private vars;
    
    function saveToStack() public pure {
        uint myVal1 = 1;
        uint myVal2 = 2;
        assert(myVal1 == myVal2);
    }
    
    function saveToMemory() public pure {
        string memory myString = "test";
        assert(bytes(myString).length == 10);
    }
    
    function saveToStorage() public {
        vars.push(2);
        vars.push(3);
        assert(vars.length == 4);
    }
}
