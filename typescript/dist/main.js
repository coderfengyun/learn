"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var greeter_1 = require("./greeter");
function hello(compiler) {
    console.log(greeter_1.greeter({ firstName: "tn", lastName: "chen" }));
}
hello("TypeScript");
