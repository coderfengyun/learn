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
        println!("count after create a: {}", Rc::strong_count(&a));
        let b = ConsRc(3, Rc::clone(&a));
        println!("count after create b: {}", Rc::strong_count(&a));
        {
            let c = ConsRc(4, Rc::clone(&a));
            println!("count after create c: {}", Rc::strong_count(&a));
        }
        println!("count after c goes out of scope: {}", Rc::strong_count(&a));
    }
}

/// Example for RefCell<T>
pub mod ref_cell_example {
    use crate::List;
    use std::cell::RefCell;
    use std::rc::Rc;

    pub trait Messenger {
        fn send(&self, msg: &str);
    }

    pub struct LimitTracker<'a, T: 'a + Messenger> {
        messenger: &'a T,
        value: usize,
        max: usize,
    }

    impl<'a, T: Messenger> LimitTracker<'a, T> {
        pub fn new(messenger: &T, max: usize) -> LimitTracker<T> {
            LimitTracker {
                messenger,
                value: 0,
                max,
            }
        }

        pub fn set_value(&mut self, value: usize) {
            self.value = value;
            let percentage_used = self.value as f64 / self.max as f64;
            if percentage_used >= 1.0 {
                self.messenger.send("Error: You have exceed your quota.");
            } else if percentage_used > 0.9 {
                self.messenger
                    .send("Urgent warning: You have used 90 percent of your quota.");
            } else if percentage_used > 0.75 {
                self.messenger
                    .send("Warning: You have used 75 percent of your quota.");
            }
        }
    }

    #[derive(Debug)]
    enum RefCellList {
        ConsRefCell(Rc<RefCell<i32>>, Rc<RefCellList>),
        Nil,
    }

    mod rc_cell_tests {
        use crate::ref_cell_example::RefCellList::{ConsRefCell, Nil};
        use crate::ref_cell_example::{LimitTracker, Messenger};
        use std::cell::RefCell;
        use std::rc::Rc;

        struct MockMessenger {
            sent_messages: RefCell<Vec<String>>,
        }

        impl MockMessenger {
            pub fn new() -> MockMessenger {
                MockMessenger {
                    sent_messages: RefCell::new(vec![]),
                }
            }
        }

        impl Messenger for MockMessenger {
            fn send(&self, msg: &str) {
                let mut ref_mut = self.sent_messages.borrow_mut();
                ref_mut.push(String::from(msg));
            }
        }

        #[test]
        fn it_sends_an_over_75_percent_warning_message() {
            let messenger = MockMessenger::new();
            let mut limit_tracker = LimitTracker::new(&messenger, 100);

            limit_tracker.set_value(76);

            assert_eq!(messenger.sent_messages.borrow().len(), 1);
        }

        #[test]
        fn it_will_immutable_outside_mutable_inside() {
            let value = Rc::new(RefCell::new(5));
            let a = Rc::new(ConsRefCell(Rc::clone(&value), Rc::new(Nil)));
            let b = ConsRefCell(Rc::new(RefCell::new(6)), Rc::clone(&a));
            let c = ConsRefCell(Rc::new(RefCell::new(10)), Rc::clone(&a));

            *value.borrow_mut() += 10;
            println!("a after={:?}", a);
            println!("b after={:?}", b);
            println!("c after={:?}", c);
        }
    }
}
