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
        use crate::chapter4::PushDownAccumulation::use_case::use_1;

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
        use crate::chapter4::repetition_replacement::usage::use1;

        #[test]
        pub fn test_use1() {
            use1()
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
        use crate::chapter4::trailing_separator::usage::use1;

        #[test]
        pub fn test_use1() {
            use1();
        }
    }
}

pub mod tt_bundle {
    macro_rules! call_a_or_b_on_tail {
        ((a: $a: expr, b: $b: expr), call a: $($tail:tt)*) => {
            $a(stringify!($($tail)*))
        };
        ((a: $a: expr, b: $b: expr), call b: $($tail:tt)*) => {
            $b(stringify!($($tail)*))
        };
        ($ab:tt, $_skip:tt $($tail:tt)*) => {
            call_a_or_b_on_tail!($ab, $($tail)*)
        };
    }

    pub fn compute_len(s: &str) -> Option<usize> {
        Some(s.len())
    }

    pub fn show_tail(s: &str) -> Option<usize> {
        println!("{:?}", s);
        None
    }

    mod usage {

        pub fn use_1() -> Option<usize> {
            call_a_or_b_on_tail!(
                (a: crate::chapter4::tt_bundle::compute_len, b: crate::chapter4::tt_bundle::show_tail),
                the recursive part that skips over all these
                tokens does not much care whether we will call a
                or call b: only the terminal rules care.
            )
        }

        pub fn use_2() -> Option<usize> {
            call_a_or_b_on_tail!(
                (a: crate::chapter4::tt_bundle::compute_len, b: crate::tt_bundle::show_tail),
                and now, to justify the existence of two paths
                we will also call a: its input should somehow
                be self-referential, so let us make it return
                some ninety one!
            )
        }
    }

    mod tests {
        use crate::chapter4::tt_bundle::usage::{use_1, use_2};

        #[test]
        pub fn test_use1() {
            assert_eq!(use_1(), None);
        }

        #[test]
        pub fn test_use2() {
            assert_eq!(use_2(), Some(89));
        }
    }
}

mod visibility {

    macro_rules! newtype_new {
        (struct $name:ident($t:ty)) => { newtype_new!(() struct $name($t)) };
        (pub struct $name:ident($t:ty)) => { newtype_new!((pub) struct $name($t)) };
        (($($vis:tt)*) struct $name:ident($t:ty)) => {
            #[derive(Debug)]
            struct $name($t);
            as_item! {
                impl $name {
                    $($vis)* fn new(value:$t) -> Self {
                        $name(value)
                    }
                }
            }
        }
    }

    macro_rules! as_item {
        ($i:item) => {
            $i
        };
    }

    mod usage {
        pub fn use1() {
            newtype_new!(struct priv_struct(u32));
            let res: priv_struct = priv_struct::new(12);
            println!("{:?}", res);
        }

        pub fn use2() {
            newtype_new!(pub struct pub_struct(i32));
            let res = pub_struct::new(-12);
            println!("{:?}", res);
        }
    }

    mod tests {
        use crate::chapter4::visibility::usage::{use1, use2};

        #[test]
        pub fn use1_test() {
            use1();
        }

        #[test]
        pub fn use2_test() {
            use2();
        }
    }
}

mod abacus {
    macro_rules! abacus {
        ((- $($moves:tt)*) -> (+ $($count:tt)*)) => {
            abacus!(($($moves)*) -> ($($count)*))
        };
        ((- $($moves:tt)*) -> ($($count:tt)*)) => {
            abacus!(($($moves)*) -> (- $($count)*))
        };
        ((+ $($moves:tt)*) -> (- $($count:tt)*)) => {
            abacus!(($($moves)*) -> ($($count)*))
        };
        ((+ $($moves:tt)*) -> ($($count:tt)*)) => {
            abacus!(($($moves)*) -> (+ $($count)*))
        };
        (() -> ()) => {
            0
        };
        (() -> (- $($count:tt)*)) => {
            {(-1i32) $(- replace_expr!($count 1i32))* }
        };
        (() -> (+ $($count:tt)*)) => {
            {(1i32) $(+ replace_expr!($count 1i32))*}
        };
    }

    macro_rules! replace_expr {
        ($_t:tt $sub:expr) => { $sub }
    }

    macro_rules! Simple {
        (-) => {-1};
        (+) => {1};
        ($($moves:tt)*) => { 0 $( + Simple!($moves))* }
    }

    mod usage {

        pub fn use1() {
            let result = abacus!((++-+-+++--++---++----+++) -> ());
            println!("{:?}", result);
        }

        pub fn use_simple() {
            let res = Simple!(++-+-+++--++---++----+++);
            println!("{:?}", res);
        }
    }

    mod tests {
        use crate::chapter4::abacus::usage::{use1, use_simple};

        #[test]
        pub fn test1() {
            use1();
        }

        #[test]
        pub fn testSimple() {
            use_simple()
        }
    }
}