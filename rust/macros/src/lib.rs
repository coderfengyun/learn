macro_rules! call_with_larch {
    ($callback:ident) => { $callback!(larch) };
}

macro_rules! expand_to_larch {
    () => { larch };
}

macro_rules! recognise_tree {
    (larch) => { println!("#1, the Larch.") };
    (redwood) => { println!("#2, the Mighty Redwood.") };
    (fir) => { println!("#3, the Fir.") };
    (chestnut) => { println!("#4, the Horse Chestnut.") };
    (pine) => { println!("#5, the Scots Pine.") };
    ($($other:tt)*) => { println!("I don't know; some kind of birch maybe?") };
}

mod tests {

    #[test]
    pub fn test() {
        recognise_tree!(expand_to_larch!());
        call_with_larch!(recognise_tree);
    }
}

mod callbacks {
    macro_rules! callback {
        ($callback:ident($($args:tt)*)) => {
            $callback! ($($args)*)
        };
    }

    mod tests {
        #[test]
        pub fn test_callback() {
            callback!(callback(println("Yes, this *was* unnecessary.")));
        }
    }
}

mod IncrementalTTMunches {

    macro_rules! mixed_rules {
        () => {};
        (trace $name:ident; $($tail:tt)* ) => {
            println!(concat!(stringify!($name), "={:?}"), $name);
            mixed_rules!($($tail)*);
        };
        (trace $name:ident = $init:expr; $($tail:tt)* ) => {
            let $name = $init;
            println!(concat!(stringify!($name), "={:?}"), $name);
            mixed_rules!($($tail)*);
        }
    }

    mod tests {

        #[test]
        pub fn test_ident () {
            let a = 12; let b = 13; let c = 14;
            mixed_rules!(trace a;trace b; trace c;);
        }

        #[test]
        pub fn test_expr () {
            let a = 12; let b = 13; let c = 14;
            mixed_rules!(trace a = c * 10; trace b = a * 10; trace c = b * 10;);
        }
    }
}

mod PushDownAccumulation {
    macro_rules! init_array {
        (@accum(0, $_e:expr) -> ($($body:tt)*)) => { init_array!(@as_expr [$($body)*]) };
        (@accum(1, $e:expr) -> ($($body:tt)*)) => { init_array!(@accum(0, $e) -> ($($body)* $e,)) };
        (@accum(2, $e:expr) -> ($($body:tt)*)) => { init_array!(@accum(1, $e) -> ($($body)* $e,)) };
        (@accum(3, $e:expr) -> ($($body:tt)*)) => { init_array!(@accum(2, $e) -> ($($body)* $e,)) };
        (@as_expr $e:expr) => {$e};
        [$e: expr; $n: tt] => {
          {
              let s = $e;
              init_array!(@accum($n, s.clone()) -> ())
          }
        };
    }

    // macro_rules! init_array2 {
    //     (@accum 0, $_e: expr) => {};
    //     (@accum 1, $e: expr) => {$e};
    //     (@accum 2, $e: expr) => {$e, init_array2!(@accum 1, $e)};
    //     (@accum 3, $e: expr) => {$e, init_array2!(@accum 2, $e)};
    //     [$e: expr; $n: tt] => {
    //         {
    //             let e = $e;
    //             [init_array2!($n, $e)]
    //         }
    //     }
    // }

    mod use_case {

        pub fn use_1() {
            let res: [String; 3] = init_array![String::from("hi"); 3];
            println!("{:?}", res);
        }

        // pub fn use_2() {
        //     let res = init_array2![String::from("hi"); 3];
        //     println!("{:?}", res);
        // }
    }

    mod tests {
        use crate::PushDownAccumulation::use_case::use_1;

        #[test]
        pub fn test() {
            use_1();
        }
    }
}

mod repetition_replacement {
    macro_rules! replace_expr {
        ($_t: tt $sub: expr) => { $sub };
    }

    macro_rules! tuple_default {
        ($($t:tt),*) => {
            (
                $(
                    replace_expr!(
                        ($t)
                        Default::default()
                    ),
                )*
            )
        }
    }

    mod usage {
        pub fn use1() {
            let a = 12;
            let b = "12";
            let res: (i32, &str) = tuple_default!(a,b);
            println!("{:?}", res);
        }
    }

    mod test {
        use crate::repetition_replacement::usage::use1;

        #[test]
        pub fn test_use1() {
            use1();
        }
    }
}

mod trailing_separator {
    macro_rules! match_exprs {
        ($($exprs:expr),* $(,)*) => { ($($exprs)*) };
    }

    mod usage {
        pub fn use1() {
            let a = 0;
            let res = match_exprs!(a,,,,);
            println!("{:?}", res);
        }
    }

    mod test {
        use crate::trailing_separator::usage::use1;

        #[test]
        pub fn test_use1() {
            use1();
        }
    }
}

