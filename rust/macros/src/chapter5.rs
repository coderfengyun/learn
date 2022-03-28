mod slice_length {

    macro_rules! replace_expr {
        ($t:tt $sub:expr) => {$sub};
    }

    macro_rules! count_tts {
        ($($tts: tt)*) => {
            <[u32]>::len(&[$(replace_expr!($tts 1u32)),*]);
        }
    }

    mod usage {
        pub fn use1() {
            let res = count_tts!(pub pub pub);
            println!("{:?}", res);
        }

        #[test]
        pub fn use1_test() {
            use1();
        }
    }
}

mod count_idents {
    macro_rules! count_idents {
        ($($idents:ident),* $(,)*) => {
            {
                enum idents {
                    $($idents),* , __CountLastSignal
                }

                idents::__CountLastSignal as u32
            }
        }
    }

    mod usage {
        pub fn use1() {
            let res = count_idents!(a,b,c,d,,,,);
            println!("{:?}", res);
        }

        #[test]
        pub fn use1_test() {
            use1();
        }
    }
}

mod parse_enum {
    macro_rules! parse_unitary_variants {
        (@as_expr $e:expr) => { $e };
        (@as_item $($i:item)+) => { $($i)+ };
        (@collect_unitary_variants ($callback:ident ( $($args:tt)* )), ($(,)*) -> ($($var_names:ident)*,)) => {
            parse_unitary_variants! {
                @as_expr
                $callback! { $($args)* ($($var_names),*) }
            }
        };
        (@collect_unitary_variants ($callback:ident ( $($args:tt)* )), ($(,)*) -> ($($var_names:ident)*,)) => {
            parse_unitary_variants! {
                @as_item
                $callback!{ $($args)* ($($var_names),*) }
            }
        };
        (@collect_unitary_variants $fixed:tt, (#[$_attr:meta] $($tail:tt)*) -> ($($var_names:tt)*,)) => {
            parse_unitary_variants! {
                @collect_unitary_variants $fixed,
                ($($tail)*) -> ($($var_names)*)
            }
        };
        (@collect_unitary_variants $fixed:tt, ($var:ident $(= $_val:expr)*, $($tail:tt)*) -> ($($var_names:tt)*,)) => {
            parse_unitary_variants! {
                @collect_unitary_variants $fixed,
                ($($tail)*) -> ($($var_names)*)
            }
        };
        (@collect_unitary_variants $fixed:tt, ($var:ident $_struct:tt, $($tail:tt)*) -> ($($var_names:tt)*,)) => {
            const _error: () = "cannot parse unitary variants from enum with non-unitary variants";
        };
        (enum $name:ident { $($body:tt)* } => $callback:ident $arg:tt ) => {
            parse_unitary_variants! {
                @collec_unitary_variants!
                ($callback $arg), ($($body)*,) -> ()
            }
        }
    }
}