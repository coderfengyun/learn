import { greeter, Person } from "./greeter"

function hello(divName: string, greetObject: Person) {
    const elt = document.getElementById(divName);
    elt.innerText = greeter(greetObject);
}

hello("", { firstName: "tn", lastName: "chen" });