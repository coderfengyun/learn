use crate::RefList::{Cons, Nil};
use std::ops::Deref;
use std::rc::Rc;

enum RefList<'a> {
    Cons(i32, &'a Box<&'a RefList<'a>>),
    Nil,
}

enum List {
    Cons2(i32, Box<List>),
    Nil2,
}

enum RcList {
    ConsRc(i32, Rc<RcList>),
    NilRc,
}

struct MyBox<T> {
    val: T,
}

impl<T> MyBox<T> {
    fn new(x: T) -> MyBox<T> {
        MyBox { val: x }
    }
}

impl<T> Deref for MyBox<T> {
    type Target = T;

    fn deref(&self) -> &Self::Target {
        &self.val
    }
}

fn hello(name: &str) {
    println!("Hello {}", name);
}

struct CustomSmartPointer {
    data: String,
}

impl Drop for CustomSmartPointer {
    fn drop(&mut self) {
        println!("Dropping CustomSmartPointer with data `{}`!", self.data);
    }
}

fn main() {}

#[cfg(test)]
pub mod tests {
    use super::*;
    use crate::List::{Cons2, Nil2};
    use crate::RcList::{ConsRc, NilRc};
    use std::rc::Rc;

    #[test]
    pub fn test_eq_to_ref() {
        let x = 5;
        let y = &x;

        assert_eq!(5, x);
        assert_eq!(5, *y);
    }

    #[test]
    pub fn test_eq_to_smart_pointer() {
        let x = 5;
        let y = Box::new(x);

        assert_eq!(5, x);
        assert_eq!(5, *y);
    }

    #[test]
    pub fn test_eq_to_my_box() {
        let x = 5;
        let y = MyBox::new(x);

        assert_eq!(5, x);
        assert_eq!(5, *y);
    }

    #[test]
    pub fn test_custom_smart_pointer() {
        let c = CustomSmartPointer {
            data: String::from("my stuff"),
        };
        let d = CustomSmartPointer {
            data: String::from("other stuff"),
        };
        println!("CustomSmartPointer Created!");
        std::mem::drop(c);
        println!("CustomSmartPointer dropped before the end of main.");
    }

    #[test]
    pub fn test_box() {
        let list = Cons2(1, Box::new(Cons2(2, Box::new(Cons2(3, Box::new(Nil2))))));
        let m = MyBox::new(String::from("Rust"));
        hello(&m);
    }

    #[test]
    #[ignore]
    pub fn test_ref_list_when_two_reference_to_box() {
        // let a = Cons(5, &Box::new(&Cons(10, &Box::new(&Nil))));
        // let b = Cons(3, &Box::new(&a));
        // let c = Cons(4, &Box::new(&a));
    }

    #[test]
    pub fn test_rc_list_when_two_ref() {
        let a = Rc::new(ConsRc(5, Rc::new(ConsRc(10, Rc::new(NilRc)))));
        let b = ConsRc(3, Rc::clone(&a));
        let c = ConsRc(4, Rc::clone(&a));
    }
}
